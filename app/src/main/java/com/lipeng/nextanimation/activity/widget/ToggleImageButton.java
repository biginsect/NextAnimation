package com.lipeng.nextanimation.activity.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageView;

/**
 * Created by lipeng-ds3 on 2017/12/11.
 */

public class ToggleImageButton extends ImageView implements Checkable {
    private static final int[] CheckedStateSet = {android.R.attr.state_checked};
    private boolean mChecked = false;

    public ToggleImageButton(Context context){
        super(context);
    }

    public ToggleImageButton(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (checked != mChecked){
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked())
            mergeDrawableStates(drawableState, CheckedStateSet);

        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}
