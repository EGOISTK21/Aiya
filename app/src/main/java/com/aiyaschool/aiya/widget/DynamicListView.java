package com.aiyaschool.aiya.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 能够动态测量item高度并完全显示所有item的ListView，适用于高度统一的item
 * Created by EGOISTK21 on 2017/5/26.
 */

public class DynamicListView extends ListView {

    public DynamicListView(Context context) {
        super(context);
    }

    public DynamicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
