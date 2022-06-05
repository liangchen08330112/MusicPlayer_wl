package cn.edu.sict.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.sict.musicplayer.R;
import cn.edu.sict.musicplayer.bean.MyGridBean;

public class MyGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<MyGridBean> list;

    public MyGridAdapter(Context context, ArrayList<MyGridBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.grid_item,null);
        ImageView iv = view.findViewById(R.id.grid_imageView);
        TextView tv = view.findViewById(R.id.grid_textView);
        MyGridBean bean = list.get(position);
        iv.setImageResource(bean.getImgSrc());
        tv.setText(bean.getTitle());
        return v;
    }
}