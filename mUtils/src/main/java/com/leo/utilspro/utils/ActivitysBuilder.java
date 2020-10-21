package com.leo.utilspro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2020/10/14.
 */
public class ActivitysBuilder {
    private Context context;
    private Class<? extends Activity> clz;
    private Intent intent;
    private int enterAnim = -10000;
    private int exitAnim = -10000;
    private boolean finish;

    public ActivitysBuilder(Context context, Class<? extends Activity> clz) {
        this.context = context;
        this.clz = clz;
        intent = new Intent(context, clz);
    }

    public static ActivitysBuilder build(Context context, Class<? extends Activity> clz) {
        return new ActivitysBuilder(context, clz);
    }

    public static void finishWithAnim(Activity activity, int enterAnim, int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }


    public static void startHome(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(home);
    }


    //enterAnim：activity进场动画，0表示没有动画
    //exitAnim：activity出场动画，0表示没有动画
    public ActivitysBuilder withAnimal(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    public ActivitysBuilder putExtra(String name, boolean value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, byte value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, char value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, short value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, int value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, long value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, float value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, double value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, String value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, CharSequence value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, Parcelable value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, Parcelable[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        intent.putParcelableArrayListExtra(name, value);
        return this;
    }

    public ActivitysBuilder putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        intent.putIntegerArrayListExtra(name, value);
        return this;
    }

    public ActivitysBuilder putStringArrayListExtra(String name, ArrayList<String> value) {
        intent.putStringArrayListExtra(name, value);
        return this;
    }

    public ActivitysBuilder putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        intent.putCharSequenceArrayListExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, Serializable value) {
        intent.putExtra(name, value);
        return this;
    }


    public ActivitysBuilder putExtra(String name, boolean[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, byte[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, short[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, char[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, int[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, long[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, float[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, double[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, String[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, CharSequence[] value) {
        intent.putExtra(name, value);
        return this;
    }

    public ActivitysBuilder putExtra(String name, Bundle value) {
        intent.putExtra(name, value);
        return this;
    }

    //是否关闭当前页面
    public ActivitysBuilder finish(boolean finish) {
        this.finish = finish;
        return this;
    }

    public void startActivity() {
        context.startActivity(intent);
        if (finish) {
            ((Activity) context).finish();
        }
        if (enterAnim != -10000 && exitAnim != -10000) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public void startActivityForResult(int requestCode) {
        ((Activity) context).startActivityForResult(intent, requestCode);

        if (finish) {
            ((Activity) context).finish();
        }
        if (enterAnim != -10000 && exitAnim != -10000) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public void setResult(int resultCode) {
        ((Activity) context).setResult(resultCode, intent);
        ((Activity) context).finish();
        if (enterAnim != -10000 && exitAnim != -10000) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }


}
