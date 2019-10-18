package com.lihang.selfmvvm.retrofitwithrxjava;

import android.os.Environment;

import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.retrofitwithrxjava.Interceptor.HttpLogInterceptor;
import com.lihang.selfmvvm.retrofitwithrxjava.Interceptor.NetCacheInterceptor;
import com.lihang.selfmvvm.retrofitwithrxjava.Interceptor.OfflineCacheInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by leo
 * on 2019/8/16.
 */
public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;


    private RetrofitApiService retrofitApiService;
    private RetrofitManager() {
        initOkHttpClient();
        initRetrofit();
    }


    public static RetrofitManager getRetrofitManager() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }


    public static RetrofitApiService getApiService() {
        return retrofitManager.retrofitApiService;
    }


    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(SystemConst.DEFAULT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        retrofitApiService = retrofit.create(RetrofitApiService.class);
    }


    private void initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                //设置缓存文件路径，和文件大小
                .cache(new Cache(new File(Environment.getExternalStorageDirectory() + "/okhttp_cache/"), 50 * 1024 * 1024))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpLogInterceptor())
                //设置在线和离线缓存
                .addInterceptor(OfflineCacheInterceptor.getInstance())
                .addNetworkInterceptor(NetCacheInterceptor.getInstance())
                .build();
    }


}
