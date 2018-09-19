package com.weifeng.wanandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.andview.refreshview.XRefreshView;
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return this.rootView != null ? this.rootView : inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        RetrofitClient.getInstance().getService(APIService.class).getListArticles(0).enqueue(new Callback<ListArticlesResponse>() {
                @Override
                public void onResponse(Call<ListArticlesResponse> call, Response<ListArticlesResponse> response) {
                    int statusCode = response.code();
                    ListArticlesResponse listArticlesResponse = response.body();
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
        xRefreshView.setAutoLoadMore(true);
        xRefreshView.setXRefreshViewListener(new XRefreshView.XRefreshViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRefresh(boolean isPullDown) {

            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

            @Override
            public void onRelease(float direction) {

            }

            @Override
            public void onHeaderMove(double headerMovePercent, int offsetY) {

            }
        });
        contentRV= view.findViewById(R.id.content_rv);
    }
}
