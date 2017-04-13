package com.aiyaschool.aiya.util;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * 利用反射机制，改变 item 的 mShiftingMode 变量
 * Created by EGOISTK21 on 2017/3/14.
 */

public class BottomNavigationViewUtil {
    @SuppressWarnings("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView bottomNavigationView) {
        BottomNavigationMenuView menuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < menuView.getChildCount(); i++) {
            BottomNavigationItemView itemView =
                    (BottomNavigationItemView) menuView.getChildAt(i);
            itemView.setShiftingMode(false);
            itemView.setChecked(itemView.getItemData().isChecked());
        }
    }
}
