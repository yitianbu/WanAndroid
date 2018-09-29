package com.weifeng.wanandroid.repositiry.response;

import com.weifeng.wanandroid.model.ArticleHeadItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/9/28 下午3:00
 */
public class ArticlesHeadResponse implements Serializable {

    private List<ArticleHeadItem> data = new ArrayList<>();
    private int errorCode;
    private String errorMsg;

    public List<ArticleHeadItem> getData() {
        return data;
    }

    public void setData(List<ArticleHeadItem> articleHeadItemList) {
        this.data = articleHeadItemList;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
