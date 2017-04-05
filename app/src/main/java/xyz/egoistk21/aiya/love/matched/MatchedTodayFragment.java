package xyz.egoistk21.aiya.love.matched;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.egoistk21.aiya.R;
import xyz.egoistk21.aiya.base.LazyFragment;
import xyz.egoistk21.aiya.base.NestFullListView;
import xyz.egoistk21.aiya.base.NestFullListViewAdapter;
import xyz.egoistk21.aiya.base.NestFullViewHolder;
import xyz.egoistk21.aiya.love.matched.bean.LoveMission;
import xyz.egoistk21.aiya.love.matched.view.ILoveMatchedTodayView;

/**
 * Created by EGOISTK on 2017/3/21.
 */

public class MatchedTodayFragment extends LazyFragment implements ILoveMatchedTodayView {

    private View rootView;
    private NestFullListView lvTodayMission, lvInviteMission;
    private List<LoveMission> loveMissions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_today, container, false);
        initData();
        initView();
        return rootView;
    }

    private void initView() {
        lvTodayMission =  ((NestFullListView) rootView.findViewById(R.id.lv_love_matched_today_love_mission));
        lvTodayMission.setAdapter(new NestFullListViewAdapter<LoveMission>(R.layout.listview_love_mission, loveMissions) {
            @Override
            public void onBind(int pos, LoveMission loveMission, NestFullViewHolder holder) {
                holder.setText(R.id.tv_listview_love_matched, "test");
            }
        });
        lvTodayMission.setOnItemLongClickListener(new NestFullListView.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(NestFullListView parent, View view, int position) {
                System.out.println(position);
            }
        });
        lvInviteMission = ((NestFullListView) rootView.findViewById(R.id.lv_love_matched_today_invite_love_mission));
        lvInviteMission.setAdapter(new NestFullListViewAdapter<LoveMission>(R.layout.listview_love_mission, loveMissions) {
            @Override
            public void onBind(int pos, LoveMission loveMission, NestFullViewHolder holder) {

            }
        });
    }

    private void initData() {
        loveMissions = new ArrayList<>();
        loveMissions.add(new LoveMission());
        loveMissions.add(new LoveMission());
        loveMissions.add(new LoveMission());
        loveMissions.add(new LoveMission());
    }
}
