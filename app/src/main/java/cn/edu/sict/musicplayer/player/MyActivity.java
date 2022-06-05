package cn.edu.sict.musicplayer.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

import cn.edu.sict.musicplayer.R;
import cn.edu.sict.musicplayer.adapter.MyGridAdapter;
import cn.edu.sict.musicplayer.bean.MyGridBean;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLocal;
    private GridView gridViewTag;
    private GridView gridViewList;

    int[] imgSrcs_tag = {R.drawable.icon_running,R.drawable.icon_study,R.drawable.icon_driving,
                        R.drawable.icon_travel,R.drawable.icon_party,R.drawable.icon_teatime,
                        R.drawable.icon_sleep,R.drawable.icon_reading,R.drawable.icon_thinking};
    String[] titles_tag = {"跑步","学习","开车","旅行","聚会","下午茶","睡眠","阅读","冥思"};
    int[] imgSrcs_list = {R.drawable.icon_rain,R.drawable.icon_sunrise,R.drawable.icon_nature,R.drawable.icon_ontheway};
    String[] titles_list = {"雨天","晴天","大自然","在路上"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        buttonLocal = (Button) findViewById(R.id.button_local);
        gridViewTag = (GridView) findViewById(R.id.gridView_tag);
        gridViewList = (GridView) findViewById(R.id.gridView_list);

        buttonLocal.setOnClickListener(this);

        initGridView(gridViewTag,imgSrcs_tag,titles_tag);
        initGridView(gridViewList,imgSrcs_list,titles_list);
    }

    private void initGridView(GridView gridView, int[] imgSrcs, String[] titles) {
        ArrayList<MyGridBean> dataList = initData(imgSrcs,titles);
        MyGridAdapter adapter = new MyGridAdapter(MyActivity.this,dataList);
        gridView.setAdapter(adapter);
    }

    private ArrayList<MyGridBean> initData(int[] imgSrcs, String[] titles) {
        ArrayList<MyGridBean> dataList = new ArrayList<>();
        for(int i=0;i<imgSrcs.length;i++){
            MyGridBean bean = new MyGridBean(imgSrcs[i],titles[i]);
            dataList.add(bean);
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_local:
                startActivity(new Intent(this,MusicListActivity.class));
                break;
        }
    }
}