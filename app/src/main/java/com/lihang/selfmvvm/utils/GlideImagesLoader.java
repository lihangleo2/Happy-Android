package com.lihang.selfmvvm.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lihang.selfmvvm.R;
import com.youth.banner.loader.ImageLoader;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by leo
 * on 2020/10/21.
 */
public class GlideImagesLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path)
//                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transition(withCrossFade())
                .centerCrop().into(imageView);

        //这里有点奇怪4.9的，不主动倒包。渐变加载动画，可以传参int durtion.默认300
    }
}
