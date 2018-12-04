package com.weifeng.wanandroid.model;

import java.io.Serializable;

/**
 * @anthor weifeng
 * @time 2018/12/4 上午11:07
 */
public class TodoBean implements Serializable {
    public static final int TODOTOP = 1;
    public static final int TODOITEM = 2;
    public static final int DONETOP = 3;
    public static final int DONEITEM = 4;


    public int viewType;
    public Object completeDate;
    public String completeDateStr;
    public String content;
    public long date;
    public String dateStr;
    public int id;
    public int priority;
    public int status;
    public String title;
    public int type;
    public int userId;

}
