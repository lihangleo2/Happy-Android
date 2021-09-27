package com.lihang.selfmvvm.customview.dialog;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lihang.ShadowLayout;
import com.lihang.selfmvvm.R;


public class DoctorDialog {
	Context context;
	private Display display;
	private Dialog dialog;

	private TextView txt_title;
	private TextView txt_content;
	private TextView btn_cancle;
	private TextView btn_confirm;
	private ShadowLayout shadowLayout_cancle;


	public DoctorDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	public DoctorDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_doctor_normal, null);
		txt_title = view.findViewById(R.id.txt_title);
		txt_content = view.findViewById(R.id.txt_content);
		btn_cancle = view.findViewById(R.id.btn_cancle);
		shadowLayout_cancle = view.findViewById(R.id.shadowLayout_cancle);


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

	public DoctorDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}


	public DoctorDialog setContent(String content) {
		if (TextUtils.isEmpty(content)) {
			txt_content.setText("内容");
		} else {
			txt_content.setText(content);
		}
		return this;
	}

	public DoctorDialog setTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			txt_title.setText("提示");
		} else {
			txt_title.setText(title);
		}
		return this;
	}


	public DoctorDialog setConfirmMsg(String msg) {

		if (TextUtils.isEmpty(msg)) {
			btn_confirm.setText("确定");
		} else {
			btn_confirm.setText(msg);
		}
		return this;
	}



	public DoctorDialog setConcleMsg(String msg) {
		if (TextUtils.isEmpty(msg)) {
			btn_cancle.setText("取消");
		} else {
			btn_cancle.setText(msg);
		}
		return this;
	}

	public void setCancaleGone(){
		shadowLayout_cancle.setVisibility(View.GONE);
	}


	public void show() {
		if (dialog != null) {
			dialog.show();
		}
	}


	public DoctorDialog setConfirmButton(final View.OnClickListener listener) {
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
