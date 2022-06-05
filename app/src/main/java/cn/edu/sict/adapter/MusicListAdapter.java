package cn.edu.sict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cn.edu.sict.bean.Music;
import cn.edu.sict.musicplayer.R;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    Context context;
    ArrayList<Music> musicList;

    //构造方法：创建Adapter对象的同时为两个成员变量赋初值
    public MusicListAdapter(Context context, ArrayList<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    //创建ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //从上下文中获取LayoutInflater对象
        LayoutInflater inflater = LayoutInflater.from(context);
        //使用LayoutInflater对象，加载小布局并转化为View视图
        View view = inflater.inflate(R.layout.music_list_item, null);
        //创建待返回的ViewHolder对象
        ViewHolder holder = new ViewHolder(view);

        //事件处理器
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //返回ViewHolder对象
        return holder;
    }

    //向ViewHolder中的组件里填充数据
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //获取本列表项的数据
        Music music = musicList.get(position);
        //将列表项的数据填充到控件中
        holder.music_list_item_tv_song.setText(music.getName());
        holder.music_list_item_tv_singer.setText(music.getSinger());
    }

    //一共有多少列表项
    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView music_list_item_tv_song;
        public TextView music_list_item_tv_singer;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.music_list_item_tv_song = (TextView) rootView.findViewById(R.id.music_list_item_tv_song);
            this.music_list_item_tv_singer = (TextView) rootView.findViewById(R.id.music_list_item_tv_singer);
        }

    }
}
