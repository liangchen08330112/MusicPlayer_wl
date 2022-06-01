package cn.edu.sict.musicplayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText activity_register_et_name;
    private EditText activity_register_et_pswd1;
    private EditText activity_register_et_pswd2;
    private Button activity_register_btn_register;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        db = openOrCreateDatabase("users.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists users(name varchar(20),pswd varchar(20),primary key(name))");
    }

    private void initView() {
        activity_register_et_name = (EditText) findViewById(R.id.activity_register_et_name);
        activity_register_et_pswd1 = (EditText) findViewById(R.id.activity_register_et_pswd1);
        activity_register_et_pswd2 = (EditText) findViewById(R.id.activity_register_et_pswd2);
        activity_register_btn_register = (Button) findViewById(R.id.activity_register_btn_register);

        activity_register_btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_btn_register:
                //当点击在注册按钮，则提交信息，此时调用submit方法
                submit();
                break;
        }
    }

    //点击注册按钮后被调用
    private void submit() {
        // validate 验证三个文本输入框的内容不为空
        String name = activity_register_et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String pswd1 = activity_register_et_pswd1.getText().toString().trim();
        if (TextUtils.isEmpty(pswd1)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pswd2 = activity_register_et_pswd2.getText().toString().trim();
        if (TextUtils.isEmpty(pswd2)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        //判断两次密码输入是否一致
        if(!pswd1.equals(pswd2)){
            Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        }else{
            try {
                //插入数据
                db.execSQL("insert into users(name,pswd) values(?,?)",new String[]{name,pswd1});
            }catch (Exception e){
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

            //将用户名、密码传递回LoginActivity

            //定义Intent对象并存入数据
            Intent data = getIntent();
            data.putExtra("name",name);
            data.putExtra("pswd",pswd1);

            //设置resultCode
            // 第一个参数：传入一个结果码
            // 第二个参数：回传的数据，一个Intent对象
            setResult(RESULT_OK,data);
            //关闭本页面，返回LoginActivity
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注册结束，关闭数据库
        db.close();
    }
}
