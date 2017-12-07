package com.lipeng.nextanimation.activity.util;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Property;

/**
 * Created by lipeng on 2017/12/7.
 * 封装{@link Drawable}，自行实现颜色与半径的具体变化
 */

public class MorphDrawable extends Drawable {
    private float cornerRadius;
    private Paint mPaint;
    public static final Property<MorphDrawable, Float> CORNER_RADIUS =
            new AnimationsUtil.FloatProperty<MorphDrawable>("cornerRadius") {
        @Override
        public void setValue(MorphDrawable morphDrawable, float value) {
            morphDrawable.setCornerRadius(value);
        }

        @Override
        public Float get(MorphDrawable object) {
            return object.getCornerRadius();
        }
    };

    public static final Property<MorphDrawable, Integer> COLOR =
            new AnimationsUtil.IntProperty<MorphDrawable>("color") {
                @Override
                public void setValue(MorphDrawable morphDrawable, int value) {
                    morphDrawable.setColor(value);
                }

                @Override
                public Integer get(MorphDrawable object) {
                    return object.getColor();
                }
            };

    public MorphDrawable(@ColorInt int color, float cornerRadius){
        this.cornerRadius = cornerRadius;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(getBounds().left, getBounds().top,
                getBounds().right, getBounds().bottom,
                cornerRadius, cornerRadius, mPaint);
    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        outline.setRoundRect(getBounds(), cornerRadius);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return mPaint.getAlpha();
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidateSelf();
    }

    public int getColor(){
        return mPaint.getColor();
    }

    public void setColor(int color){
        mPaint.setColor(color);
        invalidateSelf();
    }

}
