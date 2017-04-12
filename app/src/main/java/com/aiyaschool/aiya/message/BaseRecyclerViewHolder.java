package com.aiyaschool.aiya.message;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by XZY on 2017/2/26.
 */

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    protected View itemView;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }
}
