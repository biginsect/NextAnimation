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

public class AnimationApisAdapter  extends RecyclerView.Adapter<AnimationApisAdapter.ViewHolder>{
    private String[] mApiArray;
    private OnRecyclerItemListener mItemListener;

    public AnimationApisAdapter(String[] apiArray, OnRecyclerItemListener mItemListener){
        mApiArray = apiArray;
        this.mItemListener = mItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_api, parent, false);
        return new ViewHolder((TextView) view.findViewById(R.id.text_name));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mApiNameTextView.setText(mApiArray[position]);
    }

    @Override
    public int getItemCount() {
        return mApiArray.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mApiNameTextView;

        public ViewHolder(TextView view){
            super(view);
            mApiNameTextView = view;
            mApiNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemListener != null){
                int itemPosition = getAdapterPosition();
                mItemListener.onItemClick(itemPosition);
            }
        }
    }

    public interface OnRecyclerItemListener{
        void onItemClick(int position);
    }
}
