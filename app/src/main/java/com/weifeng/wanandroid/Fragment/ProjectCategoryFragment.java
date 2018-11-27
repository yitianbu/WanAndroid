package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.adapter.CollectArticleAdapter;
import com.weifeng.wanandroid.adapter.ProjectCategoryAdapter;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.callback.ThorCallback;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesResponse;
import com.weifeng.wanandroid.repositiry.response.ProjectCategoryResponse;
import com.weifeng.wanandroid.repositiry.response.ProjectResponse;

import java.util.List;

import retrofit2.Response;

public class ProjectCategoryFragment extends Fragment {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private ProjectCategoryAdapter adapter;
    private View rootView;

    private ProjectCategoryResponse.DataBean categoryData;



    private int currentPage =1;

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
        adapter = new ProjectCategoryAdapter(view.getContext());
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        xRefreshView.setPullRefreshEnable(true);    //禁止下拉刷新
        xRefreshView.setPullLoadEnable(true);
        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {

            @Override
            public void onRefresh() {
                currentPage =1;
                loadContentData(currentPage);
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
        RetrofitClient.getInstance().getService(APIService.class).getProjectCategoryList(page,String.valueOf(categoryData.getId())).enqueue(new ThorCallback<ProjectResponse>() {

            @Override
            public void onSuccess(Response<ProjectResponse> response) {
                if(response.body() ==null ||  response.body().data == null || response.body().data.datas == null || response.body().data.datas.size() == 0){
                    xRefreshView.stopLoadMore();
                    xRefreshView.setPullLoadEnable(false);
                    return;
                }
                if(page == 1){
                    adapter.clearProjectBeans();
                    adapter.setProjectBeans(response.body().data.datas);
                }else {
                    adapter.setProjectBeans(response.body().data.datas);
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
            categoryData = (ProjectCategoryResponse.DataBean)getArguments().getSerializable("project_category");
            loadContentData(1);
        }
    }
}
