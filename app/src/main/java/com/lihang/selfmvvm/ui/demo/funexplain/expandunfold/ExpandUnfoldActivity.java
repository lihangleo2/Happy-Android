package com.lihang.selfmvvm.ui.demo.funexplain.expandunfold;

import android.view.View;

import com.leo.utilspro.utils.HiddenAnimUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.ExpandUnfoldActivityBinding;

/**
 * Created by leo
 * on 2021/11/11.
 */
public class ExpandUnfoldActivity extends BaseActivity<NormalViewModel, ExpandUnfoldActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.expand_unfold_activity;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        binding.setOnCliclListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_explain:
                if (getStringByUI(binding.txtExplain).equals("展开")) {
                    HiddenAnimUtils.openAnim(binding.linearExplain, (int) getResources().getDimension(R.dimen.dp_100));
                    binding.txtExplain.setText("收起");
                } else {
                    HiddenAnimUtils.closeAnimate(binding.linearExplain);
                    binding.txtExplain.setText("展开");
                }
                break;
        }
    }
}
