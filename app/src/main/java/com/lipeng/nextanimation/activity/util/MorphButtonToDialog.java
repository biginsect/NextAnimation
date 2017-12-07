package com.lipeng.nextanimation.activity.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.lipeng.nextanimation.R;

/**
 * Created by lipeng on 2017/12/7.
 * 封装ChangeBounds，由button向dialog转变的动画展示
 */

public class MorphButtonToDialog extends ChangeBounds {
    private static final String PROPERTY_COLOR = "property_color";
    private static final String[] TRANSITION_PROPERTIES = {PROPERTY_COLOR};
    private @ColorInt int startColor = Color.TRANSPARENT;

    public MorphButtonToDialog(@ColorInt int startColor){
        super();
        setStartColor(startColor);
    }

    public MorphButtonToDialog(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setStartColor(@ColorInt int startColor) {
        this.startColor = startColor;
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0)
            return;
        transitionValues.values.put(PROPERTY_COLOR, startColor);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0)
            return;
        transitionValues.values.put(PROPERTY_COLOR,
                ContextCompat.getColor(view.getContext(), R.color.super_light_grey));
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues,
                                   TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues ==null || changeBounds == null)
            return null;
        Integer startColor = (Integer) startValues.values.get(PROPERTY_COLOR);
        Integer endColor = (Integer) endValues.values.get(PROPERTY_COLOR);

        if (startColor == null || endColor == null)
            return null;

        MorphDrawable background = new MorphDrawable(startColor, 0);
        endValues.view.setBackground(background);

        Animator color = ObjectAnimator.ofArgb(background, MorphDrawable.COLOR, endColor);

        //淡入淡出，滑动效果
        if (endValues.view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) endValues.view;
            float offset = viewGroup.getHeight() / 3;
            for (int i = 0; i < viewGroup.getChildCount(); i++){
                View view = viewGroup.getChildAt(i);
                view.setTranslationY(offset);
                view.setAlpha(0f);
                view.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(150)
                        .setStartDelay(150)
                        .setInterpolator(AnimationUtils.loadInterpolator(
                                viewGroup.getContext(), android.R.interpolator.fast_out_slow_in));
                offset *= 1.8f;
            }
        }

        AnimatorSet transitions = new AnimatorSet();
        //变形和颜色渐变
        transitions.playTogether(changeBounds, color);
        transitions.setDuration(300);
        transitions.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                android.R.interpolator.fast_out_slow_in));

        return transitions;
    }
}
