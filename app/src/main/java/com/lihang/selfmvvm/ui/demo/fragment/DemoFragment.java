package com.lihang.selfmvvm.ui.demo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.leo.utilspro.utils.ClickableSpanListener;
import com.leo.utilspro.utils.SpannableStringBuilder;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.FragmentDemoBinding;
import com.lihang.selfmvvm.launchstater.utils.Utils;
import com.lihang.selfmvvm.ui.MainActivity;

import androidx.fragment.app.Fragment;

/**
 * Created by leo
 * on 2020/10/21.
 */
public class DemoFragment extends BaseFragment<NormalViewModel, FragmentDemoBinding> {

    int index;

    //到时候这里用来传值
    public static DemoFragment newFragment(int index) {
        DemoFragment fragment = new DemoFragment();
        fragment.index = index;
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        binding.txt.setText("第" + index + "个");

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
