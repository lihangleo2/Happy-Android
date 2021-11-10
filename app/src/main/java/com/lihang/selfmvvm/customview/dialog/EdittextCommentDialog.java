package com.lihang.selfmvvm.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lihang.selfmvvm.R;


/**
 * Created by leo on 2019/1/15.
 */
public class EdittextCommentDialog extends Dialog {

    private View.OnClickListener listener;
    private EditText edit_content;
    private ImageView image_send;
    private TextView txt_cover;
    private TextView txt_more_text;
    private RelativeLayout linear_bottom;


    private int type; // 1: 是 1级评论  2是二级平路

    public EdittextCommentDialog(Context context, View.OnClickListener listener) {
        this(context, R.style.Dialog_nobg);
        setCanceledOnTouchOutside(true);
        this.listener = listener;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public EdittextCommentDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();//设置全屏重写show方法
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }


    public RelativeLayout getLayoutBottom() {
        return linear_bottom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_newapp_keyword);
        edit_content = findViewById(R.id.edit_content);
        txt_cover = findViewById(R.id.txt_cover);
        txt_more_text = findViewById(R.id.txt_more_text);
        linear_bottom = findViewById(R.id.linear_bottom);
        image_send = findViewById(R.id.image_send);
        image_send.setOnClickListener(listener);


        edit_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int num = s.toString().length();
                if (num == 0) {
                    txt_cover.setVisibility(View.VISIBLE);
                } else {
                    txt_cover.setVisibility(View.GONE);
                }
                if (num > 100) {
                    txt_more_text.setVisibility(View.VISIBLE);
                    txt_more_text.setText("-" + (num - 100));
                } else {
                    txt_more_text.setVisibility(View.GONE);
                }
            }
        });
    }


    public TextView getTxt_more_text(){
        return txt_more_text;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public EditText getEdit_content() {
        return edit_content;
    }

    public String getContentText() {
        return edit_content.getText().toString().trim();
    }


    public void showKeyboard() {
        if (edit_content != null) {
            //设置可获得焦点
            edit_content.setFocusable(true);
            edit_content.setFocusableInTouchMode(true);
            //请求获得焦点
            edit_content.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) edit_content
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edit_content, 0);
        }
    }

    public void setMaxLength(int maxLength) {
        edit_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setHint(String hintStr) {
        edit_content.setHint(hintStr);
    }

}
