package com.weifeng.wanandroid.repositiry;

import com.weifeng.wanandroid.network.ReadCookiesInterceptor;
import com.weifeng.wanandroid.network.SaveCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ReadCookiesInterceptor())
                .addInterceptor(new SaveCookiesInterceptor())
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.wanandroid.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()).build();

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

    public final <T> T getService(final Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}

