package com.lihang.selfmvvm.ui.demo.funexplain.glide;

import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.bitmap.GlideRoundTransform;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.GlideActivityBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by leo
 * on 2021/11/8.
 */
public class GlideUserActivity extends BaseActivity<NormalViewModel, GlideActivityBinding> {
    private String imageUrl = "https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/54a4b40807034b3090708c935689345f~tplv-k3u1fbpfcp-zoom-crop-mark:1304:1304:1304:734.awebp?";

    @Override
    protected int getContentViewId() {
        return R.layout.glide_activity;
    }

    @Override
    protected void processLogic() {
        initImage();
    }

    private void initImage() {
        GlideRoundTransform transform = new GlideRoundTransform(binding.image1.getContext(), getResources().getDimension(R.dimen.dp_10));


        //【加载特殊角】
        //设置上下左右哪个角需要圆角
        transform.setNeedCorner(true, true, false, false);
        Glide.with(binding.image1)
                .load(imageUrl)
//                .placeholder(R.mipmap.place_hold)
//                .error(R.mipmap.place_hold)
                .transform(new CenterCrop(), transform)
                .into(binding.image1);

        //【加载正常圆角】
        Glide.with(binding.image2)
                .load(imageUrl)
//                .placeholder(R.mipmap.place_hold)
//                .error(R.mipmap.place_hold)
                .transform(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_10)))
                .transition(withCrossFade())//这里是带渐变动画(如果有问题,慎用)
                .into(binding.image2);


        //【加载圆形头像】
        Glide.with(binding.imageHead)
                .load(imageUrl)
//                .placeholder(R.mipmap.place_hold)
//                .error(R.mipmap.place_hold)
                .transform(new CenterCrop(), new CircleCrop())
                .into(binding.imageHead);


        //【加载获取图片的长宽】
        Glide.with(binding.image2)
                .asBitmap()
                .load(imageUrl)
                .transform(new CenterCrop(), new RoundedCorners((int) getResources().getDimension(R.dimen.dp_10)))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        LogUtils.i("加载图片的信息", resource.getWidth() + "====");
                        LogUtils.i("加载图片的信息", resource.getHeight() + "====");
                    }
                });



    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
