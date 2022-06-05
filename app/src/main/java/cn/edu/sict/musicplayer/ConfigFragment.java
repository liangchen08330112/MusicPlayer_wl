package cn.edu.sict.musicplayer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import cn.edu.sict.adapter.ConfigGridAdapter;
import cn.edu.sict.bean.MineGridBean;

public class ConfigFragment extends Fragment {
    private GridView activity_config_gv;
    private Button activity_config_btn_help;
    private Button activity_config_btn_feedback;
    private Button activity_config_btn_more;
    private Button activity_config_btn_logout;

    //宿主Activity
    HomeActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);
        initView(view);

        //获取宿主Activity
        activity = (HomeActivity) getActivity();

        //【设置GridView】
        //初始化显示内容
        ArrayList<MineGridBean> data = new ArrayList<MineGridBean>();
        data.add(new MineGridBean(R.drawable.icon_theme, "个性主题"));
        data.add(new MineGridBean(R.drawable.icon_clean, "清理空间"));
        data.add(new MineGridBean(R.drawable.icon_clock, "定时关闭"));
        data.add(new MineGridBean(R.drawable.icon_lists, "歌单回收站"));
        //创建Adapter并绑定给GridView
        ConfigGridAdapter gridAdapter = new ConfigGridAdapter(activity, data);
        activity_config_gv.setAdapter(gridAdapter);

        //对退出登录设置单击事件监听器
        activity_config_btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.创建AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                //2.设置对话框标题
                builder.setTitle("退出登录");
                //3.设置对话框内容
                builder.setMessage("是否确认退出登陆？");
                //4..设置对话框确认按钮
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
                //5.设置对话框中立按钮
                builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //6.根据builder创建AlertDialog
                AlertDialog dialog = builder.create();
                //7.将AlertDialog展示出来
                dialog.show();
            }
        });
        return view;
    }

    private void initView(View view) {
        this.activity_config_gv = (GridView) view.findViewById(R.id.activity_config_gv);
        this.activity_config_btn_help = (Button) view.findViewById(R.id.activity_config_btn_help);
        this.activity_config_btn_feedback = (Button) view.findViewById(R.id.activity_config_btn_feedback);
        this.activity_config_btn_more = (Button) view.findViewById(R.id.activity_config_btn_more);
        this.activity_config_btn_logout = (Button) view.findViewById(R.id.activity_config_btn_logout);

    }

}
