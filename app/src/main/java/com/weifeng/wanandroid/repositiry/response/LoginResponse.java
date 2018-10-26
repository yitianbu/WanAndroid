package com.weifeng.wanandroid.repositiry.response;

import com.weifeng.wanandroid.model.UserBean;

import java.io.Serializable;

/**
 * @anthor weifeng
 * @time 2018/9/30 下午2:44
 */
public class LoginResponse implements Serializable {
    public UserBean data;
    public int errorCode;
    public String errorMsg;
}
