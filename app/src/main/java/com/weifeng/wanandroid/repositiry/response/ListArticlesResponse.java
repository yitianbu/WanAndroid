package com.weifeng.wanandroid.repositiry.response;

import com.weifeng.wanandroid.model.ArticleContentItem;

import java.io.Serializable;
import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/9/13 下午4:21
 */
public class ListArticlesResponse implements Serializable{
    public Data data;
    public int errorCode;
    public String errormsg;


    public static class Data implements Serializable{
        public int curpage;
        public List<ArticleContentItem> datas;
        public int offset;
        public boolean over;
        public int pagecount;
        public int size;
        public int total;
    }
}
