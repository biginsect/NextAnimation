package com.lipeng.nextanimation.activity.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.AdapterViewAnimator;

import com.lipeng.nextanimation.R;
import com.lipeng.nextanimation.activity.adapter.FrameAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lipeng-ds3 on 2017/12/11.
 */

public class ObjectAnimatorActivity extends BaseActivity {

    @BindView(R.id.flipper_content)
    AdapterViewAnimator mContentFlipper;
    private boolean isAnimatingUp;
    private int mContentCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        ButterKnife.bind(this);

        isAnimatingUp = true;
        mContentCount = 20;
        mContentFlipper.setAdapter(new FrameAdapter(this, mContentCount));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showNext(){
        if (mContentCount > 1){
            setAnimations();
            mContentFlipper.showNext();
            isAnimatingUp = !isAnimatingUp;
            mContentCount--;
        }else {
            finish();
        }
    }

    private void setAnimations(){
        mContentFlipper.setInAnimation(this,
                isAnimatingUp? R.animator.slide_in_bottom :R.animator.slide_in_left);
        mContentFlipper.setOutAnimation(this,
                isAnimatingUp?R.animator.slide_out_top:R.animator.slide_out_right);
    }
}
