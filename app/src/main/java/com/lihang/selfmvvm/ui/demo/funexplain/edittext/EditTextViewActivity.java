package com.lihang.selfmvvm.ui.demo.funexplain.edittext;

import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leo.utilspro.utils.ActivitysBuilder;
import com.leo.utilspro.utils.KeyBoardUtils;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.TextViewUtils;
import com.leo.utilspro.utils.ToastUtils;
import com.leo.utilspro.utils.UIUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.databinding.EdittextActivityBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //只可输入汉字，英文和数字
        initEditTextTwo();
        //editTextView不处理，且处于UI底部。正常点击会把ui顶上去。
        //套上一层scrollView。问题得到解决
        initEditTextThree();

    }

    private void initEditTextThree() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) binding.linearSon.getLayoutParams();
        layoutParams.height = (int) (UIUtils.getScreenHeight() - UIUtils.getStatusBarHeight() - getResources().getDimension(R.dimen.dp_45));
        binding.linearSon.setLayoutParams(layoutParams);
    }

    private void initEditTextTwo() {
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
        binding.editTwo.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(20)});
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
                //这里系统会触发2次是因为：按下算一次，抬起来算一次。
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
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shadowLayout_comments:
                ActivitysBuilder.build(this, CommentsActivity.class)
                        .startActivity();
                break;
        }
    }


    //点击页面其他地方关掉软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyBoardUtils.closeKeybord(binding.editOne);
                binding.editOne.clearFocus();
                binding.editTwo.clearFocus();
                binding.editThree.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
