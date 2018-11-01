package com.weifeng.wanandroid.network;

import com.weifeng.wanandroid.utils.Preferences;

import java.io.IOException;
import java.util.HashSet;


import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @anthor weifeng
 * @time 2018/11/1 上午10:45
 */
public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

           Preferences.getInstance().setCookies(cookies);

        }

        return originalResponse;
    }
}
