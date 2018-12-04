package com.weifeng.wanandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

/**
 * 用于实现本地存储
 *
 * @anthor weifeng
 * @time 2018/11/1 上午10:46
 */
public class Preferences {

    public static final String PREF_COOKIES = "pref_cookies";
    public static final String PREF_USER_NAME = "pref_user_name";
    public static final String PREF_USER_PASSWORD = "pref_user_password";

    private volatile static Preferences instance;
    private final SharedPreferences mSharedPreferences;


    private Preferences() {
        mSharedPreferences = Globals.getApplication().getSharedPreferences("wan_android_pref", Context.MODE_PRIVATE);
    }

    public static Preferences getInstance() {
        if (instance == null) {
            synchronized (Preferences.class) {
                if (instance == null) {
                    instance = new Preferences();
                }
            }
        }
        return instance;
    }

    public SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }
    

    public HashSet<String> getCookies() {
        return (HashSet<String>) getInstance().getmSharedPreferences().getStringSet(Preferences.PREF_COOKIES, new HashSet<String>());
    }

    public String getUserName(){
        return getInstance().getmSharedPreferences().getString(PREF_USER_NAME,"");
    }

    public String getUserPassword(){
        return getInstance().getmSharedPreferences().getString(PREF_USER_PASSWORD,"");
    }

    public void setCookies(HashSet<String> cookies){
        SharedPreferences.Editor editor =  getInstance().getmSharedPreferences().edit();
        editor.putStringSet(Preferences.PREF_COOKIES,cookies);
        editor.commit();
    }

    public void setUserName(String name){
        SharedPreferences.Editor editor =  getInstance().getmSharedPreferences().edit();
        editor.putString(Preferences.PREF_USER_NAME,name);
        editor.commit();
    }

    public void setUserPassword(String password){
        SharedPreferences.Editor editor =  getInstance().getmSharedPreferences().edit();
        editor.putString(Preferences.PREF_USER_PASSWORD,password);
        editor.commit();
    }

    public void cleanUserInfo(){
        setUserName("");
        setUserPassword("");
        setCookies(new HashSet<String>());
    }

}
