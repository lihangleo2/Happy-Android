package com.lihang.selfmvvm.base;

import android.text.TextUtils;

import com.leo.utilspro.utils.LogUtils;
import com.lihang.selfmvvm.base.bean.ParamsBuilder;
import com.lihang.selfmvvm.base.bean.ResponModel;
import com.lihang.selfmvvm.base.retrofitwithrxjava.Interceptor.NetCacheInterceptor;
import com.lihang.selfmvvm.base.retrofitwithrxjava.Interceptor.OfflineCacheInterceptor;
import com.lihang.selfmvvm.base.retrofitwithrxjava.RetrofitApiService;
import com.lihang.selfmvvm.base.retrofitwithrxjava.RetrofitManager;
import com.lihang.selfmvvm.base.bean.Resource;
import com.lihang.selfmvvm.base.retrofitwithrxjava.downloadutils.DownFileUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by leo
 * on 2019/10/15.
 * 这是model层的基类，封装这层的原因是，减少大量重复性工作
 */
public abstract class BaseModel {

    public LifecycleTransformer objectLifecycleTransformer;
    public ArrayList<String> onNetTags;


    public RetrofitApiService getApiService() {
        return RetrofitManager.getRetrofitManager().getApiService();
    }

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        this.objectLifecycleTransformer = objectLifecycleTransformer;
    }


    public void setOnNetTags(ArrayList<String> onNetTags) {
        this.onNetTags = onNetTags;
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData) {
        return observe(observable, liveData, null);
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = ParamsBuilder.build();
        }
        int retryCount = paramsBuilder.getRetryCount();
        if (retryCount > 0) {
            return observeWithRetry(observable, liveData, paramsBuilder);
        } else {
            return observe(observable, liveData, paramsBuilder);
        }
    }


    //把统一操作全部放在这，不会重连
    public <T> MutableLiveData<T> observe(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = paramsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        boolean closeDialog = paramsBuilder.isCloseDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }
        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable1 -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.add(oneTag);
                    }
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .doFinally(() -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.remove(oneTag);
                    }
                    if (closeDialog){
                        liveData.postValue((T) Resource.onFinaly());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(objectLifecycleTransformer)
                .subscribe(o -> {
                    //setValue(同步)，接收端和发送端在同一线程。
                    //这里使用setValue是为了解决连续2次postVlaue会丢失数据，也就不触发finally
                    liveData.setValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.setValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }


    //把统一操作全部放在这，这是带重连的
    public <T> MutableLiveData<T> observeWithRetry(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = paramsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        boolean closeDialog = paramsBuilder.isCloseDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();

        if (onlineCacheTime > 0) {
            setOnlineCacheTime(onlineCacheTime);
        }
        if (offlineCacheTime > 0) {
            setOfflineCacheTime(offlineCacheTime);
        }

        String oneTag = paramsBuilder.getOneTag();
        if (!TextUtils.isEmpty(oneTag)) {
            if (onNetTags.contains(oneTag)) {
                return liveData;
            }
        }

        final int maxCount = paramsBuilder.getRetryCount();
        final int[] currentCount = {0};

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .retryWhen(throwable -> {
                    //如果还没到次数，就延迟5秒发起重连
                    if (currentCount[0] <= maxCount) {
                        currentCount[0]++;
                        return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                    } else {
                        //到次数了跑出异常
                        return Observable.error(new Throwable("重连次数已达最高,请求超时"));
                    }
                })
                .doOnSubscribe(disposable1 -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.add(oneTag);
                    }
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .doFinally(() -> {
                    if (!TextUtils.isEmpty(oneTag)) {
                        onNetTags.remove(oneTag);
                    }
                    if (closeDialog){
                        liveData.postValue((T) Resource.onFinaly());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(objectLifecycleTransformer)
                .subscribe(o -> {
                    liveData.setValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.setValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }


    //设置在线网络缓存
    public void setOnlineCacheTime(int time) {
        NetCacheInterceptor.getInstance().setOnlineTime(time);
    }

    //设置离线网络缓存
    public void setOfflineCacheTime(int time) {
        OfflineCacheInterceptor.getInstance().setOfflineCacheTime(time);
    }

    //正常下载(重新从0开始下载)
    public <T> MutableLiveData<T> downLoadFile(Observable observable, final MutableLiveData<T> liveData, final String destDir, final String fileName) {
        return downLoadFile(observable, liveData, destDir, fileName, 0);
    }


    //断点下载，如果下载到一半，可从一半开始下载
    public <T> MutableLiveData<T> downLoadFile(Observable observable, final MutableLiveData<T> liveData, final String destDir, final String fileName, long currentLength) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(requestBody -> {
                    if (currentLength == 0) {
                        return DownFileUtils.saveFile((ResponseBody) requestBody, destDir, fileName, liveData);
                    } else {
                        return DownFileUtils.saveFile((ResponseBody) requestBody, destDir, fileName, currentLength, liveData);
                    }
                })
                .compose(objectLifecycleTransformer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    liveData.postValue((T) Resource.success(file));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });
        return liveData;
    }


    //上传文件只有2个参数，showDialog：是否显示dialog；loadmessage：showDialog显示的文字
    public <T> MutableLiveData<T> upLoadFile(Observable observable, MutableLiveData<T> liveData) {
        return upLoadFile(observable, liveData, null);
    }

    //上传文件
    public <T> MutableLiveData<T> upLoadFile(Observable observable, MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {

        if (paramsBuilder == null) {
            paramsBuilder = paramsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();

        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (showDialog) {
                        liveData.postValue((T) Resource.loading(loadingMessage));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .compose(objectLifecycleTransformer)
                .subscribe(o -> {
                    liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                }, throwable -> {
                    liveData.postValue((T) Resource.error((Throwable) throwable));
                });

        return liveData;
    }

}
