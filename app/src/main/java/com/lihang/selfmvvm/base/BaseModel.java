package com.lihang.selfmvvm.base;

import android.text.TextUtils;

import com.lihang.selfmvvm.bean.basebean.ParamsBuilder;
import com.lihang.selfmvvm.bean.basebean.ResponModel;
import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.retrofitwithrxjava.Interceptor.NetCacheInterceptor;
import com.lihang.selfmvvm.retrofitwithrxjava.Interceptor.OfflineCacheInterceptor;
import com.lihang.selfmvvm.retrofitwithrxjava.RetrofitApiService;
import com.lihang.selfmvvm.retrofitwithrxjava.RetrofitManager;
import com.lihang.selfmvvm.bean.basebean.Resource;
import com.lihang.selfmvvm.retrofitwithrxjava.downloadutils.DownFileUtils;
import com.lihang.selfmvvm.retrofitwithrxjava.uploadutils.FileUploadObserver;
import com.lihang.selfmvvm.retrofitwithrxjava.uploadutils.UploadFileRequestBody;
import com.lihang.selfmvvm.utils.LogUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by leo
 * on 2019/10/15.
 */
public abstract class BaseModel {

    public LifecycleTransformer objectLifecycleTransformer;
    public CompositeDisposable compositeDisposable;
    public ArrayList<String> onNetTags;


    public RetrofitApiService getApiService() {
        return RetrofitManager.getRetrofitManager().getApiService();
    }

    public void setObjectLifecycleTransformer(LifecycleTransformer objectLifecycleTransformer) {
        this.objectLifecycleTransformer = objectLifecycleTransformer;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public void setOnNetTags(ArrayList<String> onNetTags) {
        this.onNetTags = onNetTags;
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData) {
        return observe(observable, liveData, null);
    }

    public <T> MutableLiveData<T> observeGo(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
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
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();
        boolean cancleNet = paramsBuilder.isCancleNet();

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

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.add(oneTag);
                        }
                        if (showDialog) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .compose(objectLifecycleTransformer)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                });


        if (cancleNet) {
            compositeDisposable.add(disposable);
        }
        return liveData;
    }

    //把统一操作全部放在这，这是带重连的
    public <T> MutableLiveData<T> observeWithRetry(Observable observable, final MutableLiveData<T> liveData, ParamsBuilder paramsBuilder) {
        if (paramsBuilder == null) {
            paramsBuilder = paramsBuilder.build();
        }
        boolean showDialog = paramsBuilder.isShowDialog();
        String loadingMessage = paramsBuilder.getLoadingMessage();
        int onlineCacheTime = paramsBuilder.getOnlineCacheTime();
        int offlineCacheTime = paramsBuilder.getOfflineCacheTime();
        boolean cancleNet = paramsBuilder.isCancleNet();

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
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {

                                //如果还没到次数，就延迟5秒发起重连
                                LogUtils.i("我看看是不是在重连", "当前的重连次数 == " + currentCount[0]);
                                if (currentCount[0] <= maxCount) {
                                    currentCount[0]++;
                                    return Observable.just(1).delay(5000, TimeUnit.MILLISECONDS);
                                } else {
                                    //到次数了跑出异常
                                    return Observable.error(new Throwable("重连次数已达最高,请求超时"));
                                }
                            }
                        });
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.add(oneTag);
                        }
                        if (showDialog) {
                            liveData.postValue((T) Resource.loading(loadingMessage));
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .compose(objectLifecycleTransformer)
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        liveData.postValue((T) Resource.response((ResponModel<Object>) o));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                        if (!TextUtils.isEmpty(oneTag)) {
                            onNetTags.remove(oneTag);
                        }
                    }
                });


        if (cancleNet) {
            compositeDisposable.add(disposable);
        }
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
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        if (currentLength == 0) {
                            return DownFileUtils.saveFile(responseBody, destDir, fileName, liveData);
                        } else {
                            return DownFileUtils.saveFile(responseBody, destDir, fileName, currentLength, liveData);
                        }
                    }
                }).compose(objectLifecycleTransformer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        liveData.postValue((T) Resource.success(file));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                    }
                });
        return liveData;
    }

    public <T> MutableLiveData<T> upLoadFile(String url, String sequence, Map<String, File> files, MutableLiveData<T> liveData) {

        MultipartBody.Part body = null;
        if (files.keySet().size() > 1) {
            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(files, liveData);
            body = MultipartBody.Part.create(uploadFileRequestBody);
        } else {
            File file = null;
            for (String key : files.keySet()) {
                file = files.get(key);
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), sequence);
        return upLoadFile(getApiService().uploadPic(url, requestBody, body), liveData, true, "");
    }

    //上传文件
    public <T> MutableLiveData<T> upLoadFile(Observable observable, MutableLiveData<T> liveData, final boolean showDialog, final String message) {
        LogUtils.i("不是是错误了", "开始上传了");

        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (showDialog) {
                            liveData.postValue((T) Resource.loading(message));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                //防止RxJava内存泄漏
                .compose(objectLifecycleTransformer)
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        liveData.postValue((T) Resource.success("成功了"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.postValue((T) Resource.error(throwable));
                    }
                });

        return liveData;
    }

}
