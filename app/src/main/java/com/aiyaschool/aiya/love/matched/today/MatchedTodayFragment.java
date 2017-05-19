package com.aiyaschool.aiya.love.matched.today;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.base.NestFullListView;
import com.aiyaschool.aiya.base.NestFullListViewAdapter;
import com.aiyaschool.aiya.base.NestFullViewHolder;
import com.aiyaschool.aiya.love.matched.main.Mission;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedTodayFragment extends BaseFragment implements ILoveMatchedTodayView {

    private ImageView ivLeft, ivRight;
    private NestFullListView nflvTodayMission, nflvInviteMission;
    private List<Mission> mMissions;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_matched_today;
    }

    @Override
    protected void initView() {
        initData();
        fm = getParentFragment().getFragmentManager();
        ft = fm.beginTransaction();
        rootView.findViewById(R.id.ll_intimacy).setOnClickListener(this);
        ivLeft = (ImageView) rootView.findViewById(R.id.iv_matched_left);
        ivRight = (ImageView) rootView.findViewById(R.id.iv_matched_right);
        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        nflvTodayMission = ((NestFullListView) rootView.findViewById(R.id.lv_love_matched_today_love_mission));
        nflvTodayMission.setAdapter(new NestFullListViewAdapter<Mission>(R.layout.listview_love_mission, mMissions) {
            @Override
            public void onBind(int pos, Mission mission, NestFullViewHolder holder) {
                holder.setText(R.id.tv_listview_love_matched, "test");
                holder.setOnClickListener(R.id.ibn_listview_love_matched, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().startActivity(new Intent(getContext(), ClockInActivity.class));
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
        nflvInviteMission.setAdapter(new NestFullListViewAdapter<Mission>(R.layout.listview_love_mission, mMissions) {
            @Override
            public void onBind(int pos, Mission mission, NestFullViewHolder holder) {

            }
        });
    }

    private void initData() {
        mMissions = new ArrayList<>();
        mMissions.add(new Mission());
        mMissions.add(new Mission());
        mMissions.add(new Mission());
        mMissions.add(new Mission());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_intimacy:
                startActivity(new Intent(getContext(), IntimacyDetailActivity.class));
                break;
            case R.id.iv_matched_left:
                break;
            case R.id.iv_matched_right:
                startActivity(new Intent(getContext(), OtherDetailActivity.class));
        }
    }
}
