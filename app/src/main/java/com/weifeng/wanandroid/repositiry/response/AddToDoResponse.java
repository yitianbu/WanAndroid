package com.weifeng.wanandroid.repositiry.response;

import com.weifeng.wanandroid.model.TodoBean;
import java.io.Serializable;

/**
 * @anthor weifeng
 * @time 2018/9/28 下午3:00
 */
public class AddToDoResponse implements Serializable {


    /**
     * data : {"completeDate":null,"completeDateStr":"","content":"aefr","date":1543766400000,"dateStr":"2018-12-03","id":4658,"priority":0,"status":0,"title":"afa","type":0,"userId":11112}
     * errorCode : 0
     * errorMsg :
     */

    private TodoBean data;
    private int errorCode;
    private String errorMsg;

    public TodoBean getData() {
        return data;
    }

    public void setData(TodoBean data) {
        this.data = data;
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
