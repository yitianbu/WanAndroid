package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.weifeng.wanandroid.adapter.ProjectContentAdapter;
import com.weifeng.wanandroid.adapter.ProjectNavAdapter;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.response.NaviResponse;
import com.weifeng.wanandroid.widget.loading.AutoLoadingView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavigationFragment extends Fragment implements ProjectNavAdapter.OnItemClickListener{
    private View rootView;
    private RecyclerView navRv,contentRv;
    private AutoLoadingView autoLoadingView;
    private ProjectContentAdapter contentAdapter;
    private ProjectNavAdapter navAdapter;
    private List<NaviResponse.Articles> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.rootView == null){
            initView(view);
        }
        loadData();
    }

    private void loadData() {
        autoLoadingView.showLoading();
        RetrofitClient.getInstance().getService(APIService.class).getNavis().enqueue(new Callback<NaviResponse>() {
            @Override
            public void onResponse(Call<NaviResponse> call, Response<NaviResponse> response) {
                mData = response.body().data;
                navAdapter.setNavListData(getNavTitles(mData));
                navAdapter.setOnItemClickListener(NavigationFragment.this);
                contentAdapter.setContentListData(mData.get(0).articles);
                autoLoadingView.dismissLoading();
            }

            @Override
            public void onFailure(Call<NaviResponse> call, Throwable t) {
                Log.e("wf", t.getMessage());
                autoLoadingView.dismissLoading();
            }
        });
    }

    private List<String> getNavTitles(List<NaviResponse.Articles> articles) {
        List<String> articlesTitles = new ArrayList<>();
        if(articles == null){
            return null;
        }
        for (int i = 0; i <articles.size() ; i++) {
            if(!articles.contains(articles.get(i).name)){
                articlesTitles.add(articles.get(i).name);
            }
        }
        return articlesTitles;
    }

    private void initView(View view) {
        this.rootView = view;
        autoLoadingView = view.findViewById(R.id.loading_view);
        navRv = view.findViewById(R.id.rv_nav);
        contentRv = view.findViewById(R.id.rv_content);
        navAdapter = new ProjectNavAdapter(this.getActivity(),navRv);
        contentAdapter = new ProjectContentAdapter(this.getActivity());
        navRv.setAdapter(navAdapter);
        LinearLayoutManager navLinearLayoutManager = new LinearLayoutManager(this.getContext());
        navRv.setLayoutManager(navLinearLayoutManager);
        contentRv.setAdapter(contentAdapter);
        GridLayoutManager contentLinearLayoutManager = new GridLayoutManager(this.getContext(),2);
        contentRv.setLayoutManager(contentLinearLayoutManager);
    }

    @Override
    public void onItemClick(int position) {
        contentAdapter.setContentListData(mData.get(position).articles);
    }
}
