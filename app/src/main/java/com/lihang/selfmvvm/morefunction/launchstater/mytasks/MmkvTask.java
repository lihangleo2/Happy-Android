package com.lihang.selfmvvm.morefunction.launchstater.mytasks;

import com.leo.utilspro.utils.MmkvUtils;
import com.lihang.selfmvvm.morefunction.launchstater.task.Task;

/**
 * Created by leo
 * on 2020/4/29.
 */
public class MmkvTask extends Task {
    @Override
    public void run() {
        MmkvUtils.init();
    }
}
