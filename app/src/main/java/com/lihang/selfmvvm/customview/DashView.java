package com.lihang.selfmvvm.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.leo.utilspro.utils.LogUtils;
import com.lihang.selfmvvm.R;

import androidx.annotation.Nullable;

/**
 * Created by leo
 * on 2021/11/15.
 */
public class DashView extends View {
    private boolean isVertical;//是否是竖直的
    private int solidWidth;//实线长度
    private int divideWidth;//间隔长度
    private int dashColor;//虚线颜色
    private int mWidth;//宽度
    private int mHeight;//高度

    //画虚线的笔
    private Paint mPaintDash;
    private Path dashPath;

    public DashView(Context context) {
        this(context, null);
    }

    public DashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashView);
        isVertical = typedArray.getBoolean(R.styleable.DashView_dash_isVertical, false);
        solidWidth = (int) typedArray.getDimension(R.styleable.DashView_dash_solidWidth, getResources().getDimension(R.dimen.dp_5));
        divideWidth = (int) typedArray.getDimension(R.styleable.DashView_dash_divideWidth, getResources().getDimension(R.dimen.dp_5));
        dashColor = (int) typedArray.getColor(R.styleable.DashView_dash_dashColor, Color.parseColor("#cccccc"));


        //初始化 话虚线的笔
        mPaintDash = new Paint();
        mPaintDash.setAntiAlias(true);
        mPaintDash.setColor(dashColor);
        mPaintDash.setStyle(Paint.Style.STROKE);
//        mPaintDash.setStrokeWidth(dashWidth);
        mPaintDash.setPathEffect(new DashPathEffect(new float[]{solidWidth, divideWidth}, 0));
        //
        dashPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mWidth == 0) {
            mWidth = getWidth();
        }

        if (mHeight == 0) {
            mHeight = getHeight();
        }

        if (isVertical) {
            //如果是竖直的，就把宽度设置给画笔
            mPaintDash.setStrokeWidth(mWidth);
            dashPath.reset();
            dashPath.moveTo(mWidth / 2, 0);
            dashPath.lineTo(mWidth / 2, mHeight);
        } else {
            mPaintDash.setStrokeWidth(mHeight);
            dashPath.reset();
            dashPath.moveTo(0, mHeight / 2);
            dashPath.lineTo(mWidth, mHeight / 2);
        }
        canvas.drawPath(dashPath, mPaintDash);

    }
}
