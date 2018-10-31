package com.weifeng.wanandroid.application;

import android.app.Application;
import android.content.Context;

import com.weifeng.wanandroid.utils.Globals;

/**
 * @anthor weifeng
 * @time 2018/9/19 下午5:47
 */
public class WanApplication extends Application {

   public static Context getContext(){
       return Globals.getApplication();
   }

}
