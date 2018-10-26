package com.weifeng.wanandroid.repositiry.response;

import com.weifeng.wanandroid.model.UserBean;

import java.io.Serializable;

/**
 * @anthor weifeng
 * @time 2018/9/30 上午11:21
 */
public class RegisterResponse implements Serializable {
    public UserBean data;
    public int errorCode;
    public String errorMsg;
}
