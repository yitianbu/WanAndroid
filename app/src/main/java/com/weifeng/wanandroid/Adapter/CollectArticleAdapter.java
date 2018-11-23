package com.weifeng.wanandroid.adapter;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.activity.WebViewActivity;
import com.weifeng.wanandroid.model.ArticleContentItem;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.callback.ThorCallback;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesInStationResponse;
import com.weifeng.wanandroid.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.weifeng.wanandroid.activity.WebViewActivity.ARTICLE_EXTRA;

/**
 * @anthor weifeng
 * @time 2018/9/19 下午3:10
 */
public class CollectArticleAdapter extends BaseRecyclerAdapter<CollectArticleAdapter.ArticleViewHolder> {

    private List<ArticleContentItem> articleItems = new ArrayList<>();
    private Context context;

    public CollectArticleAdapter(Context context) {
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
        articleHolder.authorNameTv.setText(articleItems.get(position).author);
        articleHolder.publishTimeTv.setText(TimeUtil.getDate(articleItems.get(position).publishTime));
        articleHolder.chapterNameTv.setText(articleItems.get(position).chapterName + "/" + articleItems.get(position).superChapterName);
        articleHolder.articleNameTv.setText(articleItems.get(position).title);
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ic_default_head).bitmapTransform(new CircleCrop());
        Glide.with(context).load("").thumbnail(Glide.with(context).load((R.drawable.ic_default_head))).apply(options).into(articleHolder.authorAvatarImg);
        if (!TextUtils.isEmpty(articleItems.get(position).envelopePic)) {
            articleHolder.envelopePicImg.setVisibility(View.VISIBLE);
            Glide.with(context).load(articleItems.get(position).envelopePic).into(articleHolder.envelopePicImg);
        } else {
            articleHolder.envelopePicImg.setVisibility(View.GONE);
        }
        articleHolder.totalContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                articleItems.get(position).collect = true;
                intent.putExtra("url", articleItems.get(position).link);
                intent.putExtra(ARTICLE_EXTRA, articleItems.get(position));
                context.startActivity(intent);
            }
        });
        articleHolder.totalContentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("取消收藏")
                        .setMessage("确认要取消该收藏么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelCollect(position);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

    }

    private void cancelCollect(final int position) {
        RetrofitClient.getInstance().getService(APIService.class).cancelCollectStationArticle(articleItems.get(position).originId).enqueue(new ThorCallback() {
            @Override
            public void onSuccess(Response response) {
                articleItems.remove(position);
                CollectArticleAdapter.this.notifyItemRemoved(position);
                CollectArticleAdapter.this.notifyItemRangeChanged(position, getItemCount());
            }

            @Override
            public void onFailure(ErrorMessage errorMessage) {

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

    public static final class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView authorAvatarImg;
        public TextView authorNameTv;
        public TextView publishTimeTv;
        public TextView chapterNameTv;
        public TextView articleNameTv;
        public ImageView envelopePicImg;
        public View totalContentView;

        public ArticleViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                authorAvatarImg = itemView.findViewById(R.id.img_author_avatar);
                authorNameTv = itemView.findViewById(R.id.tv_author_name);
                publishTimeTv = itemView.findViewById(R.id.tv_publish_time);
                chapterNameTv = itemView.findViewById(R.id.tv_chapter_name);
                articleNameTv = itemView.findViewById(R.id.tv_article_name);
                envelopePicImg = itemView.findViewById(R.id.img_envelope_pic);
                totalContentView = itemView;
            }
        }
    }
}
