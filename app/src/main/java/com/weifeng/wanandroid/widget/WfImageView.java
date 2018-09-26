package com.weifeng.wanandroid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.weifeng.wanandroid.R;

/**
 * @anthor weifeng
 * @time 2018/9/19 下午5:09
 */
class WfImageView extends AppCompatImageView {
    private Drawable placeHolder;
    private Drawable errorHolder;
    private Context context;

    public WfImageView(Context context) {
        this(context,null);
    }

    public WfImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WfImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewProper(context,attrs);
        this.context = context;
    }

    private void initViewProper(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WfImageView);
        if(ta!=null){
            errorHolder = ta.getDrawable(R.styleable.WfImageView_error_holder);
            placeHolder = ta.getDrawable(R.styleable.WfImageView_default_holder);
            ta.recycle();
        }
    }

    public void load(String url){
        RequestOptions options = new RequestOptions().centerCrop().placeholder(placeHolder).error(errorHolder).bitmapTransform(new CircleCrop());
        Glide.with(context).load(url).apply(options).into(this);
    }

    public void setPlaceHolder(Drawable placeHolder) {
        this.placeHolder = placeHolder;
    }

    public void setErrorHolder(Drawable errorHolder) {
        this.errorHolder = errorHolder;
    }
}
