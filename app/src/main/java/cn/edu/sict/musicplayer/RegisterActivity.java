package cn.edu.sict.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 注册页面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText activity_register_et_name;
    private EditText activity_register_et_pswd1;
    private EditText activity_register_et_pswd2;
    private Button activity_register_btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
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
}
