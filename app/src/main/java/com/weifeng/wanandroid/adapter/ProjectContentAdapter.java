package com.weifeng.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.activity.WebViewActivity;
import com.weifeng.wanandroid.model.ArticleBean;
import com.weifeng.wanandroid.repositiry.response.NaviResponse;

import java.util.List;
import java.util.zip.Inflater;

/**
 * @anthor weifeng
 * @time 2018/10/29 下午5:23
 */
public class ProjectContentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ArticleBean> articles;

    public ProjectContentAdapter(FragmentActivity activity) {
        mContext = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_content_nav_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
        contentViewHolder.nameTv.setText(articles.get(position).getTitle());
        contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url",articles.get(position).getLink());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(articles == null){
            return 0;
        }
        return articles.size();
    }

    public void setContentListData(List<ArticleBean> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }


    public static class ContentViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTv;
        public View itemView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_nav_title);
            this.itemView = itemView;
        }
    }
}
