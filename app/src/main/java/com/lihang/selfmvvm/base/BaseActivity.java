package com.lihang.selfmvvm.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.JsonSyntaxException;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.customview.CustomProgress;
import com.lihang.selfmvvm.bean.basebean.Resource;
import com.lihang.selfmvvm.utils.ToastUtils;
import com.lihang.selfmvvm.utils.networks.NetWorkUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by leo
 * on 2019/10/15.
 */
public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends RxFragmentActivity
        implements View.OnClickListener {

    //获取当前activity布局文件
    protected abstract int getContentViewId();

    //处理逻辑业务
    protected abstract void processLogic();


    protected VM mViewModel;
    protected VDB binding;

    private CustomProgress dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getContentViewId());
        binding.setLifecycleOwner(this);
        createViewModel();
        processLogic();

    }

    public void createViewModel() {
        if (mViewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            mViewModel = (VM) ViewModelProviders.of(this).get(modelClass);
            mViewModel.setObjectLifecycleTransformer(bindLifecycle());
        }
    }


    public LifecycleTransformer bindLifecycle() {
        LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
        return objectLifecycleTransformer;
    }

    public Context getContext() {
        return this;
    }

    public abstract class OnCallback<T> implements Resource.OnHandleCallback<T> {
        @Override
        public void onLoading(String msg) {
            if (dialog == null) {
                dialog = CustomProgress.show(BaseActivity.this, "", true, null);
            }

            if (!TextUtils.isEmpty(msg)) {
                dialog.setMessage(msg);
            }

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!NetWorkUtils.isNetworkConnected(getContext())) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_network_error));
                return;
            }

            if (throwable instanceof ConnectException) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_server_error));
            } else if (throwable instanceof SocketTimeoutException) {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_server_timeout));
            } else if (throwable instanceof JsonSyntaxException) {
                ToastUtils.showToast("数据解析出错");
            } else {
                ToastUtils.showToast(getContext().getResources().getString(R.string.result_empty_error));
            }
        }

        @Override
        public void onFailure(String msg) {
            ToastUtils.showToast(msg);
        }

        @Override
        public void onCompleted() {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        @Override
        public void onProgress(int precent, long total) {

        }
    }
}
