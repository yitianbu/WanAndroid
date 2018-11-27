package com.weifeng.wanandroid.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.activity.WebViewActivity;
import com.weifeng.wanandroid.model.ArticleContentItem;

import java.util.ArrayList;
import java.util.List;

import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_COLLECT;
import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_ID;


/**
 * @anthor weifeng
 * @time 2018/9/19 下午3:10
 */
public class CollectArticleAdapter extends BaseRecyclerAdapter<CollectArticleAdapter.ProjectViewHolder> {

    private List<ArticleContentItem> articleItems = new ArrayList<>();
    private Context context;

    public CollectArticleAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ProjectViewHolder getViewHolder(View view) {
        return new ProjectViewHolder(view, false);
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new ProjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ll_project_item, parent, false), true);
    }

    @Override
    public void onBindViewHolder(final ProjectViewHolder holder, final int position, boolean isItem) {
        ProjectViewHolder articleHolder = holder;
        articleHolder.titleTv.setText(articleItems.get(position).title);
        articleHolder.contentTv.setText(articleItems.get(position).desc);
        articleHolder.authorTv.setText(articleItems.get(position).author);
        articleHolder.timeTv.setText(articleItems.get(position).niceDate);
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_default_head).bitmapTransform(new CircleCrop());
        Glide.with(context).load("").thumbnail(Glide.with(context).load((R.drawable.ic_default_head))).apply(options).into(articleHolder.envelopeImg);
        if (!TextUtils.isEmpty(articleItems.get(position).envelopePic)) {
            articleHolder.envelopeImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(articleItems.get(position).envelopePic).into(articleHolder.envelopeImg);
        } else {
            articleHolder.envelopeImg.setVisibility(View.GONE);
        }
        articleHolder.totalContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                articleItems.get(position).collect = true;
                intent.putExtra("url", articleItems.get(position).link);
                intent.putExtra(ARTICLE_ID, articleItems.get(position).id);
                intent.putExtra(ARTICLE_COLLECT,articleItems.get(position).collect);
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getAdapterItemCount() {
        return articleItems.size();
    }

    public void setArticleItems(List<ArticleContentItem> articleItems) {
        this.articleItems.addAll(articleItems);
        notifyDataSetChanged();
    }

    public void clearArticeItems() {
        this.articleItems.clear();
        notifyDataSetChanged();
    }

    public static final class ProjectViewHolder extends RecyclerView.ViewHolder {
        public ImageView envelopeImg;
        public TextView titleTv;
        public TextView contentTv;
        public TextView authorTv;
        public TextView timeTv;
        public View totalContentView;

        public ProjectViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                envelopeImg = itemView.findViewById(R.id.img_envelope_pic);
                titleTv = itemView.findViewById(R.id.tv_title);
                contentTv = itemView.findViewById(R.id.tv_content);
                authorTv = itemView.findViewById(R.id.tv_author);
                timeTv = itemView.findViewById(R.id.tv_time);
                totalContentView = itemView;
            }
        }
    }
}
