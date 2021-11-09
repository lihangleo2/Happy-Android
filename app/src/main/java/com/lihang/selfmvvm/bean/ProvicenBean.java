package com.lihang.selfmvvm.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2020/5/20.
 */
public class ProvicenBean implements Serializable {
    private ArrayList<JsonBean> data;

    public ArrayList<JsonBean> getData() {
        return data;
    }

    public void setData(ArrayList<JsonBean> data) {
        this.data = data;
    }
}
