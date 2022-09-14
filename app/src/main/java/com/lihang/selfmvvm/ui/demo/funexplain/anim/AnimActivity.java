package com.lihang.selfmvvm.ui.demo.funexplain.anim;

import android.animation.AnimatorSet;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.AnimActivityBinding;

/**
 * @Author leo
 * @Date 2022/9/9
 */
public class AnimActivity extends BaseActivity<NormalViewModel, AnimActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.anim_activity;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //详细连接：https://blog.csdn.net/yanbober/article/details/46481171
//            android:detachWallpaper	setDetachWallpaper(boolean)	是否在壁纸上运行
//            android:duration	    setDuration(long)	            动画持续时间，毫秒为单位
//            android:fillAfter	    setFillAfter(boolean)	        控件动画结束时是否保持动画最后的状态
//            android:fillBefore	setFillBefore(boolean)	        控件动画结束时是否还原到开始动画前的状态
//            android:fillEnabled	setFillEnabled(boolean)	        与android:fillBefore效果相同
//            android:interpolator	setInterpolator(Interpolator)	设定插值器（指定的动画效果，譬如回弹等）
//            android:repeatCount	setRepeatCount(int)	            重复次数
//            android:repeatMode	setRepeatMode(int)	            重复类型有两个值，reverse表示倒序回放，restart表示从头播放
//            android:startOffset	setStartOffset(long)	        调用start函数之后等待开始运行的时间，单位为毫秒
//            android:zAdjustment	setZAdjustment(int)	            表示被设置动画的内容运行时在Z轴上的位置（top/bottom/normal），默认为normal
            case R.id.image_zan:
                //帧动画
                if (binding.imageZan.getTag().equals("0")){
                    binding.imageZan.setImageResource(R.drawable.animationlist_zan);
                    AnimationDrawable animationDrawable = (AnimationDrawable) binding.imageZan.getDrawable();
                    animationDrawable.selectDrawable(0);//设置默认帧
                    animationDrawable.start();
                    binding.imageZan.setTag("1");
                }else {
                    binding.imageZan.setImageResource(R.mipmap.card_zan_1);
                    binding.imageZan.setTag("0");
                }
                break;

            case R.id.button_alpha:
                /*
                补间动画 alpha
                Animation animation_alpha = AnimationUtils.loadAnimation(this,R.anim.demo_alpha);
                binding.buttonAlpha.startAnimation(animation_alpha);
                ↓ 等同于
                AlphaAnimation animationCode = new AlphaAnimation(1.0f,0f);
                animationCode.setDuration(500);
                animationCode.setFillAfter(true);
                binding.buttonAlpha.startAnimation(animationCode);
                 */


                /*
                补间动画 translate
                Animation animation_translate = AnimationUtils.loadAnimation(this,R.anim.demo_translate);
                binding.buttonAlpha.startAnimation(animation_translate);
                ↓ 等同于
                java要相对自身或者父容器的话只能用代码获取宽高了
                TranslateAnimation animationCode = new TranslateAnimation(0f,binding.buttonAlpha.getWidth()/2,0f,binding.buttonAlpha.getHeight()/2);
                animationCode.setDuration(500);
                animationCode.setFillAfter(true);
                binding.buttonAlpha.startAnimation(animationCode);
                 */


                /*
                补间动画 Scale
                Animation animation_scale = AnimationUtils.loadAnimation(this,R.anim.demo_scale);
                binding.buttonAlpha.startAnimation(animation_scale);
                ↓ 等同于
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f,1.5f,1f,1.5f,binding.buttonAlpha.getWidth()/2,binding.buttonAlpha.getHeight()/2);
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                binding.buttonAlpha.startAnimation(scaleAnimation);
                 */


                /*
                补间动画 Rotate
                Animation animation_rotate = AnimationUtils.loadAnimation(this,R.anim.demo_rotate);
                binding.buttonAlpha.startAnimation(animation_rotate);
                ↓ 等同于
                RotateAnimation rotateAnimation = new RotateAnimation(0,270,binding.buttonAlpha.getWidth()/2,binding.buttonAlpha.getHeight()/2);
                rotateAnimation.setDuration(500);
                rotateAnimation.setFillAfter(true);
                binding.buttonAlpha.startAnimation(rotateAnimation);
                 */


                /*
                补间动画 Set
                Animation animation_set = AnimationUtils.loadAnimation(this,R.anim.demo_set);
                binding.buttonAlpha.startAnimation(animation_set);
                ↓
                注意，AnimationSet只支持顺序播放，不支持同时播放，(猜测原因，同时播放，直接在代码里调用可实现)
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.setFillAfter(true);
                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(scaleAnimation);
                binding.buttonAlpha.startAnimation(animationSet);
                 */










                break;
        }
    }
}
