package com.weifeng.wanandroid.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/11/1 上午11:19
 */
public class CookiesUtil extends AbsCookiesUtil{

    public CookiesUtil(@NonNull Context context, String sharePreferenceName) {
        super(context, sharePreferenceName);
    }

    @Override
    public String encodeCookies(@NonNull List<String> cookiesList, @NonNull String host) {
        StringBuilder sb = new StringBuilder();
        HashSet<String> set = new HashSet<>();
        for (String cookie: cookiesList) {
            String[] array = cookie.split(";");
            for (String item: array) {
                set.add(item);
            }
        }


        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(";");
        }

        int lastIndex = sb.lastIndexOf(";");
        if (sb.length() -1 == lastIndex) {
            sb.deleteCharAt(lastIndex);
        }
        return sb.toString();
    }
}
