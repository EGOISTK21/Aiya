package com.aiyaschool.aiya.message;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.message.bean.HitNotification;
import com.aiyaschool.aiya.util.GlideRoundTransform;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class HitListFragment extends BaseFragment {

    private List<HitNotification> mHitNotifications = new ArrayList<>();
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static HitListFragment newInstance() {
        return new HitListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_hit;
    }

    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        HitNotificationAdapter adapter = new HitNotificationAdapter(mHitNotifications);
        mRecyclerView.setAdapter(adapter);
    }

    class HitNotificationAdapter extends RecyclerView.Adapter<HitNotificationAdapter.ViewHolder> {

        List<HitNotification> mHitNotifications;

        class ViewHolder extends RecyclerView.ViewHolder {

            CircleImageView hitAvatar;
            TextView hitUsername;
            TextView hitSchool;

            ViewHolder(View itemView) {
                super(itemView);
                hitAvatar = (CircleImageView) itemView.findViewById(R.id.civ_hit_avatar);
                hitUsername = (TextView) itemView.findViewById(R.id.tv_hit_username);
                hitSchool = (TextView) itemView.findViewById(R.id.tv_hit_school);
            }

        }

        HitNotificationAdapter(List<HitNotification> hitNotifications) {
            mHitNotifications = hitNotifications;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_hit, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HitNotification hitNotification = mHitNotifications.get(position);
            Glide.with(getContext()).load(hitNotification.getUser().getAvatar().getThumb().getFace()).centerCrop()
                    .transform(new GlideRoundTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(holder.hitAvatar);
            holder.hitUsername.setText(hitNotification.getUser().getUsername());
            holder.hitSchool.setText(hitNotification.getUser().getSchool());
        }


        @Override
        public int getItemCount() {
            return mHitNotifications.size();
        }
    }
}
