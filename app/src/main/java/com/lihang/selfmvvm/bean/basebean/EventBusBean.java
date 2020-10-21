package com.lihang.selfmvvm.bean.basebean;

import java.io.Serializable;

/**
 * Created by leo
 * on 2019/9/25.
 * EventBus传递类，统一管理
 */
public class EventBusBean implements Serializable {
    //  1、刷新首页
    //  2、收藏页，取消收藏的通知首页刷新
    private int type;
    private Object value;

    public EventBusBean(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public EventBusBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
