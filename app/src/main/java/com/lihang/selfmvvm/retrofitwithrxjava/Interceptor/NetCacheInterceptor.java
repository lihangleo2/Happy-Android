package com.lihang.selfmvvm.retrofitwithrxjava.Interceptor;

import android.text.TextUtils;


import com.lihang.selfmvvm.utils.LogUtils;
import com.lihang.selfmvvm.utils.PreferenceUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leo
 * on 2019/7/25.
 * 在有网络的情况下
 * 如果还在网络有效期呢则取缓存，否则请求网络
 * 重点 : 一般okhttp只缓存不大改变的数据适合get。（个人理解 : 例如你设置了我的方案列表接口的缓存后，你删除了一条方案，刷新下。
 * 他取的是缓存，结果那条删除的数据会出来。这个时候这个接口，不适合用缓存了）
 * (这里注意，如果一个接口设置了缓存30秒，下次请求这个接口的30秒内都会去取缓存，即使你设置0也不起效。因为缓存文件里的标识里已经有30秒的有效期)
 */
public class NetCacheInterceptor implements Interceptor {
    private static NetCacheInterceptor cacheInterceptor;
    //30在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
    private int onlineCacheTime;

    public static NetCacheInterceptor getInstance() {
        if (cacheInterceptor == null) {
            synchronized (NetCacheInterceptor.class) {
                if (cacheInterceptor == null) {
                    cacheInterceptor = new NetCacheInterceptor();
                }
            }
        }
        return cacheInterceptor;
    }

    private NetCacheInterceptor() {

    }

    public void setOnlineTime(int time) {
        this.onlineCacheTime = time;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.i("我真的是醉了","11111111");
        Request request = chain.request();
        Request.Builder builder1 = request.newBuilder();

        //这里坐了自动解析头部和取值。之前一个项目要用头部的Token字段。我也不知道为什么不用cookie
        //(到时候最好用csdn登录来做)
        String token = (String) PreferenceUtil.get("USER_TOKEN", "");
        if (!TextUtils.isEmpty(token)) {
            builder1.addHeader("Token", token)
                    .build();
        }
        request = builder1.build();
        Response response = chain.proceed(request);
        List<String> list = response.headers().values("Token");
        if (list.size() > 0) {
            PreferenceUtil.put("USER_TOKEN", list.get(0));
        }

        if (onlineCacheTime != 0) {
            //如果有时间就设置缓存
            int temp = onlineCacheTime;
            Response response1 = response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + temp)
                    .removeHeader("Pragma")
                    .build();
            onlineCacheTime = 0;
            return response1;
        } else {
            //如果没有时间就不缓存
            Response response1 = response.newBuilder()
                    .header("Cache-Control", "no-cache")
                    .removeHeader("Pragma")
                    .build();
            return response1;
        }
//        return response;
    }
}
