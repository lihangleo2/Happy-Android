package com.lihang.selfmvvm.base.launchstater.mytasks;

import com.leo.utilspro.utils.LogUtils;
import com.lihang.selfmvvm.base.launchstater.task.Task;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by leo
 * on 2020/4/29.
 */
public class X5WebTask extends Task {
    @Override
    public void run() {
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
        QbSdk.initX5Environment(mContext, cb);
    }
}
