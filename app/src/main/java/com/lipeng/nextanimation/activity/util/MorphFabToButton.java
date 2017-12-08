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
 * Created by lipeng on 2017/12/8.
 */

public class MorphFabToButton  extends ChangeBounds{

    private static final String PROPERTY_COLOR = "property_color";
    private static final String PROPERTY_CORNER_RADIUS = "property_corner_radius";
    private static final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR,
            PROPERTY_CORNER_RADIUS
    };
    private @ColorInt int startColor = Color.TRANSPARENT;
    private int endCornerRadius;

    public MorphFabToButton(@ColorInt int startColor, int endCornerRadius){
        super();
        setStartColor(startColor);
        setEndCornerRadius(endCornerRadius);
    }

    public MorphFabToButton(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndCornerRadius(int endCornerRadius) {
        this.endCornerRadius = endCornerRadius;
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
        transitionValues.values.put(PROPERTY_CORNER_RADIUS, view.getHeight() / 2);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0)
            return;

        transitionValues.values.put(PROPERTY_COLOR,
                ContextCompat.getColor(view.getContext(), R.color.super_light_grey));
        transitionValues.values.put(PROPERTY_CORNER_RADIUS, endCornerRadius);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues,
                                   TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (changeBounds == null || startValues == null || endValues == null){
            return null;
        }

        Integer startColor = (Integer) startValues.values.get(PROPERTY_COLOR);
        Integer endColor = (Integer) endValues.values.get(PROPERTY_COLOR);
        Integer startCornerRadius = (Integer) startValues.values.get(PROPERTY_CORNER_RADIUS);
        Integer endCornerRadius = (Integer) endValues.values.get(PROPERTY_CORNER_RADIUS);

        if (startColor == null || endColor == null ||
                startCornerRadius == null || endCornerRadius == null){
            return null;
        }

        MorphDrawable background = new MorphDrawable(startColor, startCornerRadius);
        endValues.view.setBackground(background);

        Animator color = ObjectAnimator.ofArgb(background, MorphDrawable.COLOR, endColor);
        Animator corner = ObjectAnimator.ofFloat(background, MorphDrawable.CORNER_RADIUS,
                endCornerRadius);

        if (endValues.view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) endValues.view;
            float offset = viewGroup.getHeight() / 3;
            int childCount = viewGroup.getChildCount();
            for (int i = 0 ; i < childCount; i++){
                View view = viewGroup.getChildAt(i);
                view.setTranslationY(offset);
                view.setAlpha(0f);
                view.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(150)
                        .setStartDelay(150)
                        .setInterpolator(AnimationUtils.loadInterpolator(viewGroup.getContext(),
                                android.R.interpolator.fast_out_slow_in));
                offset *= 1.8f;
            }
        }

        AnimatorSet transitions = new AnimatorSet();
        transitions.playTogether(changeBounds, corner, color);
        transitions.setDuration(300);
        transitions.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                android.R.interpolator.fast_out_slow_in));

        return transitions;
    }
}
