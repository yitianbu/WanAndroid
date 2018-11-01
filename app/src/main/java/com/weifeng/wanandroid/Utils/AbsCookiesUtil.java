package com.weifeng.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/11/1 上午11:18
 */
public abstract class AbsCookiesUtil {

    private SharedPreferences mSharedPreferences;

    AbsCookiesUtil(@NonNull Context context, String sharePreferenceName){
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(sharePreferenceName, Context.MODE_PRIVATE);
    }

    public void encodeAndSaveCookies(@NonNull List<String> cookiesList, @NonNull String host) {
        String cookiesListString = encodeCookies(cookiesList, host);
        saveCookies(cookiesListString, host);
    }

    abstract String encodeCookies(@NonNull List<String> cookiesList, @NonNull String host);

    private void saveCookies(String cookiesListString, String host) {
        mSharedPreferences.edit().putString(host, cookiesListString).apply();
    }

    public final String getCookiesListString(@NonNull String host) {
        return mSharedPreferences.getString(host, "");
    }
}
