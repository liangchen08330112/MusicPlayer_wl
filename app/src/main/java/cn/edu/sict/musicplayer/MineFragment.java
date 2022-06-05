package cn.edu.sict.musicplayer;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import cn.edu.sict.adapter.MineGridAdapter;
import cn.edu.sict.bean.MineGridBean;
import cn.edu.sict.bean.Music;
import cn.edu.sict.util.MusicUtil;

public class MineFragment extends Fragment implements View.OnClickListener {

    private GridView activity_mine_gv_tag;
    private GridView activity_mine_gv_list;
    private Button activity_mine_btn_local;

    //第一步：数据准备
    //1.1 原始数据
    //1.1.1 准备标签九宫格的原始数据
    //图片
    int[] imgSrcs_tag = {R.drawable.icon_running, R.drawable.icon_study,
            R.drawable.icon_driving, R.drawable.icon_travel,
            R.drawable.icon_party, R.drawable.icon_teatime,
            R.drawable.icon_sleep, R.drawable.icon_reading,
            R.drawable.icon_thinking};
    //显示的标签
    String[] titles_tag = {"跑步", "学习", "开车", "旅行", "聚会", "下午茶", "睡眠", "阅读", "冥思"};

    //1.1.2 同样准备歌单九宫格的原始数据
    int[] imgSrcs_list = {R.drawable.icon_rain, R.drawable.icon_sunrise,
            R.drawable.icon_nature, R.drawable.icon_ontheway};
    String[] titles_list = {"雨天", "晴天", "大自然", "在路上"};

    //手机媒体库保存的音频信息
    ArrayList<Music> musicList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        activity_mine_gv_tag = (GridView) view.findViewById(R.id.activity_mine_gv_tag);
        activity_mine_gv_list = (GridView) view.findViewById(R.id.activity_mine_gv_list);

        //实现标签九宫格
        initGridView(activity_mine_gv_tag, imgSrcs_tag, titles_tag);

        //实现订阅歌单九宫格
        initGridView(activity_mine_gv_list, imgSrcs_list, titles_list);

        activity_mine_btn_local = (Button) view.findViewById(R.id.activity_mine_btn_local);
        activity_mine_btn_local.setOnClickListener(this);

        //调用MusicUtil的静态方法，获取手机媒体库保存的音频信息
        ArrayList<Music> musicList = MusicUtil.scanMusic(getActivity());
        //将音频集合的长度填写在本地音乐的歌曲数量上
        activity_mine_btn_local.setText("本地音乐  " + musicList.size() + "首");

    }

    //生成九宫格
    private void initGridView(GridView activity_mine_gv, int[] imgSrcs, String[] titles) {

        //第一步：数据准备
        //1.2 数据初始化
        //MineGridBean：在九宫格中的每个小格，都包含图片、标题、数量
        //因此data:9/6个小格中的图片、标题
        ArrayList<MineGridBean> dataList = initData(imgSrcs, titles);

        //第二步：创建适配器对象
        MineGridAdapter adapter = new MineGridAdapter(getActivity(), dataList);

        //第三步：将适配器绑定到GridView控件
        activity_mine_gv.setAdapter(adapter);

        //第四步：绑定事件监听器（当小格被点击时）
//        activity_mine_gv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    //GridView数据初始化：初始化dataList，即初始化九宫格中的内容。将原始数据的图标和标题放入ArrayList集合中
    private ArrayList<MineGridBean> initData(int[] imgSrcs, String[] titles) {
        ArrayList<MineGridBean> dataList = new ArrayList<>();
        for (int i = 0; i < imgSrcs.length; i++) {
            MineGridBean grid = new MineGridBean(imgSrcs[i], titles[i]);
            dataList.add(grid);
        }
        return dataList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_mine_btn_local:
                //点击“本地音乐”时跳转到MusicListActivity
                Intent intent = new Intent(getActivity(),MusicListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
