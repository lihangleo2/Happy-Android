package com.lihang.selfmvvm.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * Created by leo
 * on 2019/12/2.
 *
 * 这个是glid只加载2个圆角
 */
//            用法
//            GlideRoundTransform transform = new GlideRoundTransform(binding.image.getContext(), DensityUtils.dp2px(5));
//            transform.setNeedCorner(true, true, false, false);
//            Glide.with(binding.image).
//                    load(itemBean.getProductImage().get(0)).
//                    skipMemoryCache(true).
//                    placeholder(R.mipmap.place_hold).
//                    error(R.mipmap.place_hold).
//                    diskCacheStrategy(DiskCacheStrategy.NONE).
//                    transform(transform).
//                    into(binding.image);
public class GlideRoundTransform implements Transformation<Bitmap> {
    private BitmapPool mBitmapPool;

    private float radius;

    private boolean isLeftTop, isRightTop, isLeftBottom, isRightBotoom;

    /**
     * 需要设置圆角的部分
     *
     * @param leftTop     左上角
     * @param rightTop    右上角
     * @param leftBottom  左下角
     * @param rightBottom 右下角
     */
    public void setNeedCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        isLeftTop = leftTop;
        isRightTop = rightTop;
        isLeftBottom = leftBottom;
        isRightBotoom = rightBottom;
    }

    /**
     * @param context 上下文
     * @param radius  圆角幅度
     */
    public GlideRoundTransform(Context context, float radius) {
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.radius = radius;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {

        Bitmap source = resource.get();
        int finalWidth, finalHeight;
        //输出目标的宽高或高宽比例
        float scale;
        if (outWidth > outHeight) {
            //如果 输出宽度 > 输出高度 求高宽比

            scale = (float) outHeight / (float) outWidth;
            finalWidth = source.getWidth();
            //固定原图宽度,求最终高度
            finalHeight = (int) ((float) source.getWidth() * scale);
            if (finalHeight > source.getHeight()) {
                //如果 求出的最终高度 > 原图高度 求宽高比

                scale = (float) outWidth / (float) outHeight;
                finalHeight = source.getHeight();
                //固定原图高度,求最终宽度
                finalWidth = (int) ((float) source.getHeight() * scale);
            }
        } else if (outWidth < outHeight) {
            //如果 输出宽度 < 输出高度 求宽高比

            scale = (float) outWidth / (float) outHeight;
            finalHeight = source.getHeight();
            //固定原图高度,求最终宽度
            finalWidth = (int) ((float) source.getHeight() * scale);
            if (finalWidth > source.getWidth()) {
                //如果 求出的最终宽度 > 原图宽度 求高宽比

                scale = (float) outHeight / (float) outWidth;
                finalWidth = source.getWidth();
                finalHeight = (int) ((float) source.getWidth() * scale);
            }
        } else {
            //如果 输出宽度=输出高度
            finalHeight = source.getHeight();
            finalWidth = finalHeight;
        }

        //修正圆角
        this.radius *= (float) finalHeight / (float) outHeight;
        Bitmap outBitmap = this.mBitmapPool.get(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        if (outBitmap == null) {
            outBitmap = Bitmap.createBitmap(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        //关联画笔绘制的原图bitmap
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //计算中心位置,进行偏移
        int width = (source.getWidth() - finalWidth) / 2;
        int height = (source.getHeight() - finalHeight) / 2;
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate((float) (-width), (float) (-height));
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0.0F, 0.0F, (float) canvas.getWidth(), (float) canvas.getHeight());
        //先绘制圆角矩形
        canvas.drawRoundRect(rectF, this.radius, this.radius, paint);

        //左上角圆角
        if (!isLeftTop) {
            canvas.drawRect(0, 0, radius, radius, paint);
        }
        //右上角圆角
        if (!isRightTop) {
            canvas.drawRect(canvas.getWidth() - radius, 0, canvas.getWidth(), radius, paint);
        }
        //左下角圆角
        if (!isLeftBottom) {
            canvas.drawRect(0, canvas.getHeight() - radius, radius, canvas.getHeight(), paint);
        }
        //右下角圆角
        if (!isRightBotoom) {
            canvas.drawRect(canvas.getWidth() - radius, canvas.getHeight() - radius, canvas.getWidth(), canvas.getHeight(), paint);
        }

        return BitmapResource.obtain(outBitmap, this.mBitmapPool);
    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}

