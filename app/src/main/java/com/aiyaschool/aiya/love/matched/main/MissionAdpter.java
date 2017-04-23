package com.aiyaschool.aiya.love.matched.main;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;

import java.util.List;


/**
 * Created by EGOISTK21 on 2017/3/22.
 */

public class MissionAdpter extends ArrayAdapter<Mission> {

    private int resourceId;

    public MissionAdpter(@NonNull Context context, @LayoutRes int resource, List<Mission> missions) {
        super(context, resource, missions);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Mission mission = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView tvListviewLoveMatched = (TextView) view.findViewById(R.id.tv_listview_love_matched);
        ImageView ibtnListviewLoveMatched = (ImageView) view.findViewById(R.id.ibn_listview_love_matched);
        return view;
    }
}
