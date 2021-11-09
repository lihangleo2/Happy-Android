package com.lihang.selfmvvm.bean;


import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * TODO<json数据源>
 *
 * @author: 小嵩
 * @date: 2017/3/16 15:36
 */

public class JsonBean implements IPickerViewData {


    /**
     * name : 省份
     * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
     */

    private String value;
    private List<CityBean> childs;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<CityBean> getChilds() {
        return childs;
    }

    public void setChilds(List<CityBean> childs) {
        this.childs = childs;
    }

    // 实现 IPickerViewData 接口，
    // 这个用来显示在PickerView上面的字符串，
    // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return this.value;
    }


    public static class CityBean {
        /**
         * name : 城市
         * area : ["东城区","西城区","崇文区","昌平区"]
         */

        private String value;
        private List<AreaBean> childs;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<AreaBean> getChilds() {
            return childs;
        }

        public void setChilds(List<AreaBean> childs) {
            this.childs = childs;
        }
    }


    public static class AreaBean {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
