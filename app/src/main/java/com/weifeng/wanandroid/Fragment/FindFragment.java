package com.weifeng.wanandroid.Fragment;

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
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.weifeng.wanandroid.Adapter.ListArticlesAdapter;
import com.weifeng.wanandroid.View.ArticlesHeadView;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.ListArticlesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.weifeng.wanandroid.R;


public class FindFragment extends Fragment {
    private View rootView;
    private XRefreshView xRefreshView;
    private RecyclerView contentRV;
    private ListArticlesAdapter listArticlesAdapter;
    private int page = 0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        RetrofitClient.getInstance().getService(APIService.class).getListArticles(page).enqueue(new Callback<ListArticlesResponse>() {
                @Override
                public void onResponse(Call<ListArticlesResponse> call, Response<ListArticlesResponse> response) {
                    if(200 == response.code()) {
                        page ++;
                        ListArticlesResponse listArticlesResponse = response.body();
                        listArticlesAdapter.setArticleItems(listArticlesResponse.data.datas);
                    }
                    xRefreshView.stopLoadMore();
                }

                @Override
                public void onFailure(Call<ListArticlesResponse> call, Throwable t) {
                    Log.e("","");
                }
            });
    }

    private void initView(View view) {
        this.rootView = view;
        xRefreshView = view.findViewById(R.id.content_xRefreshView);
        xRefreshView.setPullRefreshEnable(false);    //禁止下拉刷新
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

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RetrofitClient.getInstance().getService(APIService.class).getListArticles(page).enqueue(new Callback<ListArticlesResponse>() {
                            @Override
                            public void onResponse(Call<ListArticlesResponse> call, Response<ListArticlesResponse> response) {
                                if(200 == response.code()) {
                                    page ++;
                                    ListArticlesResponse listArticlesResponse = response.body();
                                    listArticlesAdapter.setArticleItems(listArticlesResponse.data.datas);
                                }
                                xRefreshView.stopLoadMore();

                            }

                            @Override
                            public void onFailure(Call<ListArticlesResponse> call, Throwable t) {
                                Log.e("","");
                            }
                        });
                    }
                },1000);
            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
        contentRV= view.findViewById(R.id.content_rv);
        listArticlesAdapter = new ListArticlesAdapter(rootView.getContext());
        listArticlesAdapter.setCustomLoadMoreView(new XRefreshViewFooter(view.getContext()));
        contentRV.setAdapter(listArticlesAdapter);
        contentRV.addItemDecoration(new DividerItemDecoration(rootView.getContext(),DividerItemDecoration.VERTICAL));
        contentRV.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
    }
}
