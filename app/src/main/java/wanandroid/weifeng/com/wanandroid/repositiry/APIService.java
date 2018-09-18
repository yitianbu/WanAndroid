package wanandroid.weifeng.com.wanandroid.repositiry;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import wanandroid.weifeng.com.wanandroid.repositiry.response.ListArticlesResponse;

/**
 * @anthor weifeng
 * @time 2018/9/13 下午4:14
 */
public interface APIService {
@GET("/article/list/{page}/json")
    Call<ListArticlesResponse>getListArticles(@Path("page")int page);
}
