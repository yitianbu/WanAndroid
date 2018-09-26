package com.weifeng.wanandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.Utils.TimeUtil;
import com.weifeng.wanandroid.repositiry.response.ListArticlesResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/9/19 下午3:10
 */
public class ListArticlesAdapter extends RecyclerView.Adapter {
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;

    private List<ListArticlesResponse.ArticleItem> articleItems = new ArrayList<>();
    private Context context;

    public ListArticlesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ll_article_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArticleViewHolder articleHolder = (ArticleViewHolder) holder;
        articleHolder.authorNameTv.setText(articleItems.get(position).author);
        articleHolder.publishTimeTv.setText(TimeUtil.getDate(articleItems.get(position).publishTime));
        articleHolder.chapterNameTv.setText(articleItems.get(position).chapterName + "/" + articleItems.get(position).superChapterName);
        articleHolder.articleNameTv.setText(articleItems.get(position).title);
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_default_head).bitmapTransform(new CircleCrop());
        Glide.with(context).load("").thumbnail(Glide.with(context).load((R.drawable.ic_default_head))).apply(options).into(articleHolder.authorAvatarImg);
        if(!TextUtils.isEmpty(articleItems.get(position).envelopePic)){
            articleHolder.envelopePicImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(articleItems.get(position).envelopePic).into(articleHolder.envelopePicImg);
        }else {
            articleHolder.envelopePicImg.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return articleItems.size();
    }

    public void setArticleItems(List<ListArticlesResponse.ArticleItem> articleItems) {
        this.articleItems.addAll(articleItems);
        notifyDataSetChanged();
    }

    public static final class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView authorAvatarImg;
        public TextView authorNameTv;
        public TextView publishTimeTv;
        public TextView chapterNameTv;
        public TextView articleNameTv;
        public ImageView envelopePicImg;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            authorAvatarImg = itemView.findViewById(R.id.img_author_avatar);
            authorNameTv = itemView.findViewById(R.id.tv_author_name);
            publishTimeTv = itemView.findViewById(R.id.tv_publish_time);
            chapterNameTv = itemView.findViewById(R.id.tv_chapter_name);
            articleNameTv = itemView.findViewById(R.id.tv_article_name);
            envelopePicImg = itemView.findViewById(R.id.img_envelope_pic);
        }
    }
}
