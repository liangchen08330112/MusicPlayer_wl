package cn.edu.sict.musicplayer.player;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.edu.sict.musicplayer.R;

/**
 * 登录页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText activity_login_et_name;
    private EditText activity_login_et_pswd;
    private Button activity_login_btn_register;
    private Button activity_login_btn_login;
    private CheckBox activity_login_cb_keep_password;

    SQLiteDatabase db;

    //定义常量，用于作为requestCode，确认是否由注册界面返回
    public static final int REGISTER_CODE = 0;
    //用于判断是否从注册页面回传了数据回到本页面。
    //如果是的话，就不应该运行自动填充功能。
    int requestCode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        db = openOrCreateDatabase("users.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists users(name varchar(20),pswd varchar(20),primary key(name))");
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(requestCode != REGISTER_CODE) {
            //调用readSharedPreferences()，用于记住密码
            readSharedPreferences();
        }
    }

    private void readSharedPreferences() {
        SharedPreferences sp = getSharedPreferences("MusicPlayer",MODE_PRIVATE);
        //判断SharedPreferences的记住密码状态
        if(sp.getBoolean("keep",false)){
            //获取SharedPreferences中保存的用户名和密码
            String name = sp.getString("name","");
            //将获取来的用户名、密码和记住密码状态，填充到文本框和复选框上
            String password = sp.getString("pswd","");
            activity_login_et_name.setText(name);
            activity_login_et_pswd.setText(password);
            activity_login_cb_keep_password.setChecked(true);
            //如果没有记住密码，文本框没有内容，复选框会是未选中状态。
        }else {
            activity_login_et_name.setText("");
            activity_login_et_pswd.setText("");
            activity_login_cb_keep_password.setChecked(false);
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
        // validate
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

        try {
            //核验用户输入的信息是否与数据库中的用户名、密码核对成功。
            //根据用户名获取数据库中存储的密码
            Cursor cursor = db.rawQuery("select pswd from users where name=?",new String[]{name});
            //取出的结果放在pswd_check字符串中
            String pswd_check = null;
            //判断取出的数据有几行，一般只有1行或0行两种可能。
            if(cursor.getCount()==1){
                //跳转到第一行
                cursor.moveToFirst();
                //取出第一行第一列的数据
                pswd_check = cursor.getString(0);
                if(pswd_check.equals(pswd)){
                    //记住密码，写入SharedPreferences文件
                    writeSharedPreferences(name,pswd);
                    Log.i("SUCCESS","成功");
                    //跳转到HomeActivity主页面
                    startActivity(new Intent(LoginActivity.this,MyActivity.class));
                }else {
                    Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this,"用户不存在",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Log.e("EXCEPTION","异常");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setMessage("系统错误，请重试。").setCancelable(true)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // TODO validate success, do something
    }


    //回调方法：当从注册页面点击注册按钮返回本页面时
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //无论是从主页面还是注册页面返回本页，都重新初始化requestCode这个成员变量的值为-1。
        this.requestCode = -1;

        //判断是否由注册页面点击注册按钮后返回
        //这里建议使用resultCode进行判断，因为物理返回按键也可以从注册页面返回本页，而其用户名密码是未检验的
        if(resultCode == RESULT_OK){
            //获取传回来的数据，即data
            if (data != null){
                //填充两个输入框为注册页面传回来的用户名、密码
                activity_login_et_name.setText(data.getStringExtra("name"));
                activity_login_et_pswd.setText(data.getStringExtra("pswd"));

                this.requestCode = requestCode;
            }
        }
    }
    //记住密码，写入SharedPreferences
    private void writeSharedPreferences(String name, String pswd) {
        //获取SharedPreferences和编辑器对象
        SharedPreferences sp = getSharedPreferences("MusicPlayer",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //是否选中了记住密码？
        if(activity_login_cb_keep_password.isChecked()==true){
            editor.putString("name",name);
            editor.putString("pswd",pswd);
            //选中了
            editor.putBoolean("keep",true);
        }else {
            //没选中
            editor.putBoolean("keep",false);
        }
        //提交。
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
