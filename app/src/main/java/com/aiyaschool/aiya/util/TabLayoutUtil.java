package com.aiyaschool.aiya.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;

import com.aiyaschool.aiya.R;

/**
 * 为TabLayout设置竖直divider
 * Created by EGOISTK21 on 2017/4/20.
 */

public class TabLayoutUtil {

    private TabLayoutUtil() {
    }

    public static void initIndicator(Context context, TabLayout tabLayout) {
        View root = tabLayout.getChildAt(0);
        root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPureWhite));
        GradientDrawable divider = new GradientDrawable();
        divider.setSize(1, 1);
        divider.setColor(ContextCompat.getColor(context, R.color.colorDivider));
        ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        ((LinearLayout) root).setDividerDrawable(divider);
        ((LinearLayout) root).setDividerPadding(16);
    }
}
