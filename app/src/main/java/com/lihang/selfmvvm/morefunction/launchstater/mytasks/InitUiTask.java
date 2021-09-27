package com.lihang.selfmvvm.morefunction.launchstater.mytasks;


import com.lihang.selfmvvm.morefunction.launchstater.task.MainTask;

/**
 * Created by leo
 * on 2020/4/29.
 * 例子
 * 主线程执行的task
 */

public class InitUiTask extends MainTask {
    //调用启动器等待的时候，这个task如果没有完成，启动器就会等待
//    @Override
//    public boolean needWait() {
//        return true;
//    }


    //有些task要依赖某些task；意思在执行此Task之前，InitThreadTask必须执行完
//    @Override
//    public List<Class<? extends Task>> dependsOn() {
//        List<Class<? extends Task>> task = new ArrayList<>();
//        task.add(InitThreadTask.class);
//        return task;
//    }

    @Override
    public void run() {

    }
}
