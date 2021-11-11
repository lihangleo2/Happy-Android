package com.lihang.selfmvvm.ui.demo.funexplain.shareanim;

import android.app.SharedElementCallback;
import android.os.Build;
import android.transition.Transition;
import android.view.View;

import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.PreferenceUtil;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.common.SystemConst;
import com.lihang.selfmvvm.databinding.ShareActivityBinding;

import java.util.List;
import java.util.Map;

/**
 * Created by leo
 * on 2021/11/11.
 */
public class ShareAnimActivity extends BaseActivity<NormalViewModel, ShareActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.share_activity;
    }

    @Override
    protected void processLogic() {
        withAnim();
    }

    @Override
    protected void setListener() {
        binding.leoTitleBar.bar_left_btn.setOnClickListener(v->{
            finishAfterTransition();
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void withAnim() {
        if (Build.VERSION.SDK_INT >= 22) {
            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    sharedElements.clear();
                    sharedElements.put("shareView", binding.shadowLayoutShare);
                }
            });
        }

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                //共享元素动画结束。一般在这里去请求网络渲染页面会比较流畅
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }
}
