package cn.edu.sict.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.sict.bean.MineGridBean;
import cn.edu.sict.musicplayer.R;

public class ConfigGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MineGridBean> data;

    //构造方法
    public ConfigGridAdapter(Context context, ArrayList<MineGridBean> data) {
        this.context = context;
        this.data = data;
    }

    //获取小格数
    @Override
    public int getCount() {
        return data.size();
    }

    //获取第position条数据
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    //获取第position条数据的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取第position条数据展示出来的view
    //利用缓存原理重写的getView方法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //使用LayoutInflater布局加载器，将小布局xml文件加载并转化为view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.config_grid_item, null);

        //找到小布局view中的控件
        ImageView config_grid_item_iv = (ImageView) view.findViewById(R.id.config_grid_item_iv);
        TextView config_grid_item_tv = (TextView) view.findViewById(R.id.config_grid_item_tv);

        //填充数据
        config_grid_item_tv.setText(data.get(position).getTitle());
        config_grid_item_iv.setImageResource(data.get(position).getImgSrc());

        //返回已填写好数据的view
        return view;
    }

}
