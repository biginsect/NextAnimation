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
 * 矩形按钮转变为圆按钮的动画，有颜色的变化
 */

public class MorphButtonToFab extends ChangeBounds {

    private static final String PROPERTY_COLOR = "plaid:rectMorph:color";
    private static final String PROPERTY_CORNER_RADIUS = "plaid:rectMorph:cornerRadius";
    private static final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR,
            PROPERTY_CORNER_RADIUS
    };
    private @ColorInt int endColor = Color.TRANSPARENT;

    public MorphButtonToFab(@ColorInt int endColor){
        super();
        setEndColor(endColor);
    }

    public MorphButtonToFab(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    @Override
    public  String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0)
            return;
        transitionValues.values.put(PROPERTY_COLOR,
                ContextCompat.getColor(view.getContext(), R.color.super_light_grey));
        transitionValues.values.put(PROPERTY_CORNER_RADIUS,
                view.getResources().getDimensionPixelSize(R.dimen.dialog_corners));
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0)
            return;
        transitionValues.values.put(PROPERTY_COLOR, endColor);
        transitionValues.values.put(PROPERTY_CORNER_RADIUS, view.getHeight() / 2);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues,
                                   TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (changeBounds == null || startValues == null || endValues == null)
            return null;

        Integer startColor = (Integer) startValues.values.get(PROPERTY_COLOR);
        Integer startCornerRadius = (Integer) startValues.values.get(PROPERTY_CORNER_RADIUS);
        Integer endColor = (Integer) endValues.values.get(PROPERTY_COLOR);
        Integer endCornerRadius = (Integer) endValues.values.get(PROPERTY_CORNER_RADIUS);

        if (startColor == null || startCornerRadius == null ||
                endColor == null || endCornerRadius == null){
            return null;
        }

        MorphDrawable background = new MorphDrawable(startColor, startCornerRadius);
        endValues.view.setBackground(background);

        Animator color = ObjectAnimator.ofArgb(background, MorphDrawable.COLOR, endColor);
        Animator corners = ObjectAnimator.ofFloat(background,
                MorphDrawable.CORNER_RADIUS, endCornerRadius);

        //hide child views
        if (endValues.view instanceof  ViewGroup){
            ViewGroup viewGroup = (ViewGroup) endValues.view;
            for (int i = 0; i < viewGroup.getChildCount(); i++){
                View view = viewGroup.getChildAt(i);
                view.animate()
                        .alpha(0f)
                        .translationY(view.getHeight() / 3)
                        .setStartDelay(0L)
                        .setDuration(50L)
                        .setInterpolator(AnimationUtils.loadInterpolator(viewGroup.getContext(),
                                android.R.interpolator.fast_out_linear_in))
                        .start();
            }
        }

        AnimatorSet transitions = new AnimatorSet();
        transitions.playTogether(changeBounds, corners, color);
        transitions.setDuration(300);
        transitions.setInterpolator(AnimationsUtil.getInterpolator(sceneRoot.getContext()));

        return transitions;
    }
}
