package com.aiyaschool.aiya.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.me.util.DBCopyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/5/14.
 */

public class SchoolDBHelper extends SQLiteOpenHelper {

    public static final List<String> PROVINCE = new ArrayList<>(Arrays.asList("北京市", "天津市",
            "河北省", "山西省", "内蒙古自治区", "辽宁省", "吉林省", "黑龙江省", "上海市", "江苏省", "浙江省",
            "安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "广西壮族自治区",
            "海南省", "重庆市", "四川省", "贵州省", "云南省", "西藏自治区", "陕西省", "甘肃省", "青海省",
            "宁夏回族自治区", "新疆维吾尔自治区"));
    private static SQLiteDatabase sDatabase;
    private static final String DB_NAME = "edu.db";
    private static final int DB_VERSION = 1;

    public static SQLiteDatabase getDBInstance() {
        if (sDatabase == null) {
            synchronized (SchoolDBHelper.class) {
                sDatabase = new SchoolDBHelper(MyApplication.getInstance()).getReadableDatabase();
            }
        }
        return sDatabase;
    }

    public static void closeDB() {
        if (sDatabase != null) {
            sDatabase.close();
            sDatabase = null;
        }
    }

    public SchoolDBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);
    }

    public SchoolDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static List<String> loadSchoolList(String province) {
        List<String> schoolList = new ArrayList<>();
        String sql = "SELECT school WHERE province = " + province + "FROM edu";
        Cursor cursor = sDatabase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            schoolList.add(cursor.getString(2));
        }
        return schoolList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
