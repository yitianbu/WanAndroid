package com.weifeng.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.weifeng.wanandroid.R;
import com.weifeng.wanandroid.adapter.CollectArticleAdapter;
import com.weifeng.wanandroid.adapter.TodoAdapter;
import com.weifeng.wanandroid.model.TodoBean;
import com.weifeng.wanandroid.repositiry.APIService;
import com.weifeng.wanandroid.repositiry.RetrofitClient;
import com.weifeng.wanandroid.repositiry.callback.ThorCallback;
import com.weifeng.wanandroid.repositiry.response.AddToDoResponse;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesResponse;
import com.weifeng.wanandroid.repositiry.response.ToDoListResponse;
import com.weifeng.wanandroid.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.weifeng.wanandroid.model.TodoBean.DONEITEM;
import static com.weifeng.wanandroid.model.TodoBean.DONETOP;
import static com.weifeng.wanandroid.model.TodoBean.TODOITEM;
import static com.weifeng.wanandroid.model.TodoBean.TODOTOP;


public class TodoFragment extends Fragment {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private View rootView;

    private int currentPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.rootView == null) {
            initView(view);
            this.rootView = view;
        }
    }

    private void initView(View view) {
        xRefreshView = view.findViewById(R.id.content_xRefreshView);
        recyclerView = view.findViewById(R.id.rv_content);
        adapter = new TodoAdapter(view.getContext());
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

    private void loadContentData(final int currentPage) {
        RetrofitClient.getInstance().getService(APIService.class).getToDoList(currentPage).enqueue(new ThorCallback<ToDoListResponse>() {

            @Override
            public void onSuccess(Response<ToDoListResponse> response) {
                if(response.body() == null ||  response.body().getData()==null ||response.body().getData().datas == null || response.body().getData().datas.size() ==0) {
                    xRefreshView.stopLoadMore();
                    xRefreshView.stopRefresh();
                    xRefreshView.enableRecyclerViewPullUp(false);
                    return;
                }
                if(response.body().getErrorCode() == -1001){
                    Preferences.getInstance().cleanUserInfo();
                    Toast.makeText(TodoFragment.this.getContext(),response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getData() == null) {
                    return;
                }
                if (currentPage == 1) {
                    adapter.clearTodoItems();
                    adapter.setTodoItems(convertData(response.body().getData().datas));
                } else {
                    adapter.setTodoItems(convertData(response.body().getData().datas));
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

    private List<TodoBean> convertData(List<TodoBean> data) {
        List<TodoBean> result = new ArrayList<>();
        List<TodoBean> todoBeanList = new ArrayList<>();
        List<TodoBean> doneBeanList = new ArrayList<>();
        for (int i = 0; i <data.size() ; i++) {
            if(data.get(i).status == 0){
                todoBeanList.add(data.get(i));
            }else {
                doneBeanList.add(data.get(i));
            }
        }
        data.clear();
        data.addAll(todoBeanList);
        data.addAll(doneBeanList);
        for (int i = 0; i < data.size(); i++) {
            TodoBean currentData = data.get(i);
            if (i == 0) {
                TodoBean todoBean = new TodoBean();
                todoBean.viewType = TODOTOP;
                result.add(0,todoBean);
                if (currentData.status == 1) {
                    TodoBean todoBean1 = new TodoBean();
                    todoBean1.viewType = DONETOP;
                    result.add(todoBean1);
                }
                if(currentData.status==0){
                    currentData.viewType = TODOITEM;
                }else {
                    currentData.viewType = DONEITEM;
                }
                result.add(currentData);
            } else {
                if(currentData.status!=data.get(i-1).status){
                    TodoBean todoBean = new TodoBean();
                    todoBean.viewType = DONETOP;
                    result.add(todoBean);
                }
                if(currentData.status==0){
                    currentData.viewType = TODOITEM;
                }else {
                    currentData.viewType = DONEITEM;
                }
                result.add(currentData);
            }
        }
        return result;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            currentPage = 1;
            loadContentData(currentPage);
        }
    }
}
