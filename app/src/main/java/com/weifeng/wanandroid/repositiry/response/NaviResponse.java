package com.weifeng.wanandroid.repositiry.response;

import com.weifeng.wanandroid.model.ArticleBean;

import java.io.Serializable;
import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/10/30 上午10:02
 */
public class NaviResponse implements Serializable {
    public List<Articles> data;
    public int errorCode;
    public String errorMsg;


    public static class Articles implements Serializable {
        public List<ArticleBean> articles;
        public Integer cid;
        public String name;
    }

}
