package com.aiyaschool.aiya.me.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *地区数据库
 * Created by wewarriors on 2017/4/24.
 */

public class RegionDbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "region.db";
    private static final int DB_VERSION = 1;

    private static RegionDbHelper helper;

    public static RegionDbHelper getInstance(Context context){
        if(helper == null){
            synchronized (RegionDbHelper.class){
                if(helper == null){
                    helper = new RegionDbHelper(context.getApplicationContext());
                }
            }
        }
        return helper;
    }

    /**
     * 关闭数据库，用在退出登录时。
     */
    public static void closeDb(){
        if(helper != null){
            helper.close();
        }
        helper = null;
    }

    public RegionDbHelper(Context context){
        this(context,DB_NAME,null,DB_VERSION);
    }

    public RegionDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
