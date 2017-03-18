package com.egoistk.aiya.bottomNav;

import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**利用反射机制，改变 item 的 mShiftingMode 变量
 * Created by EGOISTK on 2017/3/14.
 */

public class BottomNavigationViewHelper {
    @SuppressWarnings("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {
        BottomNavigationMenuView navigationMenuView =
                (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = navigationMenuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(navigationMenuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < navigationMenuView.getChildCount(); i++) {
                BottomNavigationItemView itemView =
                        (BottomNavigationItemView) navigationMenuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Log
        }
    }
}
