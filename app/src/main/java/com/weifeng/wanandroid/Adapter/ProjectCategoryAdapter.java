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
import com.weifeng.wanandroid.repositiry.response.ProjectResponse;
import com.weifeng.wanandroid.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_COLLECT;
import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_ID;


/**
 * @anthor weifeng
 * @time 2018/9/19 下午3:10
 */
public class ProjectCategoryAdapter extends BaseRecyclerAdapter<ProjectCategoryAdapter.ArticleViewHolder> {

    private List<ProjectResponse.ProjectBean> projectBeans = new ArrayList<>();
    private Context context;

    public ProjectCategoryAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ArticleViewHolder getViewHolder(View view) {
        return new ArticleViewHolder(view, false);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        return new ArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ll_article_item, parent, false), true);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position, boolean isItem) {
        ArticleViewHolder articleHolder = holder;
        articleHolder.authorNameTv.setText(projectBeans.get(position).author);
        articleHolder.contentTv.setText(TimeUtil.getDate(projectBeans.get(position).publishTime));
        articleHolder.chapterNameTv.setText(projectBeans.get(position).chapterName + "/" + projectBeans.get(position).superChapterName);
        articleHolder.articleNameTv.setText(projectBeans.get(position).title);
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_default_head).bitmapTransform(new CircleCrop());
        Glide.with(context).load("").thumbnail(Glide.with(context).load((R.drawable.ic_default_head))).apply(options).into(articleHolder.authorAvatarImg);
        if (!TextUtils.isEmpty(projectBeans.get(position).envelopePic)) {
            articleHolder.envelopePicImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(projectBeans.get(position).envelopePic).into(articleHolder.envelopePicImg);
        } else {
            articleHolder.envelopePicImg.setVisibility(View.GONE);
        }
        articleHolder.totalContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                projectBeans.get(position).collect = true;
                intent.putExtra("url", projectBeans.get(position).link);
                intent.putExtra(ARTICLE_ID, projectBeans.get(position).id);
                intent.putExtra(ARTICLE_COLLECT,projectBeans.get(position).collect);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getAdapterItemCount() {
        return projectBeans.size();
    }

    public void setProjectBeans(List<ProjectResponse.ProjectBean> projectBeans) {
        this.projectBeans.addAll(projectBeans);
        notifyDataSetChanged();
    }

    public void clearProjectBeans() {
        this.projectBeans.clear();
        notifyDataSetChanged();
    }

    public static final class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView authorAvatarImg;
        public TextView authorNameTv;
        public TextView contentTv;
        public TextView chapterNameTv;
        public TextView articleNameTv;
        public ImageView envelopePicImg;
        public View totalContentView;

        public ArticleViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                authorAvatarImg = itemView.findViewById(R.id.img_author_avatar);
                authorNameTv = itemView.findViewById(R.id.tv_author_name);
                contentTv = itemView.findViewById(R.id.tv_publish_time);
                chapterNameTv = itemView.findViewById(R.id.tv_chapter_name);
                articleNameTv = itemView.findViewById(R.id.tv_article_name);
                envelopePicImg = itemView.findViewById(R.id.img_envelope_pic);
                totalContentView = itemView;
            }
        }
    }
}
