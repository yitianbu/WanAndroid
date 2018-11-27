package com.weifeng.wanandroid.repositiry.response;

import java.io.Serializable;
import java.util.List;

/**
 * @anthor weifeng
 * @time 2018/11/27 上午10:38
 */
public class ProjectResponse implements Serializable {
    public int errorCode;
    public String errorMsg;
    public ProjectData data;


    public static class ProjectData implements Serializable{
        public int curPage;
        public List<ProjectBean> datas;
    }

    public static class ProjectBean implements Serializable{

        /**
         * apkLink : 
         * author : songmao123
         * chapterId : 294
         * chapterName : 完整项目
         * collect : true
         * courseId : 13
         * desc : 一款数据基于Wan Android API，采用Kotlin+MVP+Dagger2+Rxjava架构的Material Design风格玩安卓客户端。
         * envelopePic : http://www.wanandroid.com/blogimgs/4c47aec3-1740-4ad9-9a37-ee99a1e742de.png
         * fresh : false
         * id : 7555
         * link : http://www.wanandroid.com/blog/show/2429
         * niceDate : 2018-11-22
         * origin : 
         * projectLink : https://github.com/songmao123/WanAndroid
         * publishTime : 1542900280000
         * superChapterId : 294
         * superChapterName : 开源项目主Tab
         * tags : [{"name":"项目","url":"/project/list/1?cid=294"}]
         * title : Kotlin+MVP+RxJava+Dagger2版玩安卓客户端
         * type : 0
         * userId : -1
         * visible : 1
         * zan : 0
         */

        public String apkLink;
        public String author;
        public int chapterId;
        public String chapterName;
        public boolean collect;
        public int courseId;
        public String desc;
        public String envelopePic;
        public boolean fresh;
        public int id;
        public String link;
        public String niceDate;
        public String origin;
        public String projectLink;
        public long publishTime;
        public int superChapterId;
        public String superChapterName;
        public String title;
        public int type;
        public int userId;
        public int visible;
        public int zan;

    }
}
