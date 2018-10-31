package com.weifeng.wanandroid.model;

/**
 * @anthor weifeng
 * @time 2018/10/29 上午10:17
 */
public class ArticleBean {
    private long chapterId;
    private String chapterName;   // "常用网站"
    private String link;          // "https://developers.google.cn/"
    private String title;         // "Google开发者"
    private long publishTime;     //


    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
}
