package com.aiyaschool.aiya.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.message.ui.activity.ChatQQActivity;
import com.aiyaschool.aiya.message.ui.view.CircleImageView;

import java.util.List;

/**
 * Created by XZY on 2017/3/8.
 */

public class MsgListFragment extends android.support.v4.app.Fragment{
    private RecyclerView mMsgRecyclerView;
    private MsgAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_message_list, container, false);

        mMsgRecyclerView = (RecyclerView) view.findViewById(R.id.view_list_message);
        mMsgRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
//        if (登录成功) {
            List<Msg> msgList = new Msg().getMsgs2();
//        }else{
//            List<Msg> msgList = new Msg().getMsgs();
//        }
        mAdapter = new MsgAdapter(msgList);
        mMsgRecyclerView.setAdapter(mAdapter);

    }
//
//    @Override
//    public void OnItemClick(int position, View view) {
////        Log.e("1111", String.valueOf(position));
//
//    }


    private class MsgHolder extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mTime;
        private TextView mPreView;
        private CircleImageView mCircleImageView;
        private Msg mMsg;
//        private OnItemClickListener mListenter;

        public MsgHolder(View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            mTitle = (TextView)
                    itemView.findViewById(R.id.msg_title);
            mTime = (TextView)
                    itemView.findViewById(R.id.msg_time);
            mPreView = (TextView)
                    itemView.findViewById(R.id.msg_preview);
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
            Msg msg = mMsg.get(position);
            holder.bindCrime(msg);
            if (msg.getmTitle().equals("情侣聊天")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (msg.getmTitle().equals("消息通知")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            }else if (msg.getmTitle().equals("任务消息")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            }else if (msg.getmTitle().equals("攻略指南")) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChatQQActivity.class);
                        startActivity(intent);
                    }
                });
            }else if (msg.getmTitle().equals("匹配请求")) {
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
