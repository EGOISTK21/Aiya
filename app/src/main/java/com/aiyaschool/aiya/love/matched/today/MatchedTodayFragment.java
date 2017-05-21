package com.aiyaschool.aiya.love.matched.today;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.otherDetail.OtherDetailActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.base.NestFullListView;
import com.aiyaschool.aiya.base.NestFullListViewAdapter;
import com.aiyaschool.aiya.base.NestFullViewHolder;
import com.aiyaschool.aiya.love.matched.main.Mission;
import com.aiyaschool.aiya.me.activity.PersonalDataActivity;

import java.util.List;

import butterknife.OnClick;

import static com.aiyaschool.aiya.activity.main.MainActivity.DESTROY_LOVE;


/**
 * Created by EGOISTK21 on 2017/3/21.
 */

public class MatchedTodayFragment extends BaseFragment {

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
        fm = getParentFragment().getFragmentManager();
        ft = fm.beginTransaction();
        rootView.findViewById(R.id.ll_intimacy).setOnClickListener(this);
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


    @OnClick(R.id.iv_matched_left)
    void showTa() {
        startActivityForResult(new Intent(getContext(), OtherDetailActivity.class), DESTROY_LOVE);
    }

    @OnClick(R.id.iv_matched_right)
    void showMe() {
        startActivity(new Intent(getActivity(), PersonalDataActivity.class));
    }

    @OnClick(R.id.ll_intimacy)
    void showIntimacy() {
        startActivity(new Intent(getContext(), IntimacyDetailActivity.class));
    }

}
