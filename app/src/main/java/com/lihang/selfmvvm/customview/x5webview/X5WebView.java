package com.lihang.selfmvvm.customview.x5webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.customview.LeoTitleBar;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by leo
 * on 2019/10/21.
 */

//这样写完后，之后和正常的webView一个用法，只不过走的是x5内核
public class X5WebView extends WebView {
    private ProgressBar mProgressBar;
    private LeoTitleBar leoTitleBar;

    public void setTitleBar(LeoTitleBar leoTitleBar) {
        this.leoTitleBar = leoTitleBar;
    }

    private WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    getContext().startActivity(intent);
                    return true;
                }
            } catch (Exception e) {
                return true;
            }

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            //网页出错了，用于消失progressBar
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.GONE);
            }
            Toast.makeText(getContext(), "网页加载失败", Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //加载失败多次请求证书
            handler.proceed();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            //webView加载结束的动作，用于消失progressBar
        }
    };


    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                if (mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                    mProgressBar.startAnimation(animation);
                }
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mProgressBar.setProgress(newProgress, true);
            } else {
                mProgressBar.setProgress(newProgress);
            }

        }

        @Override
        public void onReceivedTitle(WebView webView, String s) {
            super.onReceivedTitle(webView, s);
            if (leoTitleBar != null && !TextUtils.isEmpty(s)) {
                leoTitleBar.setTitle(s);
            }
        }
    };

    private Animation animation;

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        animation = AnimationUtils.loadAnimation(arg0, R.anim.alpha_progress);
        mProgressBar = new ProgressBar(arg0, null,
                android.R.attr.progressBarStyleHorizontal);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 8);
        mProgressBar.setLayoutParams(layoutParams);

        Drawable drawable = getContext().getResources().getDrawable(
                R.drawable.shap_progressbar_web);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
        initWebViewSettings();
    }


    private void initWebViewSettings() {
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDomStorageEnabled(true);

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //开启缓存LOAD_CACHE_ELSE_NETWORK//LOAD_NO_CACHE关闭缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //解决webView不加载图片
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        WebSettings webSetting = this.getSettings();
//        webSetting.setJavaScriptEnabled(true);
//        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
//
//        webSetting.setAllowFileAccess(true);//设置可以访问文件
//        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//设置WebView底层的布局算法，
//        webSetting.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSetting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件
//        webSetting.setUseWideViewPort(true);//将图片调整到适合webview的大小
//        webSetting.setSupportMultipleWindows(true);
//        webSetting.setLoadWithOverviewMode(true);
//        webSetting.setAppCacheEnabled(true);
//        // webSetting.setDatabaseEnabled(true);
//        webSetting.setDomStorageEnabled(true);
//        webSetting.setGeolocationEnabled(true);
//        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//        webSetting.setCacheMode(android.webkit.WebSettings.LOAD_NO_CACHE); //开启缓存LOAD_CACHE_ELSE_NETWORK//LOAD_NO_CACHE关闭缓存
//        webSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //解决webView不加载图片
//            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        setWebViewClient(client);
        setWebChromeClient(webChromeClient);
    }


}
