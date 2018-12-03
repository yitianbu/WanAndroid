package com.weifeng.wanandroid.fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.weifeng.wanandroid.activity.LoginActivity;
import com.weifeng.wanandroid.activity.WebViewActivity;
import com.weifeng.wanandroid.adapter.ListArticlesAdapter;
import com.weifeng.wanandroid.holder.LocalImageHolderView;
import com.weifeng.wanandroid.model.ArticleHeadItem;
import com.weifeng.wanandroid.repositiry.response.ArticlesHeadResponse;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.ListArticlesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.weifeng.wanandroid.R;

import java.util.ArrayList;
import java.util.List;


public class FindFragment extends Fragment implements OnItemClickListener {
    private View rootView;
    private XRefreshView xRefreshView;
    private RecyclerView contentRV;
    private ListArticlesAdapter listArticlesAdapter;
    ConvenientBanner convenientBanner;
    private int page = 0;

    List<ArticleHeadItem> articleHeadItemList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.rootView == null) {
            initView(view);
        }
        loadHeadData();
        loadContentData();
    }

    private void loadHeadData() {
        page = 0;
        RetrofitClient.getInstance().getService(APIService.class).getArticlesHead().enqueue(new Callback<ArticlesHeadResponse>() {
            @Override
            public void onResponse(Call<ArticlesHeadResponse> call, Response<ArticlesHeadResponse> response) {
                if (200 == response.code()) {
                    articleHeadItemList = response.body().getData();
                    List<String> imgUrls = new ArrayList<>();
                    for (int i = 0; i < articleHeadItemList.size(); i++) {
                        imgUrls.add(articleHeadItemList.get(i).getImagePath());
                    }
                    setHeadData(imgUrls);
                }
            }

            @Override
            public void onFailure(Call<ArticlesHeadResponse> call, Throwable t) {
                Log.e("wf", "getArticleHead" + t.getMessage());
            }
        });
    }


    private void loadContentData() {
        RetrofitClient.getInstance().getService(APIService.class).getListArticles(page).enqueue(new Callback<ListArticlesResponse>() {
            @Override
            public void onResponse(Call<ListArticlesResponse> call, Response<ListArticlesResponse> response) {
                if (200 == response.code()) {
                    if(page == 0){
                        listArticlesAdapter.clearArticeItems();
                    }
                    page++;
                    ListArticlesResponse listArticlesResponse = response.body();
                    listArticlesAdapter.setArticleItems(listArticlesResponse.data.datas);
                }
                xRefreshView.stopLoadMore();
                xRefreshView.stopRefresh();
            }

            @Override
            public void onFailure(Call<ListArticlesResponse> call, Throwable t) {
                Log.e("", "");
                xRefreshView.stopLoadMore();
                xRefreshView.stopRefresh();
            }
        });
    }

    private void initView(View view) {
        this.rootView = view;
        xRefreshView = view.findViewById(R.id.content_xRefreshView);
        xRefreshView.setPullRefreshEnable(true);    //禁止下拉刷新
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setAutoLoadMore(true);
        xRefreshView.enableRecyclerViewPullUp(true);
        xRefreshView.enablePullUpWhenLoadCompleted(true);
        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefresh(boolean isPullDown) {
                loadHeadData();
                loadContentData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                loadContentData();
            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
        contentRV = view.findViewById(R.id.content_rv);
        listArticlesAdapter = new ListArticlesAdapter(rootView.getContext());
        listArticlesAdapter.setCustomLoadMoreView(new XRefreshViewFooter(view.getContext()));
        listArticlesAdapter.setHeaderView(getHeadView(), contentRV);
        contentRV.setAdapter(listArticlesAdapter);
        contentRV.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));
        contentRV.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    }


    private View getHeadView() {
        View header = LayoutInflater.from(rootView.getContext()).inflate(R.layout.ll_article_head, null);
        convenientBanner = header.findViewById(R.id.convenientBanner);
        //本地图片例子
        return header;
    }

    private void setHeadData(List<String> imgDates) {
        if (convenientBanner == null) {
            return;
        }
        convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_article_headview;
                    }
                }, imgDates)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this)
                .setCanLoop(true)
                .startTurning(2500);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this.getActivity(), WebViewActivity.class);
        intent.putExtra("url",articleHeadItemList.get(position).getUrl());
        startActivity(intent);
    }
}
