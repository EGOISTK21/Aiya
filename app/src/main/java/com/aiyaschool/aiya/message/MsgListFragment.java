package com.aiyaschool.aiya.message;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.message.hit.HitListActivity;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.message.utils.DateTimeUtils;
import com.aiyaschool.aiya.util.GlideRoundTransform;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by XZY on 2017/3/8.
 */

public class MsgListFragment extends BaseFragment {

    private RecyclerView mMsgRecyclerView;
    private List<Msg> msgList;
    private List<Long> msgTime;
    private List<String> msgPre;
    private MsgAdapter adapter;

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_default;
    }

    @Override
    protected void initView() {
        mMsgRecyclerView = rootView.findViewById(R.id.recycler_view);
        mMsgRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        msgList = new Msg().getMsgs2();
        adapter = new MsgAdapter(msgList);
        //RecycleView 增加边距
        int spacingInPixels = 30;
        mMsgRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        mMsgRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        msgTime = UserUtil.getMsgTime();
        msgPre = UserUtil.getMsgPre();
        for (int i = 0; i < 4; i++) {
            msgList.get(i).setmTime(DateTimeUtils.setChatTime(msgTime.get(i)));
            msgList.get(i).setmPreview(msgPre.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    private class MsgHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mTime;
        private TextView mPreView;
        private CircleImageView mCircleImageView;
        private Msg mMsg;

        MsgHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.msg_title);
            mTime = itemView.findViewById(R.id.msg_time);
            mPreView = itemView.findViewById(R.id.msg_preview);
            mCircleImageView = itemView.findViewById(R.id.circleImageView);
        }

        void bindCrime(Msg msg) {
            mMsg = msg;
            mTitle.setText(mMsg.getmTitle());
            mTime.setText(mMsg.getmTime());
            mPreView.setText(mMsg.getmPreview());
            mCircleImageView.setImageResource(mMsg.getmImageView());
        }
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
//            outRect.left = space;
//            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }

    private class MsgAdapter extends RecyclerView.Adapter<MsgHolder> {

        private List<Msg> mMsg;

        public MsgAdapter(List<Msg> msgs) {
            this.mMsg = msgs;
        }

        public List<Msg> getMsg() {
            return mMsg;
        }

        @Override
        public MsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_msg, parent, false);
            return new MsgHolder(view);
        }

        @Override
        public void onBindViewHolder(MsgHolder holder, int position) {
            Msg msg = mMsg.get(position);
            holder.bindCrime(msg);
            switch (msg.getmTitle()) {
                case "情侣聊天":
                    if (UserUtil.getUser().isMatched()) {
                        Glide.with(getContext()).load(UserUtil.getTa().getAvatar().getThumb().getFace()).centerCrop()
                                .transform(new GlideRoundTransform(getContext())).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(holder.mCircleImageView);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (UserUtil.getUser().isMatched()) {
                                startActivity(new Intent(getContext(), ChatQQActivity.class));
                            } else {
                                ToastUtil.show("你还没有伴侣");
                                ((MainActivity) getActivity()).setLovePage();
                            }
                        }
                    });
                    break;
                case "消息通知":
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), EmptyActivity.class).putExtra("title", "消息通知"));
                        }
                    });
                    break;
                case "匹配请求":
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), HitListActivity.class));
                        }
                    });
                    break;
                case "攻略指南":
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(), EmptyActivity.class).putExtra("title", "攻略指南"));
                        }
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return mMsg.size();
        }
    }

}
