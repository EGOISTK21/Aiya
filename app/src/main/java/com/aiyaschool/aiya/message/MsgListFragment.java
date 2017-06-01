package com.aiyaschool.aiya.message;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.util.ToastUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;

import java.util.List;

/**
 * Created by XZY on 2017/3/8.
 */

public class MsgListFragment extends BaseFragment {

    private RecyclerView mMsgRecyclerView;

    public static MsgListFragment newInstance() {
        return new MsgListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_default;
    }

    @Override
    protected void initView() {
        mMsgRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mMsgRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //        if (登录成功) {
        List<Msg> msgList = new Msg().getMsgs2();
//        }else{
//            List<Msg> msgList = new Msg().getMsgs();
//        }
        MsgAdapter adapter = new MsgAdapter(msgList);
        //RecycleView 增加边距
        int spacingInPixels = 30;
        mMsgRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        mMsgRecyclerView.setAdapter(adapter);
    }


    private class MsgHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mTime;
        private TextView mPreView;
        private CircleImageView mCircleImageView;
        private Msg mMsg;
//        private OnItemClickListener mListenter;

        public MsgHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.msg_title);
            mTime = (TextView) itemView.findViewById(R.id.msg_time);
            mPreView = (TextView) itemView.findViewById(R.id.msg_preview);
            mCircleImageView = (CircleImageView) itemView.findViewById(R.id.circleImageView);
        }

        public void bindCrime(Msg msg) {
            mMsg = msg;
            mTitle.setText(mMsg.getmTitle());
            mTime.setText(mMsg.getmTime());
            mPreView.setText(mMsg.getmPreview());
            mCircleImageView.setImageResource(mMsg.getmImageView());
        }


//        @Override
//        public void onClick(View v) {
////            Log.e("1111", String.valueOf(getAdapterPosition()));
//
//            mListenter.OnItemClick(getAdapterPosition(), v);
//
//        }


    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
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

        @Override
        public MsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_msg, parent, false);
            return new MsgHolder(view);
        }

        @Override
        public void onBindViewHolder(MsgHolder holder, int position) {
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            Msg msg = mMsg.get(position);
            holder.bindCrime(msg);
            if (msg.getmTitle().equals("情侣聊天")) {
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
            } else if (msg.getmTitle().equals("消息通知")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ft.addToBackStack(null);
                        ft.replace(R.id.container_message, HitListFragment.newInstance(), null).commit();
                    }
                });
            }/*else if (msg.getmTitle().equals("任务消息")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            }*/ else if (msg.getmTitle().equals("攻略指南")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (msg.getmTitle().equals("匹配请求")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mMsg.size();
        }
    }


}
