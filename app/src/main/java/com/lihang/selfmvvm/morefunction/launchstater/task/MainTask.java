package com.lihang.selfmvvm.morefunction.launchstater.task;

/**
 * Created by leo
 * on 2020/4/29.
 */
public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
