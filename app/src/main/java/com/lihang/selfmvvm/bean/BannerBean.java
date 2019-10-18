package com.lihang.selfmvvm.bean;

import java.io.Serializable;

/**
 * Created by leo
 * on 2019/10/15.
 */
public class BannerBean implements Serializable {

    /**
     * desc : Android高级进阶直播课免费学习
     * id : 23
     * imagePath : https://wanandroid.com/blogimgs/67c28e8c-2716-4b78-95d3-22cbde65d924.jpeg
     * isVisible : 1
     * order : 0
     * title : Android高级进阶直播课免费学习
     * type : 0
     * url : https://url.163.com/4bj
     */

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
