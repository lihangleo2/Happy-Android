package com.lihang.selfmvvm.customview.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lihang.selfmvvm.R;


public class IosAlertDialogByleo {
	Context context;
	private Display display;
	private Dialog dialog;
	private TextView txt_msg;
	private TextView btn_cancle;
	private TextView btn_confirm;

	public IosAlertDialogByleo(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
		        .getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public IosAlertDialogByleo builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
		        R.layout.dialog_like_ios, null);
		txt_msg = view.findViewById(R.id.txt_msg);
		btn_cancle = view.findViewById(R.id.btn_cancle);
		btn_confirm = view.findViewById(R.id.btn_confirm);
		btn_cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);

//		// 调整dialog背景大小
//		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
//		        .getWidth() * 0.5), LayoutParams.WRAP_CONTENT));

		return this;
	}

	public IosAlertDialogByleo setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}



	public IosAlertDialogByleo setMsg(String msg) {
		if (TextUtils.isEmpty(msg)) {
			txt_msg.setText("内容");
		} else {
			txt_msg.setText(msg);
		}
		return this;
	}


	public IosAlertDialogByleo setConfirmMsg(String msg) {
		if (TextUtils.isEmpty(msg)) {
			btn_confirm.setText("确定");
		} else {
			btn_confirm.setText(msg);
		}
		return this;
	}

	public IosAlertDialogByleo setConcleMsg(String msg) {
		if (TextUtils.isEmpty(msg)) {
			btn_cancle.setText("取消");
		} else {
			btn_cancle.setText(msg);
		}
		return this;
	}




	public void show() {
		if (dialog != null) {
			dialog.show();
		}
	}


	public IosAlertDialogByleo setConfirmButton(final View.OnClickListener listener) {
		btn_confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				listener.onClick(v);
			}
		});
		return this;
	}

}
