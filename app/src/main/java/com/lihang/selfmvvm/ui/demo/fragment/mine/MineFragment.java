package com.lihang.selfmvvm.ui.demo.fragment.mine;

import android.os.Bundle;
import android.view.View;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseFragment;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.MineFragmentBinding;
import com.lihang.selfmvvm.ui.demo.set.SetActivity;

/**
 * Created by leo
 * on 2021/9/26.
 */
public class MineFragment extends BaseFragment<NormalViewModel, MineFragmentBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_setting:
                ActivitysBuilder.build(this, SetActivity.class)
                        .startActivity();
                break;
        }
    }
}
