package com.weifeng.wanandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshContentView;
import com.andview.refreshview.XRefreshView;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.adapter.CollectArticleAdapter;
import com.weifeng.wanandroid.adapter.ListArticlesAdapter;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.callback.ThorCallback;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesInStationResponse;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectFragment extends Fragment {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private CollectArticleAdapter adapter;
    private View rootView;



    private int currentPage =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return this.rootView !=null ? this.rootView: inflater.inflate(R.layout.fragment_collect, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.rootView == null){
            initView(view);
            this.rootView = view;
        }
    }

    private void initView(View view) {
        xRefreshView = view.findViewById(R.id.content_xRefreshView);
        recyclerView = view.findViewById(R.id.rv_content);
        adapter = new CollectArticleAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
                currentPage++;
                loadContentData(currentPage);
            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
    }

    public void loadContentData(final int page) {
        RetrofitClient.getInstance().getService(APIService.class).getCollectArticleList(page).enqueue(new ThorCallback<CollectArticlesResponse>() {

            @Override
            public void onSuccess(Response<CollectArticlesResponse> response) {
                if(response.body() ==null ||  response.body().getData() == null){
                    return;
                }
                if(page == 0){
                    adapter.clearArticeItems();
                    adapter.setArticleItems(response.body().getData().getDatas());
                }else {
                    adapter.setArticleItems(response.body().getData().getDatas());
                }
                xRefreshView.stopLoadMore();
                xRefreshView.stopRefresh();
            }

            @Override
            public void onFailure(ErrorMessage errorMessage) {
                xRefreshView.stopLoadMore();
                xRefreshView.stopRefresh();
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            loadContentData(0);
        }
    }
}
