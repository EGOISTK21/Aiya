package com.aiyaschool.aiya.message.interfaces;

import android.view.View;

/**
 * Created by XZY on 2017/2/26.
 */

public interface OnItemClickListener {

    void OnItemClick(int position, View view);

    boolean  OnItemLongClick(int position, View view);
}