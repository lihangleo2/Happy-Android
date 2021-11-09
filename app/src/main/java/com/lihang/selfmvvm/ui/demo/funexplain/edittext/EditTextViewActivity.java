package com.lihang.selfmvvm.ui.demo.funexplain.edittext;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.leo.utilspro.utils.KeyBoardUtils;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.TextViewUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.EdittextActivityBinding;

import static com.leo.utilspro.utils.KeyBoardUtils.isShouldHideInput;

/**
 * Created by leo
 * on 2021/11/9.
 */
public class EditTextViewActivity extends BaseActivity<NormalViewModel, EdittextActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.edittext_activity;
    }

    @Override
    protected void processLogic() {
        //发送按钮监听 及 软键盘删除监听
        initEditTextOne();
    }

    private void initEditTextOne() {
        //监听软键盘上的发送按钮
        binding.editOne.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String numString = v.getText().toString();

                    if (TextUtils.isEmpty(numString)) {
                        return false;
                    }

                    ToastUtils.showToast("点击发送“触发”====>" + numString);
                    KeyBoardUtils.closeKeybord(binding.editOne);
                    binding.editOne.clearFocus();
                    return true;
                }
                return false;
            }
        });


        //监听软键盘删除键的
        binding.editOne.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //这里会触发2次是因为按一次，抬起来一次。
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                    String numString = ((TextView) v).getText().toString();
                    ToastUtils.showToast("软键盘删除“触发”====>" + numString);
                }
                return false;
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }


    //点击页面其他地方关掉软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyBoardUtils.closeKeybord(binding.editOne);
                binding.editOne.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
