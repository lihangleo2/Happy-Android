package com.lihang.selfmvvm;

import android.app.Application;
import android.content.Context;

import com.leo.utilspro.utils.PreferenceUtil;
import com.leo.utilspro.utils.abase.LeoUtils;
import com.lihang.selfmvvm.bean.User;
import com.lihang.selfmvvm.morefunction.launchstater.TaskDispatcher;
import com.lihang.selfmvvm.morefunction.launchstater.mytasks.SmartRefreshLayoutTask;
import com.lihang.selfmvvm.morefunction.launchstater.mytasks.X5WebTask;

/**
 * Created by leo
 * on 2019/10/15.
 */
public class MyApplication extends Application {

    private static MyApplication context;
    private static User loginUser;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //捕获崩溃日志，位置在外部存储的LianSou
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        handleSSLHandshake();
        context = this;
        LeoUtils.initContext(this);
        TaskDispatcher.init(this);
        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
        dispatcher.addTask(new SmartRefreshLayoutTask())
                .addTask(new X5WebTask())
                .start();

    }

    public static User getLoginUser() {
        if (loginUser == null) {
            loginUser = PreferenceUtil.getByClass("user", User.class);
        }
        return loginUser;
    }


    public static String getUserToken() {
        if (loginUser == null) {
            return "";
        } else {
            return loginUser.getToken();
        }
    }


    public static void updateUser(User user) {
        PreferenceUtil.putByClass("user", user);
        loginUser = user;
    }

    public static void logOut() {
        loginUser = null;
        PreferenceUtil.clearByClass(User.class);
    }


    public static Context getContext() {
        return context;
    }
    
   
    
    
        public  void handleSSLHandshake() {
        //解决三星(部分手机)，glide加载https，加载不除来
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {

                }
                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {

                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {

        }
    }
    
}
