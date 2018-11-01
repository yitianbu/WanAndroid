package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weifeng.wanandroid.adapter.MineViewPagerAdapter;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.utils.DensityUtil;
import com.weifeng.wanandroid.utils.Preferences;


public class MineFragment extends Fragment {
    private View rootView;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollaspsingToolBarLayout;
    private LinearLayout mLoginContentLl;
    private ImageView mUserHeadImg;
    private TextView mNickNameTv;
    private TextView mUserSignTv;
    private Toolbar mToolbar;
    private ImageView mBackIv;
    private ButtonBarLayout buttonBarLayout;
    private ImageView mToolBarAvatarIv;
    private TextView mToolBarNameTv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MineViewPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(rootView == null){
            initView(view);
        }
        initViewPager();
        initListener();
        initUserInfo();
    }

    private void initUserInfo() {
        if(!TextUtils.isEmpty(Preferences.getInstance().getUserName()) && Preferences.getInstance().getCookies()!=null){
            mNickNameTv.setText(Preferences.getInstance().getUserName());
        }else {
            mNickNameTv.setText("未登录");
        }
    }

    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                iv_parallax.setTranslationY(verticalOffset);
                //200是appbar的高度
                if (Math.abs(verticalOffset) == DensityUtil.dp2px(200) - mToolbar.getHeight()) {//关闭
                    buttonBarLayout.setVisibility(View.VISIBLE);
                    mCollaspsingToolBarLayout.setContentScrimResource(R.color.white);
                    mBackIv.setBackgroundResource(R.drawable.back_black);
                } else {  //展开
                    buttonBarLayout.setVisibility(View.INVISIBLE);
                    mCollaspsingToolBarLayout.setContentScrimResource(R.color.transparent);
                    mBackIv.setBackgroundResource(R.drawable.back_white);
                }
            }
        });
    }

    private void initViewPager() {
        mPagerAdapter  = new MineViewPagerAdapter((this.getActivity()).getSupportFragmentManager()) ;
        mPagerAdapter.addFragment(new Fragment_One(), "收藏");
        mPagerAdapter.addFragment(new Fragment_One(), "ToDo");
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mViewPager.setCurrentItem(1);
//        for (int i = 0; i <mPagerAdapter.getCount() ; i++) {
//            mTabLayout.addTab(mTabLayout.newTab().setText(mPagerAdapter.getPageTitle(i)));
//        }
    }

    private void initView(View view) {
        mAppBarLayout = view.findViewById(R.id.app_bar);
        mCollaspsingToolBarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        mLoginContentLl = view.findViewById(R.id.ll_login_content);
        mUserHeadImg = view.findViewById(R.id.img_user_head);
        mNickNameTv = view.findViewById(R.id.tv_nick_name);
        mUserSignTv = view.findViewById(R.id.tv_user_sign);
        mToolbar = view.findViewById(R.id.toolbar);
        mBackIv = view.findViewById(R.id.iv_back);
        buttonBarLayout = view.findViewById(R.id.buttonBarLayout);
        mToolBarAvatarIv = view.findViewById(R.id.iv_toolbar_avatar);
        mToolBarNameTv = view.findViewById(R.id.tv_toolbar_name);
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.view_pager);
        ((AppCompatActivity)this.getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

}
