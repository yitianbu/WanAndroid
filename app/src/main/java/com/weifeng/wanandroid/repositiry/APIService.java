package com.weifeng.wanandroid.repositiry;
import com.weifeng.wanandroid.repositiry.response.AddToDoResponse;
import com.weifeng.wanandroid.repositiry.response.ArticlesHeadResponse;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesInStationResponse;
import com.weifeng.wanandroid.repositiry.response.CollectArticlesResponse;
import com.weifeng.wanandroid.repositiry.response.ListArticlesResponse;
import com.weifeng.wanandroid.repositiry.response.LoginResponse;
import com.weifeng.wanandroid.repositiry.response.NaviResponse;
import com.weifeng.wanandroid.repositiry.response.ProjectCategoryItemResponse;
import com.weifeng.wanandroid.repositiry.response.ProjectCategoryResponse;
import com.weifeng.wanandroid.repositiry.response.ProjectResponse;
import com.weifeng.wanandroid.repositiry.response.RegisterResponse;
import com.weifeng.wanandroid.repositiry.response.ToDoListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @anthor weifeng
 * @time 2018/9/13 下午4:14
 */
public interface APIService {

    /**
     * 首页文章列表
     */
    @GET("/article/list/{page}/json")
    Call<ListArticlesResponse> getListArticles(@Path("page") int page);

    /**
     * 首页banner图
     */
    @GET("/banner/json")
    Call<ArticlesHeadResponse> getArticlesHead();


    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    Call<RegisterResponse> postRegister(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    Call<LoginResponse> postLogin(@Field("username") String username, @Field("password") String password);


    /**
     * 获取导航
     */
    @GET("/navi/json")
    Call<NaviResponse> getNavis();

    /**
     * 收藏站内文章
     */
    @POST("/lg/collect/{id}/json")
    Call<CollectArticlesInStationResponse> collectStationArticle(@Path("id") int id);

    /**
     * 取消站内文章收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    Call<CollectArticlesInStationResponse> cancelCollectStationArticle(@Path("id") int id);


    /**
     * 收藏站外文章
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    Call<CollectArticlesInStationResponse> collectOutStationArticle(@Field("title") String title, @Field("author") String author, @Field("link") String link);

    /**
     * 收藏文章列表
     */
    @GET("/lg/collect/list/{page}/json")
    Call<CollectArticlesResponse> getCollectArticleList(@Path("page") int page);

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    Call<ProjectCategoryResponse> getProjectCategory();

    /**
     * 项目列表数据
     */
    @GET("/project/list/{id}/json")
    Call<ProjectResponse> getProjectCategoryList(@Path("id") int id, @Query("cid") String cid);

    /**
     * 新增一个TODO
     */
    @POST("/lg/todo/add/json")
    @FormUrlEncoded
    Call<AddToDoResponse> addToDo(@Field("title") String title,@Field("content") String content,@Field("date") String date,@Field("type") Integer type,@Field("priority") Integer priority);

    /**
     * 更新一个TODO
     */
    @POST("/lg/todo/update/{id}/json")
    @FormUrlEncoded
    Call<AddToDoResponse> updateToDo(@Field("id") Integer id,@Field("title") String title,@Field("content") String content,@Field("date") String date,@Field("status") int status,@Field("type") Integer type,@Field("priority") Integer priority);

    /**
     * 删除一个Todo
     */
    @POST("/lg/todo/delete/{id}/json")
    Call<AddToDoResponse> deleteToDo(@Path("id") Integer id);

    /**
     * 仅更新完成状态Todo
     */
    @POST("/lg/todo/done/{id}/json")
    @FormUrlEncoded
    Call<AddToDoResponse> updateToDoStatus(@Path("id") Integer id,@Field("status") Integer status);

    /**
     * Todo列表,页码从1开始
     */
    @POST("/lg/todo/v2/list/{page}/json")
    Call<ToDoListResponse> getToDoList(@Path("page") Integer page);


}
