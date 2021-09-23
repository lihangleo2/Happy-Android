package com.lihang.selfmvvm.bean.basebean;

import java.io.Serializable;

/**
 * Created by leo
 * on 17/12/26.
 * 这个类是泛型类，可根据后端的返回字段修改
 */
public class ResponModel<T> implements Serializable {
    public static final int RESULT_SUCCESS = 0;

    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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

    public boolean isSuccess(){
        return RESULT_SUCCESS == errorCode;
    }

    public Boolean isOtherLogin() {
        return errorCode == 100;
    }
}
