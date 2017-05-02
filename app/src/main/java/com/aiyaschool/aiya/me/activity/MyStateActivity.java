package com.aiyaschool.aiya.me.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;

public class MyStateActivity extends AppCompatActivity {

    private TextView mTvBack;
    private RecyclerView mRvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_state);
        initView();

    }

    private void initView() {

        mTvBack = (TextView) findViewById(R.id.tv_back);
        mRvState = (RecyclerView) findViewById(R.id.rv_my_state);
        mRvState.setLayoutManager(new LinearLayoutManager(this));
        mRvState.setAdapter(new StateAdapter());
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder>{

        @Override
        public StateAdapter.StateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyStateActivity.this).inflate(R.layout.my_state_item,parent,false);
            StateViewHolder mStateViewHolder = new StateViewHolder(view);
            return mStateViewHolder;
        }

        @Override
        public void onBindViewHolder(StateAdapter.StateViewHolder holder, int position) {
//            holder.mIvPhoto.setImageResource();
//            holder.mTvDay.setText();
//            holder.mTvMonth.setText();
//            holder.mTvContent.setText();
//            holder.mTvPosition.setText();
        }

        @Override
        public int getItemCount() {
            return 9;
        }

        class StateViewHolder extends RecyclerView.ViewHolder {

            private TextView mTvDay,mTvMonth,mTvContent,mTvPosition;

            private ImageView mIvPhoto;
            public StateViewHolder(View itemView) {
                super(itemView);

                mTvDay = (TextView) itemView.findViewById(R.id.tv_day);
                mTvMonth = (TextView) itemView.findViewById(R.id.tv_month);
                mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
                mTvPosition = (TextView) itemView.findViewById(R.id.tv_position);

                mIvPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            }
        }
    }
}
