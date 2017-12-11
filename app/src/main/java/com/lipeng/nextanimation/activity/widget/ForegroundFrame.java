package com.lipeng.nextanimation.activity.widget;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.widget.FrameLayout;

import com.lipeng.nextanimation.R;
import com.lipeng.nextanimation.activity.ui.ObjectAnimatorActivity;

/**
 * Created by lipeng on 2017/12/8.
 */

public class ForegroundFrame extends FrameLayout{

    private static final int DELAY_COLOR_CHANGE = 750;

    public ForegroundFrame(Context context){
        super(context);
        addContentView(this);
    }

    public ForegroundFrame(Context context, AttributeSet attrs){
        super(context, attrs);
        addContentView(this);
    }

    public ForegroundFrame(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        addContentView(this);
    }

    public ForegroundFrame(Context context, AttributeSet attrs,
                           int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        addContentView(this);
    }

    private void doStuff(){
        resizeView();
        moveViewOffScreen();
        final int backgroundColor = R.color.primary;
        animatedFeregroundColor(ContextCompat.getColor(getContext(), backgroundColor));
    }

    private void addContentView(FrameLayout container){
        inflate(getContext(), R.layout.layout_frame, this);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_quiz);
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPropertyAnimatorCompat animatorCompat = buildAnimation(v);
                animatorCompat.setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(View view) {

                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        doStuff();
                    }

                    @Override
                    public void onAnimationCancel(View view) {

                    }
                });
            }
        });
    }

    private ViewPropertyAnimatorCompat buildAnimation(View view){
        ViewPropertyAnimatorCompat animatorCompat = ViewCompat.animate(view);
        animatorCompat.alpha(0f)
                .scaleX(0f)
                .scaleY(0f);
        return animatorCompat;
    }

    private final Property<ForegroundFrame, Integer> FOREGROUND_COLOR =
            new IntProperty<ForegroundFrame>("foregroundColor") {
                @SuppressLint("NewApi")
                @Override
                public void setValue(ForegroundFrame object, int value) {
                    if (object.getForeground() instanceof ColorDrawable){
                        ((ColorDrawable)object.getForeground().mutate()).setColor(value);
                    }else {
                        object.setForeground(new ColorDrawable(value));
                    }
                }

                @SuppressLint("NewApi")
                @Override
                public Integer get(ForegroundFrame object) {
                    if (object.getForeground() instanceof ColorDrawable){
                        return ((ColorDrawable) object.getForeground()).getColor();
                    }else {
                        return Color.TRANSPARENT;
                    }
                }
            };

    private void resizeView(){
        final float widthHeightRatio = (float)getHeight() / (float) getWidth();

        resizeViewProperty(View.SCALE_X, .5f, 200);
        resizeViewProperty(View.SCALE_Y,
                .5f / widthHeightRatio, 250);
    }

    private void resizeViewProperty(Property<View, Float> property,
                                    float targetScale, int durationOffset){
        ObjectAnimator animator = ObjectAnimator.ofFloat(this,
                property, 1f, targetScale);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.setStartDelay(DELAY_COLOR_CHANGE + durationOffset);
        animator.start();
    }

    private void animatedFeregroundColor(@ColorInt final int targetColor){
        ObjectAnimator animator = ObjectAnimator.ofInt(this, FOREGROUND_COLOR,
                Color.TRANSPARENT, targetColor);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setStartDelay(DELAY_COLOR_CHANGE);
        animator.start();
    }

    private void moveViewOffScreen(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (getContext() instanceof ObjectAnimatorActivity)
                    ((ObjectAnimatorActivity) getContext()).showNext();
            }
        };

        new Handler().postDelayed(runnable, DELAY_COLOR_CHANGE * 2);
    }

    public abstract class IntProperty<T> extends Property<T, Integer>{
        public IntProperty(String name){
            super(Integer.class, name);
        }

        public abstract void setValue(T object, int value);

        @Override
        public void set(T object, Integer value) {
            setValue(object, value);
        }
    }

}
