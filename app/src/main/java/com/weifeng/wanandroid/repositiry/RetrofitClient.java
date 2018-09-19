package com.weifeng.wanandroid.repositiry;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @anthor weifeng
 * @time 2018/9/13 下午5:38
 */

public class RetrofitClient {

    public static final String url = "http://www.wanandroid.com/";
    private static RetrofitClient instance = null;
    private Retrofit mRetrofit;
    private APIService apiService;

    private RetrofitClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                ;
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public final<T> T getService(final Class<T> clazz){
        return mRetrofit.create(clazz);
    }
}

