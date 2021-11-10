package com.lihang.selfmvvm.ui.demo.funexplain.edittext;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.leo.utilspro.utils.KeyBoardUtils;
import com.leo.utilspro.utils.LogUtils;
import com.leo.utilspro.utils.PreferenceUtil;
import com.lihang.nbadapter.BaseAdapter;
import com.lihang.selfmvvm.R;
import com.lihang.selfmvvm.base.BaseActivity;
import com.lihang.selfmvvm.base.NormalViewModel;
import com.lihang.selfmvvm.customview.dialog.EdittextCommentDialog;
import com.lihang.selfmvvm.databinding.CommentActivityBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by leo
 * on 2021/11/10.
 */
public class CommentsActivity extends BaseActivity<NormalViewModel, CommentActivityBinding> {
    private CommentsAdapter adapter;
    private View itemView;//被点击的列表item
    //
    int keyBoard_Y;//软键盘的高度y
    int itemView_Y;//列表item的高度y
    //
    private EdittextCommentDialog edittextDialog;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    RelativeLayout relativeLayout = edittextDialog.getLayoutBottom();
                    int[] location = new int[2];
                    relativeLayout.getLocationOnScreen(location);
                    keyBoard_Y = (int) (location[1] - getContext().getResources().getDimension(R.dimen.dp_50));
                    PreferenceUtil.put("keyBoardHeight", keyBoard_Y);
                    LogUtils.i("这里没有数据吗",keyBoard_Y+"====板子的y");



                    if (itemView_Y > keyBoard_Y) {
                        binding.recyclerView.smoothScrollBy(0, itemView_Y - keyBoard_Y);
                    }

                    break;

                case 5:
                    if (binding.recyclerView != null) {
                        int dp_16 = (int) getResources().getDimension(R.dimen.dp_16);
                        binding.recyclerView.setPadding(0, 0, 0, dp_16);
                    }
                    break;
            }
        }
    };


    @Override
    protected int getContentViewId() {
        return R.layout.comment_activity;
    }

    @Override
    protected void processLogic() {
        adapter = new CommentsAdapter(this);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                itemView = binding.recyclerView.getLayoutManager().findViewByPosition(position);
                int[] location2 = new int[2];
                itemView.getLocationOnScreen(location2);
                itemView_Y = location2[1];
                LogUtils.i("这里没有数据吗",itemView_Y+"====item的y");

                binding.recyclerView.setPadding(0, 0, 0, 1000);
                openOneCommentKeyboard(2);
                mHandler.sendEmptyMessageDelayed(1, 500);
            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            arrayList.add("");
        }
        adapter.setDataList(arrayList);
        binding.recyclerView.setAdapter(adapter);

        initEditDialog();
    }


    public void openOneCommentKeyboard(int commentType) {
        //commentType是判断之前回复1级评论还是2级评论
        edittextDialog.show();
        edittextDialog.setType(commentType);
//        if (currentCommentBean != null) {
//            if (currentCommentBean instanceof OneCommentBean) {
//                OneCommentBean oneCommentBean = (OneCommentBean) currentCommentBean;
//                String nickName = oneCommentBean.getContentUserResp().getNickname();
//                edittextDialog.setHint("@" + nickName);
//            } else if (currentCommentBean instanceof TwoCommentBean) {
//                TwoCommentBean oneCommentBean = (TwoCommentBean) currentCommentBean;
//                String nickName = oneCommentBean.getContentUserResp().getNickname();
//                edittextDialog.setHint("@" + nickName);
//            }
//        }
        edittextDialog.setHint("@回复李航");
        Observable.timer(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
            edittextDialog.showKeyboard();
        });

    }


    private void initEditDialog() {
        edittextDialog = new EdittextCommentDialog(this, this);
        edittextDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (itemView_Y != 0 && itemView_Y > keyBoard_Y) {
                    binding.recyclerView.smoothScrollBy(0, keyBoard_Y - itemView_Y);
                    mHandler.sendEmptyMessageDelayed(5, 300);
                }
                KeyBoardUtils.closeKeybord(edittextDialog.getEdit_content());
                edittextDialog.getEdit_content().setText("");
                edittextDialog.getEdit_content().clearFocus();
                edittextDialog.setHint("我来补充两句~");
            }
        });
    }


    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
