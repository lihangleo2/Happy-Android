package com.leo.utilspro;

import com.leo.utilspro.utils.bitmap.PictureUtil;
import com.leo.utilspro.utils.crash.CrashHandler;
import com.leo.utilspro.utils.networks.NetStateChangeReceiver;
import com.leo.utilspro.utils.threadpool.ThreadManager;

import androidx.recyclerview.widget.SimpleItemAnimator;

/**
 * Created by leo
 * on 2020/9/22.
 */
public class Explain {

    /**
     * glide：GlideRoundTransform 用法
     * */
    //说明：glide加载不同圆角


//  1、context   2、圆角大小
//  GlideRoundTransform transform = new GlideRoundTransform(binding.image.getContext(), DensityUtils.dp2px(5));
//  设置上下左右哪个角需要圆角
//  transform.setNeedCorner(true, true, false, false);
//  Glide.with(binding.image)
//  .load(item.getImg())
//  .placeholder(R.mipmap.place_hold)
//  .error(R.mipmap.place_hold)
//  .transform(transform)
//  .into(binding.image);


//──────────────────────────────────────────────────────────────────────────────

    /**
     * 图片压缩：PictureUtil 用法
     * */
    //说明：需要打开存储权限


//    1、要压缩的图片路径  2、压缩的质量0 - 100
//    返回的是压缩后的filePath
//    PictureUtil.compressImage(imageItems.get(i).path, 75)


    /*
    *
    *
      Flowable.just(Selects).subscribeOn(Schedulers.io())
                .map(new Function<ArrayList<ImageItem>, ArrayList<File>>() {
                    @Override
                    public ArrayList<File> apply(ArrayList<ImageItem> imageItems) throws Exception {
                        for (int i = 0; i < Selects.size(); i++) {
                            arrayListCompress.add(new File(PictureUtil.compressImage(Selects.get(i).path, 50)));
                        }
                        return arrayListCompress;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<File>>() {
                    @Override
                    public void accept(ArrayList<File> files) throws Exception {
                        PictureProgressUtil.initData(files.size());
                        mViewModel.upLoadPicss("condition", "files", files, ParamsBuilder.build().isShowDialog(false)).observe(FastInquiryActivity.this, resource -> {
                            resource.handler(new OnCallback<List<PicureBean>>() {
                                @Override
                                public void onSuccess(List<PicureBean> data) {
                                    dialog.setMessage("正在下单..");

                                }

                                @Override
                                public void onProgress(int precent, long total) {
                                    super.onProgress(precent, total);
                                    dialog.setMessage("图片上传" + PictureProgressUtil.setCurrentProgress(precent) + "%");
                                }

                            });
                        });
                    }
                });
    * */




//──────────────────────────────────────────────────────────────────────────────

    /**
     * 捕捉崩溃日志：CrashHandler 用法
     * */
    //需要打开存储权限。崩溃日志在：文件管理/内部存储/App_Crash 内


//    在Application中初始化；如果后面带了类，那么app会自动重启。如果没有带类，那么app会闪退
//    CrashHandler crashHandler = CrashHandler.getInstance();
//    crashHandler.init(getApplicationContext()，WelComeActivity.class);


//──────────────────────────────────────────────────────────────────────────────

    /**
     * 网络状态变化监听： NetStateChangeReceiver
     * */


    /*
    * 监听网络状态变化
    * NetStateChangeReceiver
    * 需要加上权限 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    *
    * 在需要监听的页面注册(onCreate)：
    * NetWorkUtils.register(this,this);
    *
    * 在注册的页面释放(onDestroy)：
    * 解绑观察者
    * NetWorkUtils.unregister(this,this);
    *
    * 页面要实现NetStateChangeObserver网络监听。其中有网络连接和网络断开的监听
    * @Override
    * public void onNetDisconnected() {
        LogUtils.i("网络已连接", "网络断开了断开了");
        ToastUtils.showToast("网络已断开");
      }

      @Override
      public void onNetConnected(NetworkType networkType) {
        LogUtils.i("网络已连接", "连接了连接了" + networkType.toString());
      }
    *
    * */


//──────────────────────────────────────────────────────────────────────────────

    /**
     * 线程池管理线程：ThreadManager
     * */
     //用法：ThreadManager.getInstance().execute(Runnable runnable)


//──────────────────────────────────────────────────────────────────────────────

    /**
     * Activitys工具类 ActivitysBuilder（就是之前项目的ActivityUtils）
     * */
    /*
    * 简单应用
    * ActivitysBuilder.build(MainActivity.this, SecondActivity.class)
                    .withAnimal(R.anim.anim_alpha_show, R.anim.anim_translate_hide)//withAnimal加上后，就是带上专场动画
                    .putExtra("haha", "我真的可以")//intent传值
                    .putExtra("wocao", 10)
                    .finish(true)//是否关闭本activity页面
                    .startActivity();
    *
    *
    * 如果需要startActivityForResult，直接点上就行了。里面有个requestCode;
    * 那么第二个页面只需要这样：
    * ActivitysBuilder.build(SecondActivity.this, MainActivity.class)
                    .putExtra("kk", "小朋友")
                    .withAnimal(0, R.anim.anim_translate_hide)
                    .setResult(9);
    * */


//──────────────────────────────────────────────────────────────────────────────


    /**
     * recyclerview：adapter刷新的时候，列表里的图片会闪烁
     * */

    //recyclerView：在adapter刷新的时候，item里面图片会闪。加上这句
    //((SimpleItemAnimator) binding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);


//──────────────────────────────────────────────────────────────────────────────


    /**
     * SmartRefreshLayout的用法
     * */
    /*
    <com.scwang.smartrefresh.layout.SmartRefreshLayout
    android:id="@+id/smartRefreshLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    </com.scwang.smartrefresh.layout.SmartRefreshLayout>
    * */


    /*
    自动加载
    app:srlEnableAutoLoadMore="false"

    不满一屏时，允许上拉加载，下拉刷新。
    app:srlEnableLoadMoreWhenContentNotFull="true"


    是否启动仿ios越界回弹
    app:srlEnableOverScrollDrag="true"
    如果启动了，那么就可以达成，头部只要回弹效果，底部是上拉加载
    注意：
        如果加上了上面的属性，比如头部只需要回弹的话，就用false
        app:srlEnableRefresh="false"
        低部只需要回弹的话，就用false
        app:srlEnableLoadMore="false"
    * */




}
