package com.lihang.selfmvvm.base.bean;

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
    //-------------举例如下-------------//
    private boolean result;
    private T source;

    private String name;
    /*
    * 1. 有些人的接口返回是否成功，不是以errorCode为主的那么我们直接修改这里就可以了
    *
    * 2. 有些人在做一个项目，可能一会以errorCode为主一会以别的为主，比如字段result
    * 那么我们在判断成功与否的时候就可以用或。如下：
    *
    public boolean isSuccess(){
        return RESULT_SUCCESS == errorCode || result;
    }
    *
    * 3. 我们这里是data字段去解析数据，假如一个app是一个字段去解析那么我们可以直接修改；
    * 如果，一个项目有多重标准，比如既有data也有source，如下（source同理）：
    *
    public T getData() {
        if (data == null) {
            return source;
        }
        return data;
    }
    *
    * 4. 还有一种情况，则是他可能并没有按照这个json格式走，可能字段什么的都放在了ResponModel这里比如name。
    * 那么我们就将这个ResponModel返回出去.具体用法查看 RetrofitApiService里的 getAllResult()用法
    *
    * */

    public T getData() {
        if (data == null) {
            return source;
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getSource() {
        if (source == null) {
            return data;
        }
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return RESULT_SUCCESS == errorCode || result;
    }

    public Boolean isOtherLogin() {
        return errorCode == 100;
    }
}
