package com.weifeng.wanandroid.holder;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.weifeng.wanandroid.R;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * @anthor weifeng
 * @time 2018/9/28 下午7:31
 */
public class LocalImageHolderView extends Holder<String> {
    private ImageView imageView;

    public LocalImageHolderView(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_head_pic);
    }

    @Override
    protected void initView(View itemView) {
        imageView = itemView.findViewById(R.id.img_head_pic);
    }

    @Override
    public void updateUI(String data) {
//        imageView.setImageResource(data); glide 加载数据
        if (imageView != null) {
            Glide.with(imageView.getContext()).load(data).into(imageView);
        }
    }
}