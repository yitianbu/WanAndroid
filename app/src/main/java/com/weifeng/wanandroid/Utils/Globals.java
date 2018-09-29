package com.weifeng.wanandroid.Utils;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @anthor weifeng
 * @time 2018/9/29 上午11:37
 */
public class Globals {
    private static final Object LOCK = new Object();
    private static Application sApplication;

    public static Application getApplication() {
        if (sApplication != null) {
            return sApplication;
        }
        try {
            sApplication = getSystemApp();
        } catch (NotMainThreadLocalException e) {
            if (sApplication == null) {
                synchronized (LOCK) {
                    if (sApplication != null) {
                        return sApplication;
                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new ActivityThreadGetter());
                    try {
                        LOCK.wait(5000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sApplication;
    }



    private static Application getSystemApp() throws NotMainThreadLocalException, ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Class<?> cls = Class.forName("android.app.ActivityThread");
        Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
        Field declaredField = cls.getDeclaredField("mInitialApplication");
        declaredField.setAccessible(true);
        Object currentActivityThread = declaredMethod.invoke(null);
        if (currentActivityThread == null && Thread.currentThread().getId() != Looper.getMainLooper().getThread().getId()) {
            //it is in thread local, using main thread to get it
            throw new NotMainThreadLocalException("you should get it from main thread");
        }
        Object object = declaredField.get(currentActivityThread);
        if (object instanceof Application) {
            return (Application) object;
        }
        return null;
    }

    static class ActivityThreadGetter implements Runnable {
        ActivityThreadGetter() {
        }

        @Override
        public void run() {
            try {
                if (sApplication != null) {
                    return;
                }
                sApplication = getSystemApp();
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                synchronized (LOCK) {
                    LOCK.notifyAll();
                }
            }
        }
    }

    /**
     * 对于4.0-4.1的机型，从子线程中获取currentActivityThread抛出此异常，切换到主线程阻塞获取
     */
    private static class NotMainThreadLocalException extends IllegalStateException {
        NotMainThreadLocalException(String s) {
            super(s);
        }
    }
}
