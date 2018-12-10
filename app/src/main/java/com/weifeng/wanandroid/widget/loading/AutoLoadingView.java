package com.weifeng.wanandroid.widget.loading;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.weifeng.wanandroid.R;


/**
 * 显示加载进度及错误信息
 *
 * @author xiaohu 2011-11-18
 */
public class AutoLoadingView extends LinearLayout {

    private LayoutInflater mInflater;
    private ImageView loadingImg;


    private ILoadingAnimation animation;

    public AutoLoadingView(Context context) {
        this(context, null);
    }

    public AutoLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(getContext());
        mInflater.inflate(R.layout.autoloading_view, this);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.parseColor("#ffffff"));
        loadingImg = findViewById(R.id.load_progress);
        animation = new DefaultLoadingAnimation(context);
        animation.bind(loadingImg);
        showLoading();
    }

    public void showLoading() {
        this.setVisibility(VISIBLE);
        loadingImg.setVisibility(VISIBLE);
        animation.reset();
        animation.start();
    }

    public void dismissLoading() {
        this.setVisibility(GONE);
        loadingImg.setVisibility(GONE);
        animation.end();
    }
}
