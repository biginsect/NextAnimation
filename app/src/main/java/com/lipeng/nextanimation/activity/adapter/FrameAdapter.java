package com.lipeng.nextanimation.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lipeng.nextanimation.activity.widget.ForegroundFrame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipeng-ds3 on 2017/12/11.
 */

public class FrameAdapter extends BaseAdapter {

    private final List<ForegroundFrame> mFrameViews;

    public FrameAdapter(Context context, int count){
        mFrameViews = new ArrayList<>();
        for (int i = 0; i < count; i++)
            mFrameViews.add(new ForegroundFrame(context));
    }

    @Override
    public int getCount() {
        return mFrameViews.size();
    }

    @Override
    public ForegroundFrame getItem(int position) {
        return mFrameViews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mFrameViews.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItem(position);
    }
}
