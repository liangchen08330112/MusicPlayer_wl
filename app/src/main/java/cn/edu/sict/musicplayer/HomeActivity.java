package cn.edu.sict.musicplayer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

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

    ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }


    private void initView() {
        activity_home_rb_mine = (RadioButton) findViewById(R.id.activity_home_rb_mine);
        activity_home_rb_lib = (RadioButton) findViewById(R.id.activity_home_rb_lib);
        activity_home_rb_config = (RadioButton) findViewById(R.id.activity_home_rb_config);
        activity_home_rg_title = (RadioGroup) findViewById(R.id.activity_home_rg_title);
        activity_home_vp = (ViewPager2) findViewById(R.id.activity_home_vp);
        activity_home_iv_genre = (ImageView) findViewById(R.id.activity_home_iv_genre);
        activity_home_tv_song = (TextView) findViewById(R.id.activity_home_tv_song);
        activity_home_iv_play = (ImageView) findViewById(R.id.activity_home_iv_play);
        activity_home_iv_next = (ImageView) findViewById(R.id.activity_home_iv_next);
        activity_home_iv_list = (ImageView) findViewById(R.id.activity_home_iv_list);

        //加载ViewPager2中的Fragment
        //初始化Fragment列表
        initData();
        //创建适配器并绑定到ViewPager
        HomePagerAdapter adapter = new HomePagerAdapter(this);
        activity_home_vp.setAdapter(adapter);
        //为ViewPager绑定事件监听器：当滑到某个页面时，标题栏对应标题tag变黄色
        activity_home_vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            //当页面滑动时，在屏幕滚动过程中不断被调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            //当第position个界面被选中时，调用此方法
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        activity_home_rb_mine.setChecked(true);
                        break;
                    case 1:
                        activity_home_rb_lib.setChecked(true);
                        break;
                    case 2:
                        activity_home_rb_config.setChecked(true);
                        break;
                }
            }
            //当手指操作屏幕时（状态发生变化），调用此方法
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //设置RadioGroup
        //为rg单选按钮组绑定事件监听器：当选中某个选项卡，就使ViewPager显示对应的Fragment
        activity_home_rg_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_home_rb_mine:
                        activity_home_vp.setCurrentItem(0);
                        break;
                    case R.id.activity_home_rb_lib:
                        activity_home_vp.setCurrentItem(1);
                        break;
                    case R.id.activity_home_rb_config:
                        activity_home_vp.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    //初始化fragmentList，加入ViewPager要加载的三个Fragment页面数据
    private void initData() {
        fragmentList = new ArrayList<>();
        MineFragment mineFragment = new MineFragment();
        NetFragment netFragment = new NetFragment();
        ConfigFragment configFragment = new ConfigFragment();
        fragmentList.add(mineFragment);
        fragmentList.add(netFragment);
        fragmentList.add(configFragment);
    }

    //ViewPager的适配器
    class HomePagerAdapter extends FragmentStateAdapter{
        //构造方法
        public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        //根据第position条数据创建并加入对应的Fragment
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        //Fragment的数量
        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}
