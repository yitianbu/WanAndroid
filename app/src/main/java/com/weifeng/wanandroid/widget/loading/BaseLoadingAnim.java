package com.weifeng.wanandroid.widget.loading;

import java.util.ArrayList;
import java.util.List;

/**
 * ┏┛ ┻━━━━━┛ ┻┓
 * ┃　　　　　　 ┃
 * ┃　　　━　　　┃
 * ┃　┳┛　  ┗┳　┃
 * ┃　　　　　　 ┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　 ┃
 * ┗━┓　　　┏━━━┛
 * * ┃　　　┃   神兽保佑
 * * ┃　　　┃   代码无BUG！
 * * ┃　　　┗━━━━━━━━━┓
 * * ┃　　　　　　　    ┣┓
 * * ┃　　　　         ┏┛
 * * ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
 * * * ┃ ┫ ┫   ┃ ┫ ┫
 * * * ┗━┻━┛   ┗━┻━┛
 *
 * @author qigengxin
 * @since 2018-04-19 11:52
 */
public abstract class BaseLoadingAnim implements ILoadingAnimation{

    private List<LoadingAnimListener> listenerList = new ArrayList<>();

    @Override
    public void addListener(LoadingAnimListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void removeAllListener(LoadingAnimListener listener) {
        listenerList.remove(listener);
    }

    @Override
    public void removeListener(LoadingAnimListener listener) {
        listenerList.clear();
    }

    protected void notifyAnimStart() {
        for (LoadingAnimListener listener : listenerList) {
            listener.onAnimStart(this);
        }
    }

    protected void notifyAnimEnd() {
        for (LoadingAnimListener listener : listenerList) {
            listener.onAnimEnd(this);
        }
    }
}
