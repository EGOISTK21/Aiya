package xyz.egoistk.aiya.love.matched.view.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xyz.egoistk.aiya.R;
import xyz.egoistk.aiya.love.matched.adpter.LoveMissionAdpter;
import xyz.egoistk.aiya.base.LazyFragment;
import xyz.egoistk.aiya.love.matched.LoveMission;
import xyz.egoistk.aiya.love.matched.view.i.ILoveMatchedTodayView;

/**
 * Created by EGOISTK on 2017/3/21.
 */

public class LoveMatchedTodayFragment extends LazyFragment implements ILoveMatchedTodayView {

    private View rootView;
    private List<LoveMission> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_today, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new LoveMission());
        }
        ListView listView = (ListView) rootView.findViewById(R.id.lv_love_matched_today_today_love_mission);
        LoveMissionAdpter adpter = new LoveMissionAdpter(getContext(), R.layout.listview_love_mission, list);
        listView.setAdapter(adpter);
    }
}
