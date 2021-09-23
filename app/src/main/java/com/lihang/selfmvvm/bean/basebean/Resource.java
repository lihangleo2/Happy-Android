package com.lihang.selfmvvm.bean.basebean;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by leo
 * on 2019/10/16.
 */
//这个用来拓展LiveData
public class Resource<T> {
    //状态  这里有多个状态 0表示加载中；1表示成功；2表示联网失败；3表示接口虽然走通，但走的失败（如：关注失败）
    public static final int LOADING = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int FAIL = 3;
    public static final int PROGRESS = 4;//注意只有下载文件和上传图片时才会有
    public static final int OTHERLOGIN = 5;//单设备登录
    public int state;

    public String errorMsg;
    public T data;
    public Throwable error;

    //这里和文件和进度有关了
    public int precent;//文件下载百分比
    public long total;//文件总大小

    public Resource(int state, T data, String errorMsg) {
        this.state = state;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public Resource(int state, Throwable error) {
        this.state = state;
        this.error = error;
    }

    public Resource(int state, int precent, long total) {
        this.state = state;
        this.precent = precent;
        this.total = total;
    }


    public static <T> Resource<T> loading(String showMsg) {
        return new Resource<>(LOADING, null, showMsg);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> response(ResponModel<T> data) {
        if (data != null) {
            if (data.isSuccess()) {
                return new Resource<>(SUCCESS, data.getData(), null);
            }

            if (data.isOtherLogin()) {
                return new Resource<>(OTHERLOGIN, null, data.getErrorMsg());
            }
            return new Resource<>(FAIL, null, data.getErrorMsg());
        }
        return new Resource<>(ERROR, null, null);
    }


    public static <T> Resource<T> failure(String msg) {
        return new Resource<>(ERROR, null, msg);
    }

    public static <T> Resource<T> error(Throwable t) {
        return new Resource<>(ERROR, t);
    }

    public static <T> Resource<T> progress(int precent, long total) {
        return new Resource<>(PROGRESS, precent, total);
    }

    public void handler(OnHandleCallback<T> callback) {
        handlerUnCloseDialog(callback);
        if (state != LOADING) {
            callback.onCompleted();
        }
    }



    //网络加载完成后不消失dialog;(场景：连续请求2个网络。第二个网络要等第一个网络返回的参数，才请求。第一个网络不该关闭dialog)
    public void handlerUnCloseDialog(OnHandleCallback<T> callback) {
        switch (state) {
            case LOADING:
                callback.onLoading(errorMsg);
                break;
            case SUCCESS:
                callback.onSuccess(data);
                break;
            case FAIL:
                callback.onFailure(errorMsg);
                break;
            case ERROR:
                callback.onError(error);
                break;
            case PROGRESS:
                callback.onProgress(precent,total);
                break;
            case OTHERLOGIN:
                callback.onOtherLogin(errorMsg);
                break;
        }
    }


    public void handler(OnHandleCallback<T> callback, SmartRefreshLayout smartRefreshLayout) {
        handlerUnCloseDialog(callback,smartRefreshLayout);
        if (state != LOADING) {
            callback.onCompleted();
        }
    }


    public void handlerUnCloseDialog(OnHandleCallback<T> callback, SmartRefreshLayout smartRefreshLayout) {
        switch (state) {
            case LOADING:
                callback.onLoading(errorMsg);
                break;
            case SUCCESS:
                callback.onSuccess(data);
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
                break;
            case FAIL:
                callback.onFailure(errorMsg);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
            case ERROR:
                callback.onError(error);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
            case PROGRESS:
                callback.onProgress(precent,total);
                break;

            case OTHERLOGIN:
                callback.onOtherLogin(errorMsg);
                smartRefreshLayout.finishRefresh(false);
                smartRefreshLayout.finishLoadMore(false);
                break;
        }
    }


    public interface OnHandleCallback<T> {
        void onLoading(String showMessage);

        void onSuccess(T data);

        void onFailure(String msg);

        void onError(Throwable error);

        void onCompleted();

        void onProgress(int precent,long total);

        void onOtherLogin(String msg);
    }


}
