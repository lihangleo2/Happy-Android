package com.lihang.selfmvvm.customview.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.utilspro.utils.LogUtils;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.morefunction.apkupdate.DownLoadService;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Consumer;


/**
 * Created by lihang on 2017/8/14.
 */

public class UpdateDialog extends Dialog {

    private TextView text_message;
    private TextView text_message_other;
    private TextView text_update;//确认升级
    private TextView text_calcle_down;//取消升级
    private TextView progress_percent;
    private RxPermissions rxPermissions;
    private Context context;
    private String url;

    private int max;
    private int progressValue;
    private ProgressBar progress;
    private Handler mViewUpdateHandler;

    private RelativeLayout relative_showApp;
    private RelativeLayout relative_loading;

    private ImageView image_title;


    public UpdateDialog(Context context) {
        this(context, R.style.MyDialogStyleBottom);
//        setCanceledOnTouchOutside(false);
//        setCancelable(false);
        rxPermissions = new RxPermissions((FragmentActivity) context);
        this.context = context;
    }


    public UpdateDialog(Context context, int theme) {
        super(context, theme);
//        setCanceledOnTouchOutside(false);
//        setCancelable(false);
    }

    @Override
    public void show() {
        super.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_app);
        text_message = findViewById(R.id.text_message);
        text_message_other = findViewById(R.id.text_message_other);
        text_update = findViewById(R.id.text_update);
        text_calcle_down = findViewById(R.id.text_calcle_down);
        progress_percent = findViewById(R.id.progress_percent);
        progress = findViewById(R.id.progress);
        relative_showApp = findViewById(R.id.relative_showApp);
        relative_loading = findViewById(R.id.relative_loading);
        image_title = findViewById(R.id.image_title);
        text_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            relative_showApp.setVisibility(View.GONE);
                            relative_loading.setVisibility(View.VISIBLE);
                            Intent service = new Intent(context, DownLoadService.class);
                            service.putExtra("downloadurl", url);
                            context.startService(service);
                            LogUtils.i("文件下载", "=====");

                        } else {
                            Toast.makeText(context, "SD卡下载权限被拒绝", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        text_calcle_down.setOnClickListener(v -> {
            Intent service = new Intent(context, DownLoadService.class);
            context.stopService(service);
            UpdateDialog.this.dismiss();
        });


        mViewUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        int num1 = progressValue / 10;
                        int num2 = max / 1000;
                        if (num2 != 0) {
                            int percent = num1 / num2;
                            progress_percent.setText(percent + "%");
                            LogUtils.i("我看看这里的数据呗", percent + "%");
                        }

                        break;

                    case 1:
                        if (max > 10) {
                            double dMax = (double) max / (double) (1024 * 1024);
                            String result = String.format("%.2f", dMax);
                        } else {
                        }
                        break;
                }

            }
        };


    }


    public void setProgress(int progressValue) {
        this.progressValue = progressValue;
        progress.setProgress(progressValue);
        mViewUpdateHandler.sendEmptyMessage(0);
        LogUtils.i("我看看这里的数据呗", progressValue + "====");

    }

    public void setMessage(String newVersion, String oldVersion, String url) {
//        text_message.setText("发现新版本:  " + newVersion);
        text_message.setText("提示");
//        text_message_other.setText("当前版本:  " + oldVersion);
        text_message_other.setText("是否确认下载 ");
        this.url = url;
    }

    public void setTotal(int max) {
        this.max = max;
        progress.setMax(max);
        mViewUpdateHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
