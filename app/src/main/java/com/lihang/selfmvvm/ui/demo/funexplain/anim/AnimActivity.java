package com.lihang.selfmvvm.ui.demo.funexplain.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
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
        switch (v.getId()) {
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
                if (binding.imageZan.getTag().equals("0")) {
                    binding.imageZan.setImageResource(R.drawable.animationlist_zan);
                    AnimationDrawable animationDrawable = (AnimationDrawable) binding.imageZan.getDrawable();
                    animationDrawable.selectDrawable(0);//设置默认帧
                    animationDrawable.start();
                    binding.imageZan.setTag("1");
                } else {
                    binding.imageZan.setImageResource(R.mipmap.card_zan_1);
                    binding.imageZan.setTag("0");
                }
                break;

            case R.id.button_tween:
                //补间动画

                /*
                补间动画 alpha xml实现:
                Animation animation_alpha = AnimationUtils.loadAnimation(this,R.anim.demo_alpha);
                binding.buttonTween.startAnimation(animation_alpha);
                ↓ 等同于 代码实现:
                AlphaAnimation animationCode = new AlphaAnimation(1.0f,0f);
                animationCode.setDuration(500);
                animationCode.setFillAfter(true);
                binding.buttonTween.startAnimation(animationCode);
                 */


                /*
                补间动画 translate xml实现:
                Animation animation_translate = AnimationUtils.loadAnimation(this,R.anim.demo_translate);
                binding.buttonTween.startAnimation(animation_translate);
                ↓ 等同于 代码实现:
                java要相对自身或者父容器的话只能用代码获取宽高了
                TranslateAnimation animationCode = new TranslateAnimation(0f,binding.buttonTween.getWidth()/2,0f,binding.buttonTween.getHeight()/2);
                animationCode.setDuration(500);
                animationCode.setFillAfter(true);
                binding.buttonTween.startAnimation(animationCode);
                 */


                /*
                补间动画 Scale xml实现:
                Animation animation_scale = AnimationUtils.loadAnimation(this,R.anim.demo_scale);
                binding.buttonTween.startAnimation(animation_scale);
                ↓ 等同于 代码实现:
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f,1.5f,1f,1.5f,binding.buttonTween.getWidth()/2,binding.buttonTween.getHeight()/2);
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                binding.buttonTween.startAnimation(scaleAnimation);
                 */


                /*
                补间动画 Rotate xml实现:
                Animation animation_rotate = AnimationUtils.loadAnimation(this,R.anim.demo_rotate);
                binding.buttonTween.startAnimation(animation_rotate);
                ↓ 等同于 代码实现:
                RotateAnimation rotateAnimation = new RotateAnimation(0,270,binding.buttonTween.getWidth()/2,binding.buttonTween.getHeight()/2);
                rotateAnimation.setDuration(500);
                rotateAnimation.setFillAfter(true);
                binding.buttonTween.startAnimation(rotateAnimation);
                 */


                //补间动画 Set xml实现:
                Animation animation_set = AnimationUtils.loadAnimation(this, R.anim.demo_set);
                binding.buttonTween.startAnimation(animation_set);
                /*
                ↓ 代码实现:
                注意，AnimationSet只支持顺序播放，不支持同时播放，(猜测原因，同时播放，直接在代码里调用可实现)
                AnimationSet animationSet = new AnimationSet(false);
                animationSet.setFillAfter(true);
                animationSet.addAnimation(rotateAnimation);
                animationSet.addAnimation(scaleAnimation);
                binding.buttonTween.startAnimation(animationSet);
                 */
                break;

            case R.id.button_property:

                /*
                属性动画 alpha xml实现:
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.demo_alpha);
                animator.setTarget(binding.buttonProperty);
                animator.start();
                ↓ 等同于 代码实现:
                ObjectAnimator objectAnimator_alpha = ObjectAnimator.ofFloat(binding.buttonProperty,"alpha",1.0f,0f);
                objectAnimator_alpha.setDuration(500);
                objectAnimator_alpha.start();
                 */


                /*
                属性动画 translate xml实现:
                Animator animator_translate = AnimatorInflater.loadAnimator(this, R.animator.demo_translate);
                animator_translate.setTarget(binding.buttonProperty);
                animator_translate.start();
                ↓ 等同于 代码实现:
                ObjectAnimator objectAnimator_translate = ObjectAnimator.ofFloat(binding.buttonProperty, "translationX", 0f, 500f);
                objectAnimator_translate.setDuration(500);
                objectAnimator_translate.start();
                 */



                /*
                属性动画 scale xml实现:
                Animator animator_scale = AnimatorInflater.loadAnimator(this,R.animator.demo_scale);
                binding.buttonProperty.setPivotX(binding.buttonProperty.getWidth()/2);
                animator_scale.setTarget(binding.buttonProperty);
                animator_scale.start();
                ↓ 等同于 代码实现:
                ObjectAnimator objectAnimator_scale = ObjectAnimator.ofFloat(binding.buttonProperty,"scaleX",0f,1f);
                objectAnimator_scale.setDuration(500);
                objectAnimator_scale.start();
                 */


                /*
                属性动画 rotate xml实现:
                Animator animator_rotate = AnimatorInflater.loadAnimator(this,R.animator.demo_rotate);
                animator_rotate.setTarget(binding.buttonProperty);
                animator_rotate.start();
                ↓ 等同于 代码实现:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(binding.buttonProperty,"rotation",0f,360f);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                 */


                //除此之外，还有一个ValueAnimator.
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentAlpha = (float) animation.getAnimatedValue();
                        binding.buttonProperty.setAlpha(currentAlpha);
                    }
                });
                valueAnimator.start();

                break;
        }
    }
}
