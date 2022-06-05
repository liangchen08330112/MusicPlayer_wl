package cn.edu.sict.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText activity_login_et_name;
    private EditText activity_login_et_pswd;
    private Button activity_login_btn_register;
    private Button activity_login_btn_login;
    private CheckBox activity_login_cb_keep_password;

    //定义常量，用于作为requestCode，确认是否由注册界面返回
    public static final int REGISTER_CODE = 0;
    //定义标志，用于帮助“记住密码”功能，判断本页是否从注册页面返回并接收注册页面的回传数据（此时不能执行记住密码功能）
    int requestCode = -1;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //创建或打开数据库
        db = openOrCreateDatabase("musicplayer.db",MODE_PRIVATE,null);
        //在数据库中创建数据表users
        db.execSQL("create table if not exists users(name varchar(50),pswd varchar(50),primary key(name))");
    }

    //无论是第一次打开登录页面，还是从子页面返回，通过onStart()方法判断本页面中的“记住密码”状态，并确定是否在文本输入框里填数据
    @Override
    protected void onStart() {
        super.onStart();

        //由于onActivityResult()回调方法会在onStart()方法之前运行，意味着由注册页面回传的用户名密码填充在文本输入框后，
        //  填充的数据有可能被onStart()方法清空
        //因此：首先判断本页面是否从注册页面接收了回传数据，即是否requestCode==REGISTER_CODE
        //  如果从注册页面接收了回传数据，则不运行记住密码功能（即只有不是从注册页面返回，才运行记住密码功能）
        if(requestCode!=REGISTER_CODE){
            //【记住密码功能 - 读取】
            //利用SharedPreferences检验是否处于“记住密码”状态，确定是否将保存的用户名、密码填充在文本输入框上
            readSharedPreferences();
        }

    }

    private void initView() {
        activity_login_et_name = (EditText) findViewById(R.id.activity_login_et_name);
        activity_login_et_pswd = (EditText) findViewById(R.id.activity_login_et_pswd);
        activity_login_btn_register = (Button) findViewById(R.id.activity_login_btn_register);
        activity_login_btn_login = (Button) findViewById(R.id.activity_login_btn_login);
        activity_login_cb_keep_password = (CheckBox) findViewById(R.id.activity_login_cb_keep_password);

        activity_login_btn_register.setOnClickListener(this);
        activity_login_btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_btn_register:
                //打开注册界面，并传递requestCode
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REGISTER_CODE);
                break;
            case R.id.activity_login_btn_login:
                submit();
                break;
        }
    }


    //点击登录按钮后被调用
    private void submit() {
        // 获取用户输入的用户名、密码，并确认这两个文本框没有留空
        String name = activity_login_et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String pswd = activity_login_et_pswd.getText().toString().trim();
        if (TextUtils.isEmpty(pswd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        //【登陆：验证用户名、密码可与数据库中的一条数据对应】
        //使用try...catch...的原因：
        //  为防止操作数据库产生Exception异常，导致程序中断，
        //  可以使用异常处理的方法，捕捉到的所有Exception都用Toast提示报错的方式解决。
        try{
            //查询
            Cursor cursor = db.rawQuery("select pswd from users where name=?",new String[]{name});
            String pswd_check = null;
            //如果根据用户名只查询到一行信息，则获取该行保存的密码
            if(cursor.getCount()==1){
                //跳转到第一行
                cursor.moveToFirst();
                //获取该行第一列的String内容
                pswd_check = cursor.getString(0);

            if(pswd_check.equals(pswd)){    //验证成功，则登陆
                //【记住密码功能 - 写入】
                writeSharedPreferences(name,pswd);

                //【跳转到HomeActivity主页面】
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                    Intent intent = new Intent(LoginActivity.this,MineFragment.class);
                startActivity(intent);
            }else{         //验证失败，通过Toast提示错误信息
                    Toast.makeText(this,"用户名、密码错误",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
        }

    }

    //回调方法：当从注册页面点击注册按钮返回本页面时
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //每次从新页面返回，都需要初始化成员变量requestCode的值为-1
        //（防止从注册页面获取一次回传数据后，this.requestCode持续为REGISTER_CODE，
        // 导致开启其他页面返回时也不调用记住密码自动填充）
        this.requestCode = -1;

        //判断是否由注册页面点击注册按钮后返回
        //这里建议使用resultCode进行判断，因为物理返回按键也可以从注册页面返回本页，而其用户名密码是未检验的
        if(resultCode == RESULT_OK){
            //获取传回来的数据，即data
            if (data != null){
                //填充两个输入框为注册页面传回来的用户名、密码
                activity_login_et_name.setText(data.getStringExtra("name"));
                activity_login_et_pswd.setText(data.getStringExtra("pswd"));

                //如果本页面由注册页面接收了回传的用户名密码，则设置成员变量requestCode的值为REGISTER_CODE
                this.requestCode = requestCode;
            }
        }

    }

    //【记住密码功能 - 读取】
    //加载页面时，通过读取SharePreferences文件，确认“记住密码”多选框是否应该处于被选中状态，以及被记住的用户名密码是什么
    private void readSharedPreferences() {
        //获取SharedPreferences对象
        SharedPreferences preferences = getSharedPreferences("MusicUserRecord",MODE_PRIVATE);

        //如果从preference中读到，是“记住密码”的状态，则从preference里读取用户名密码并显示在文本框里
        //关于preference.getBoolean("", false)：
        // 参数1，key，根据key读取preference里的信息；
        // 参数2，如果key所对应的变量不存在的话，默认返回什么值
        if(preferences.getBoolean("keep",false)){
            //获取保存在SharedPreferences中的用户名、密码
            String username = preferences.getString("name","");
            String password = preferences.getString("pswd","");
            //设置用户名、密码输入框中，填入preference中存储的用户名、密码
            activity_login_et_name.setText(username);
            activity_login_et_pswd.setText(password);
            //设置“记住密码”多选框为选中状态
            activity_login_cb_keep_password.setChecked(true);
        }else{         //如果记住密码处于非选中状态，则清空所有文本输入框和复选框内容
            activity_login_et_name.setText("");
            activity_login_et_pswd.setText("");
            //设置“记住密码”多选框为未选中状态
            activity_login_cb_keep_password.setChecked(false) ;
        }

    }

    //【记住密码功能 - 写入】
    //点击登陆按钮后，确认“记住密码”复选框是否被选中。
    // 若选中，则向SharedPreferences保存该状态，并保存用户名、密码
    // 若未选中，则向SharedPreferences保存该状态
    private void writeSharedPreferences(String name, String pswd) {
        //获取SharedPreferences对象，并创建Editor编辑器对象
        SharedPreferences preferences = getSharedPreferences("MusicUserRecord", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //判断“记住密码”复选框是否被选中
        if(activity_login_cb_keep_password.isChecked()){    //如果“记住密码”被选中
            editor.putBoolean("keep",true);     //“记住密码”状态
            editor.putString("name",name);      //用户输入的用户名
            editor.putString("pswd",pswd);      //用户输入的密码
        }else{
            editor.putBoolean("keep",false);     //“记住密码”状态
        }
        //提交保存
        editor.commit();

    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
