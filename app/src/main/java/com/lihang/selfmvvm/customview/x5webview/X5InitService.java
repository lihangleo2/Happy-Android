package com.lihang.selfmvvm.customview.x5webview;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.lihang.selfmvvm.utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;

import androidx.annotation.Nullable;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class X5InitService extends IntentService {
    public X5InitService() {
        super(X5InitService.class.getSimpleName());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public X5InitService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initX5Web();
    }

    private void initX5Web() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.i("x5初始化哦", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
