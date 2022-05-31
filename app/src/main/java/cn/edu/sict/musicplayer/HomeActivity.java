package cn.edu.sict.musicplayer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 主页面
 */
public class HomeActivity extends AppCompatActivity {

    private RadioButton activity_home_rb_mine;
    private RadioButton activity_home_rb_lib;
    private RadioButton activity_home_rb_config;
    private RadioGroup activity_home_rg_title;
    private ViewPager2 activity_home_vp;
    private ImageView activity_home_iv_genre;
    private TextView activity_home_tv_song;
    private ImageView activity_home_iv_play;
    private ImageView activity_home_iv_next;
    private ImageView activity_home_iv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();

        activity_home_rg_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.activity_home_rb_config){
                    logout();
                }
            }
        });
    }
    private void initView() {
        activity_home_rb_mine = (RadioButton) findViewById(R.id.activity_home_rb_mine);
        activity_home_rb_lib = (RadioButton) findViewById(R.id.activity_home_rb_lib);
        activity_home_rb_config = (RadioButton) findViewById(R.id.activity_home_rb_config);
        activity_home_rg_title = (RadioGroup) findViewById(R.id.activity_home_rg_title);
        activity_home_vp = (ViewPager2)findViewById(R.id.activity_home_vp);
        activity_home_iv_genre = (ImageView) findViewById(R.id.activity_home_iv_genre);
        activity_home_tv_song = (TextView) findViewById(R.id.activity_home_tv_song);
        activity_home_iv_play = (ImageView) findViewById(R.id.activity_home_iv_play);
        activity_home_iv_next = (ImageView) findViewById(R.id.activity_home_iv_next);
        activity_home_iv_list = (ImageView) findViewById(R.id.activity_home_iv_list);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("确定退出登录？").setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("手滑了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
