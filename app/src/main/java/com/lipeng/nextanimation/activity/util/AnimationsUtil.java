package com.lipeng.nextanimation.activity.util;

import android.content.Context;
import android.util.Property;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/**
 * Created by lipeng on 2017/12/7.
 * //动画工具类，不允许继承
 */

public class AnimationsUtil {

    private AnimationsUtil(){}

    private static Interpolator sInterpolator;

    public static Interpolator getInterpolator(Context context){//对于同一个页面使用同一个interpolator
        if (sInterpolator == null){
            synchronized (AnimationsUtil.class){
                if (sInterpolator == null)
                    sInterpolator = AnimationUtils.loadInterpolator(context,
                            android.R.interpolator.fast_out_slow_in);
            }
        }
        return sInterpolator;
    }

    public static abstract class FloatProperty<T> extends Property<T, Float>{

        public FloatProperty(String name){
            super(Float.class, name);
        }

        public abstract void setValue(T t, float value);

        @Override
        final public void set(T object, Float value) {
            setValue(object, value);
        }
    }

    public static abstract class IntProperty<T> extends Property<T, Integer>{
        public IntProperty(String name){
            super(Integer.class, name);
        }

        public abstract void setValue(T t, int value);

        @Override
        final public void set(T object, Integer value) {
            setValue(object, value);
        }
    }


}
