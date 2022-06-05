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

public class MineGridAdapter extends BaseAdapter {

    Context context;        //上下文对象
    ArrayList<MineGridBean> dataList;   //要展示的数据列表

    //构造函数：创建本对象的同时为两个成员变量赋值
    public MineGridAdapter(Context context, ArrayList<MineGridBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    //获取列表有多长（小格数量）
    @Override
    public int getCount() {
        return dataList.size();
    }

    //获取第position条数据
    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    //获取第position项的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取第position条数据显示出来的view视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.布局加载器（通道膨胀器）：读取小布局，并转化为一个view
        //从当前页面中获取一个通道膨胀器
        LayoutInflater inflater = LayoutInflater.from(context);
        //利用通道膨胀器把小布局“膨胀”为一个view
        View view = inflater.inflate(R.layout.mine_grid_item,null);

        //2.找到view中的控件
        ImageView iv = view.findViewById(R.id.mine_grid_item_iv);
        TextView tv = view.findViewById(R.id.mine_grid_item_tv);

        //3.获取本项数据，并用数据填充控件
        MineGridBean item = dataList.get(position);
        iv.setImageResource(item.getImgSrc());
        tv.setText(item.getTitle());

        //4.返回填充好的view
        return view;
    }
}
