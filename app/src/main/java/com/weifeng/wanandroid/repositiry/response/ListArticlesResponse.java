package com.weifeng.wanandroid.repositiry.response;

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
        public List<ArticleItem> datas;
        public int offset;
        public boolean over;
        public int pagecount;
        public int size;
        public int total;
    }

    public static class ArticleItem implements Serializable{
        public String apklink;
        public String author;
        public int chapterid;
        public String chapterName;
        public boolean collect;
        public int courseid;
        public String desc;
        public String envelopePic;
        public boolean fresh;
        public int id;
        public String link;
        public String nicedate;
        public String origin;
        public String projectlink;
        public long publishTime;
        public int superchapterid;
        public String superChapterName;
        public List<Object> tags;
        public String title;
        public int type;
        public int userid;
        public int visible;
        public int zan;
    }
}
