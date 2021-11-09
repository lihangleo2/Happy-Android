package com.lihang.selfmvvm.ui.demo.set.editor;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.leo.utilspro.utils.KeyBoardUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.EditornameActivityBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leo
 * on 2021/11/8.
 */
public class EditorNameActivity extends BaseActivity<NormalViewModel, EditornameActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.editorname_activity;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        binding.setOnClickListener(this);

        InputFilter inputFilter = new InputFilter() {
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.showToast("只能输入汉字,英文，数字");
                    return "";
                }
            }
        };
        binding.editName.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(20)});


        binding.editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    binding.imgDelete.setVisibility(View.GONE);
                } else {
                    binding.imgDelete.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_delete:
                binding.editName.setText("");
                break;
        }
    }


    //这里是收起软键盘的操作(这里额外加了判断，点击x,清楚内容的时候，不收起。Explain.java里也有说明)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            int[] leftTop = {0, 0};
            binding.imgDelete.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + binding.imgDelete.getHeight(), right = left
                    + binding.imgDelete.getWidth();
            if (ev.getX() > left && ev.getX() < right
                    && ev.getY() > top && ev.getY() < bottom) {
            } else {
                if (KeyBoardUtils.isShouldHideInput(v, ev)) {
                    binding.editName.clearFocus();
                    KeyBoardUtils.closeKeybord(EditorNameActivity.this);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
