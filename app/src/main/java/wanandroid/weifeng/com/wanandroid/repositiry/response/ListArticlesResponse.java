package wanandroid.weifeng.com.wanandroid.repositiry.response;

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
        public List<Item> datas;
        public int offset;
        public boolean over;
        public int pagecount;
        public int size;
        public int total;
    }

    public static class Item implements Serializable{
        public String apklink;
        public String author;
        public int chapterid;
        public String chaptername;
        public boolean collect;
        public int courseid;
        public String desc;
        public String envelopepic;
        public boolean fresh;
        public int id;
        public String link;
        public String nicedate;
        public String origin;
        public String projectlink;
        public long publishtime;
        public int superchapterid;
        public String superchaptername;
        public List<Object> tags;
        public String title;
        public int type;
        public int userid;
        public int visible;
        public int zan;
    }
}
