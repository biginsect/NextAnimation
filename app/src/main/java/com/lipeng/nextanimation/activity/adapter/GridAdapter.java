package com.lipeng.nextanimation.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lipeng.nextanimation.R;

/**
 * Created by lipeng-ds3 on 2017/12/11.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder>{
    private String[] mGridItemArray;
    private OnRecyclerItemListener mItemListener;

    public GridAdapter(String[] gridItemArray, OnRecyclerItemListener mItemListener){
        mGridItemArray = gridItemArray;
        this.mItemListener = mItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, parent, false);
        return new ViewHolder((TextView) view.findViewById(R.id.text_name));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mGridItemArray[position]);
    }

    @Override
    public int getItemCount() {
        return mGridItemArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView;
        public ViewHolder(TextView view){
            super(view);
            mTextView = view;
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemListener != null){
                mItemListener.onItemClick(mTextView);
            }
        }
    }

    public interface OnRecyclerItemListener{
        void onItemClick(View view);
    }
}
