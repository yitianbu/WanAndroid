package com.weifeng.wanandroid.network;

import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import com.weifeng.wanandroid.utils.Preferences;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @anthor weifeng
 * @time 2018/11/1 上午10:38
 */
public class ReadCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) Preferences.getInstance().getmSharedPreferences().getStringSet(Preferences.PREF_COOKIES, new HashSet<String>());
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }

        return chain.proceed(builder.build());
    }
}
