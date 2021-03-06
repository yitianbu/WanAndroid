package com.weifeng.wanandroid.activity;

import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.weifeng.wanandroid.fragment.NavigationFragment;
import com.weifeng.wanandroid.fragment.ProjectMainFragment;
import com.weifeng.wanandroid.fragment.FindFragment;
import com.weifeng.wanandroid.fragment.MineFragment;

import com.weifeng.wanandroid.R;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{
    public static final String TAG_FIND ="find";
    public static final String TAG_PROJECT = "project";
    public static final String TAG_NAVIGATION = "navigation";
    public static final String TAG_MINE ="mine";

    private FragmentTabHost mTabHost;
    private TabWidget mTabWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);
        mTabHost.setOnTabChangedListener(this);
        mTabWidget = mTabHost.getTabWidget();
        mTabWidget.setDividerDrawable(android.R.color.transparent);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_FIND).setIndicator(getTabView(TAG_FIND)),
                FindFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_PROJECT).setIndicator(getTabView(TAG_PROJECT)),
                ProjectMainFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_NAVIGATION).setIndicator(getTabView(TAG_NAVIGATION)),
                NavigationFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAG_MINE).setIndicator(getTabView(TAG_MINE)),
                MineFragment.class, null);
        mTabHost.setCurrentTab(0);
    }

    private View getTabView(String tag) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_wan_main_tab, null);
        TextView tabTitleTV = view.findViewById(R.id.tv_title);
        ImageView tabIconImg = view.findViewById(R.id.img_icon);
        if(TAG_FIND.equals(tag)){
            tabTitleTV.setText("发现");
            tabIconImg.setImageResource(R.drawable.wan_main_tab_find_selector);
            return view;
        }else if(TAG_PROJECT.equals(tag)){
            tabTitleTV.setText("项目");
            tabIconImg.setImageResource(R.drawable.wan_main_tab_project_selector);
            return view;
        }else if(TAG_NAVIGATION.equals(tag)){
            tabTitleTV.setText("导航");
            tabIconImg.setImageResource(R.drawable.wan_main_tab_navigation_selector);
            return view;
        }else if(TAG_MINE.equals(tag)){
            tabTitleTV.setText("我的");
            tabIconImg.setImageResource(R.drawable.wan_main_tab_mine_selector);
            return view;
        }
        return null;
    }



    @Override
    public void onTabChanged(String tabId) {
        if(TAG_FIND.equals(tabId)) {
            mTabHost.setCurrentTab(0);
        }else if(TAG_PROJECT.equals(tabId)){
            mTabHost.setCurrentTab(1);
        }else if(TAG_NAVIGATION.equals(tabId)){
            mTabHost.setCurrentTab(2);
        }else if(TAG_MINE.equals(tabId)){
            mTabHost.setCurrentTab(3);
        }
    }

}
