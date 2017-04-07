package xyz.egoistk21.aiya.love.matched;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedTodayFragment extends LazyFragment implements ILoveMatchedTodayView {

    private View rootView;
    private ImageView ivLeft, ivRight;
    private NestFullListView nflvTodayMission, nflvInviteMission;
    private List<LoveMission> loveMissions;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_love_matched_today, container, false);
        initData();
        initView();
        return rootView;
    }

    private void initView() {
        fm = getParentFragment().getFragmentManager();
        ft = fm.beginTransaction();
        rootView.findViewById(R.id.ll_intimacy).setOnClickListener(this);
        ivLeft = (ImageView) rootView.findViewById(R.id.iv_matched_left);
        ivRight = (ImageView) rootView.findViewById(R.id.iv_matched_right);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        nflvTodayMission = ((NestFullListView) rootView.findViewById(R.id.lv_love_matched_today_love_mission));
        nflvTodayMission.setAdapter(new NestFullListViewAdapter<LoveMission>(R.layout.listview_love_mission, loveMissions) {
            @Override
            public void onBind(int pos, LoveMission loveMission, NestFullViewHolder holder) {
                holder.setText(R.id.tv_listview_love_matched, "test");
                holder.setOnClickListener(R.id.ibn_listview_love_matched, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ft.addToBackStack(null);
                        ft.replace(R.id.container_love, new ClockInFragment()).commit();
                        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
                    }
                });
            }
        });
        nflvTodayMission.setOnItemLongClickListener(new NestFullListView.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(NestFullListView parent, View view, int position) {
                System.out.println(position);
            }
        });
        nflvInviteMission = ((NestFullListView) rootView.findViewById(R.id.lv_love_matched_today_invite_love_mission));
        nflvInviteMission.setAdapter(new NestFullListViewAdapter<LoveMission>(R.layout.listview_love_mission, loveMissions) {
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_intimacy:
                ft.addToBackStack(null);
                ft.replace(R.id.container_love, new IntimacyDetailFragment()).commit();
                break;
            case R.id.iv_matched_left:
                break;
            case R.id.iv_matched_right:
                ft.addToBackStack(null);
                ft.replace(R.id.container_love, new OtherFragment()).commit();
                getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        }
    }
}
