package xyz.egoistk21.aiya.love.matched.adpter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.love.matched.bean.LoveMission;


/**
 * Created by EGOISTK21 on 2017/3/22.
 */

public class LoveMissionAdpter extends ArrayAdapter<LoveMission> {

    private int resourceId;

    public LoveMissionAdpter(@NonNull Context context, @LayoutRes int resource, List<LoveMission> loveMissions) {
        super(context, resource, loveMissions);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LoveMission loveMission = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView tvListviewLoveMatched = (TextView) view.findViewById(R.id.tv_listview_love_matched);
        ImageButton ibtnListviewLoveMatched = (ImageButton) view.findViewById(R.id.ibn_listview_love_matched);
        return view;
    }
}
