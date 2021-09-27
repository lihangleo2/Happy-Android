package com.lihang.selfmvvm.morefunction.launchstater;

import android.os.Looper;
import android.os.MessageQueue;


import com.lihang.selfmvvm.morefunction.launchstater.task.DispatchRunnable;
import com.lihang.selfmvvm.morefunction.launchstater.task.Task;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by leo
 * on 2020/4/29.
 */
public class DelayInitDispatcher {
    private Queue<Task> mDelayTasks = new LinkedList<>();

    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    public DelayInitDispatcher addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
