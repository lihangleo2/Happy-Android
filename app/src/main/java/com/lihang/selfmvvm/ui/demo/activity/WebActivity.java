package com.lihang.selfmvvm.ui.demo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.base.bean.EventBusBean;
import com.lihang.selfmvvm.databinding.WebActivityBinding;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class WebActivity extends BaseActivity<NormalViewModel, WebActivityBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.web_activity;
    }

    @Override
    protected void processLogic() {
        String url = getIntent().getStringExtra("url");
        binding.webViewX5.setTitleBar(binding.leoTitleBar);
        binding.webViewX5.loadUrl(url);
        binding.leoTitleBar.bar_left_btn.setOnClickListener(this);
        //与web的js交互，"android" 是web端写的注册方法要保持一致。
        //binding.webViewX5.addJavascriptInterface(new AndroidJavaScript(WebActivity.this), "android");
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.webViewX5.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        binding.webViewX5.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webViewX5.destroy();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_btn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.webViewX5.canGoBack()) {
            binding.webViewX5.goBack();
        } else {
            super.onBackPressed();
        }
    }


//    /**
//     * AndroidJavaScript
//     * 本地与h5页面交互的js类，这里写成内部类了
//     * returnAndroid方法上@JavascriptInterface一定不能漏了
//     */
//    @SuppressLint("JavascriptInterface")
//    private class AndroidJavaScript {
//        Context mContxt;
//
//        public AndroidJavaScript(Context mContxt) {
//            this.mContxt = mContxt;
//        }
//
//        /**
//         * 与js交互时用到的方法，在js里直接调用的
//         */
//        @JavascriptInterface
//        public void onClickToBack() {// web端写的方法名要保持一致
//            EventBus.getDefault().post(new EventBusBean(15));
//            finish();
//
//        }
//    }
}
