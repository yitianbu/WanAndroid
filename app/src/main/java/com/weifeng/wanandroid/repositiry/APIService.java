package com.weifeng.wanandroid.repositiry;

import com.weifeng.wanandroid.repositiry.response.ArticlesHeadResponse;
import com.weifeng.wanandroid.repositiry.response.ListArticlesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @anthor weifeng
 * @time 2018/9/13 下午4:14
 */
public interface APIService {
    @GET("/article/list/{page}/json")
    Call<ListArticlesResponse> getListArticles(@Path("page") int page);

    @GET("/banner/json")
    Call<ArticlesHeadResponse> getArticlesHead();
}
