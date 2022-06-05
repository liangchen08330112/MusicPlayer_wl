package cn.edu.sict.musicplayer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.edu.sict.adapter.MusicListAdapter;
import cn.edu.sict.bean.Music;
import cn.edu.sict.util.MusicUtil;

/**
 * 音乐列表页面
 */
public class MusicListActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView activity_music_list_iv_return;
    private TextView activity_music_list_tv_return;
    private RecyclerView activity_music_list_rv;
    private TextView activity_music_list_tv_current_time;
    private SeekBar activity_music_list_sb;
    private TextView activity_music_list_tv_finish_time;
    private TextView activity_music_list_tv_song;
    private TextView activity_music_list_tv_singer;
    private TextView activity_music_list_tv_duration;
    private ImageView activity_music_list_iv_prev;
    private ImageView activity_music_list_iv_play;
    private ImageView activity_music_list_iv_next;

    //音乐列表数据
    ArrayList<Music> musicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        initView();
    }

    private void initView() {
        activity_music_list_iv_return = (ImageView) findViewById(R.id.activity_music_list_iv_return);
        activity_music_list_tv_return = (TextView) findViewById(R.id.activity_music_list_tv_return);
        activity_music_list_rv = (RecyclerView) findViewById(R.id.activity_music_list_rv);
        activity_music_list_tv_current_time = (TextView) findViewById(R.id.activity_music_list_tv_current_time);
        activity_music_list_sb = (SeekBar) findViewById(R.id.activity_music_list_sb);
        activity_music_list_tv_finish_time = (TextView) findViewById(R.id.activity_music_list_tv_finish_time);
        activity_music_list_tv_song = (TextView) findViewById(R.id.activity_music_list_tv_song);
        activity_music_list_tv_singer = (TextView) findViewById(R.id.activity_music_list_tv_singer);
        activity_music_list_tv_duration = (TextView) findViewById(R.id.activity_music_list_tv_duration);
        activity_music_list_iv_prev = (ImageView) findViewById(R.id.activity_music_list_iv_prev);
        activity_music_list_iv_play = (ImageView) findViewById(R.id.activity_music_list_iv_play);
        activity_music_list_iv_next = (ImageView) findViewById(R.id.activity_music_list_iv_next);

        initMusicList();    //初始化音乐列表
        initListener();     //初始化各种监听器
    }

    //初始化各种监听器
    private void initListener() {
        activity_music_list_iv_return.setOnClickListener(this);
        activity_music_list_tv_return.setOnClickListener(this);
    }

    //初始化音乐列表
    private void initMusicList() {
        initData();
        //创建Adapter
        MusicListAdapter adapter = new MusicListAdapter(this, musicList);
        //绑定Adapter到RecyclerView上
        activity_music_list_rv.setAdapter(adapter);
        //设置LayoutManager
        //参数1，上下文对象；参数2，布局方向；参数3，是否数据倒转（即后面的数据显示在前面）
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //将LayoutManager绑定在RecyclerView上
        activity_music_list_rv.setLayoutManager(layoutManager);
    }

    //初始化音乐列表数据
    private void initData() {
//        musicList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Music music = new Music("Song" + i, "Singer" + i, null);
//            musicList.add(music);
//        }

        //通过调用MusicUtil的静态方法，获取系统媒体库的音频信息集合
        musicList = MusicUtil.scanMusic(this);
    }

    //单击事件监听器的事件处理方法
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_music_list_iv_return:
                finish();
                break;
            case R.id.activity_music_list_tv_return:
                finish();
                break;
        }

    }
}
