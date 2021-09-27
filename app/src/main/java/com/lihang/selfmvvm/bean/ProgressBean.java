package com.lihang.selfmvvm.bean;

import java.io.Serializable;

/**
 * Created by Leo on 2018/8/23.
 */

public class ProgressBean implements Serializable {
    private int progress;
    private int max;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
