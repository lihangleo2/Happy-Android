package com.lihang.selfmvvm.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

/**
 * Created by leo
 * on 2020/6/5.
 */
public class DataBindingHelper {
    //用@BindingAdapter标注，有点类似自定义属性，后面是属性名，方法体类似得到属性值后去做的事情。
    //第一个参数：是当前的控件类型，其实也可以写成View，但是要加载还是要判断是否是imageView
    //第二个参数：是网络加载的url。
    @BindingAdapter("imageSrc")
    public static void loadImage(ImageView imageView, int id) {
        imageView.setImageResource(id);
    }

    //@BindingAdapter还能修改系统属性值，这是修改textView的属性，意思只要使用DataBinding给textView设置setText值的，
    //都会加上后面这段 " - 我是通过方法加的"
    //我这里先注释掉了。不然整个项目的textView都会加上整个，如果要测试，可以打开
    //@BindingAdapter("android:text")
    //public static void setText(TextView textView, String testStr) {
    //    textView.setText(testStr + " - 我是通过方法加的");
    //}

}
