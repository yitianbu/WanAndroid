package com.weifeng.wanandroid.repositiry.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @anthor weifeng
 * @time 2018/11/23 上午10:33
 */
public abstract class ThorCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call call, Response response) {
        if(response.code() == 200 ){
            onSuccess(response);
        }else {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.errorMsg = response.message();
            errorMessage.errorCode = response.code();
            onFailure(errorMessage);
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.errorMsg = t.getMessage();
        onFailure(errorMessage);
    }

    public abstract void onSuccess(Response<T> response);
    public abstract void onFailure(ErrorMessage errorMessage);

    public class ErrorMessage{
        public int errorCode;
        public String errorMsg;
    }


}
