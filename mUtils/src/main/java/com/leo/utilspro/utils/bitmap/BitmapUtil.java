package com.leo.utilspro.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.leo.utilspro.utils.TimeUtils;
import com.leo.utilspro.utils.abase.LeoUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by siberiawolf on 17/3/22.
 */

public class BitmapUtil {
    /**
     * 按图片的质量压缩
     *
     * @param filePath 需要压缩的图片路径
     * @param quality  压缩的质量 0 - 100
     * @return
     */

    public static String compressBitmapByQuality(String filePath, int quality) {
        Bitmap bm = BitmapFactory.decodeFile(filePath);
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File imageFile = new File(LeoUtils.getApplication().getFilesDir().getAbsolutePath().toString() + "/" + TimeUtils.getStrByLong(System.currentTimeMillis(), "yyyy.MM.dd_HH:mm:ss:SSS") + "_atmancarm.jpg");
        try {
            imageFile.createNewFile();
            FileOutputStream out = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {

        }

        String imgpath = imageFile.getPath();

        return imgpath;
    }

    /**
     * 按图片长宽压缩
     * 按传入的长宽后，计算出合适的压缩比如，压缩图片，此处指的是图片长宽大小的压缩
     */
    public static Bitmap compressBitmapBySize(String filePath, int requireWidth, int requireHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置整个属性后，不会加载bitmap，只会获取图片属性，比如宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比()
        options.inSampleSize = calculateInSampleSize(options, requireWidth, requireHeight);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //与原图片比，长宽压缩多少倍数。 multiple  几倍数
    public static Bitmap compressBitmapBySourceSize(String filePath, double multiple) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置整个属性后，不会加载bitmap，只会获取图片属性，比如宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        int sourceHeight = options.outHeight;
        int sourceWidth = options.outWidth;
        int requireWidth = (int) (sourceWidth / multiple);
        int requireHeight = (int) (sourceHeight / multiple);

        // 计算缩放比()
        options.inSampleSize = calculateInSampleSize(options, requireWidth, requireHeight);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    /**
     * 获取照片角度
     * 一般是拍摄照片的时候才会用到
     *
     * @param path
     * @return
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     * 一般是拍摄照片的时候才会用到
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }


    // 计算缩放比()
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    //压缩图片到 指定大小
    public static Bitmap compressTargetBitmap(Bitmap bitmap) {
        double maxSize = 500.00;//KB
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap = zoomBitmap(bitmap, bitmap.getWidth() / Math.sqrt(i),
                    bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }


    public static Bitmap zoomBitmap(Bitmap bgimage, double newWidth,
                                    double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


}
