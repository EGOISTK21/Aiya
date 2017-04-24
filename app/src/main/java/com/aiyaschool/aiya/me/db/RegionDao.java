package com.aiyaschool.aiya.me.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aiyaschool.aiya.me.bean.RegionModel;

import java.util.List;

/**
 * Created by wewarriors on 2017/4/24.
 */

public class RegionDao {

    private Context mContext;
    private SQLiteDatabase db;

    public RegionDao(Context context){
        this.mContext = context;
        this.db = RegionDbHelper.getInstance(context).getReadableDatabase();
    }

    /**
     * 加载省份
     * @return
     */
    private List<RegionModel> loadProvinceList(){
        return null;
    }
}
