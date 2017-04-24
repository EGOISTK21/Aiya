package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.me.bean.GuestItem;
import com.aiyaschool.aiya.me.view.RoundImageView;


import java.util.ArrayList;
import java.util.List;

public class MyGuestActivity extends AppCompatActivity {

    private TextView mTvBack;
    private RecyclerView mRvGuest;

    private List<GuestItem> mGuestItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guest);
        initView();

    }

    private void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mRvGuest = (RecyclerView) findViewById(R.id.rv_guest);


        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyGuestActivity.this,MainActivity.class);
                intent.putExtra("Flag","Me");
                startActivity(intent);
            }
        });

        mGuestItems = new ArrayList<>();
        for(int i=0;i<10;i++){
            GuestItem guestitem = new GuestItem();
            guestitem.setId(R.drawable.guanggao1);
            guestitem.setName("李太白"+i);
            guestitem.setSchool("西安电子科技大学"+i);
            guestitem.setTime(i+"分钟前");
            mGuestItems.add(guestitem);
        }
        System.out.println("mGuestItem"+mGuestItems.size());
        mRvGuest.setLayoutManager(new LinearLayoutManager(this));
        mRvGuest.setAdapter(new GuestAdapter());
        mRvGuest.addItemDecoration(new DividerItemDecoration(MyGuestActivity.this, DividerItemDecoration.VERTICAL));
    }

    class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> implements View.OnClickListener{

        @Override
        public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            System.out.println("onCreateViewHolder");
            View view = LayoutInflater.from(MyGuestActivity.this).inflate(R.layout.guest_record_item,parent,false);
            GuestViewHolder mGuestViewHolder = new GuestViewHolder(view);
            return mGuestViewHolder;
        }

        @Override
        public void onBindViewHolder(GuestViewHolder holder, int position) {
            holder.mRivPhoto.setImageResource(mGuestItems.get(position).getId());
            holder.mTvName.setText(mGuestItems.get(position).getName());
            holder.mTvSchool.setText(mGuestItems.get(position).getSchool());
            holder.mTvTime.setText(mGuestItems.get(position).getTime());
        }



        @Override
        public int getItemCount() {
            return mGuestItems.size();
        }

        @Override
        public void onClick(View v) {
            int position = mRvGuest.getChildAdapterPosition(v);
            //可以在这个地方进入访客的具体资料
            //处理点击事件

        }

        class GuestViewHolder extends RecyclerView.ViewHolder{

            private RoundImageView mRivPhoto;
            private TextView mTvName,mTvSchool,mTvTime;

            public GuestViewHolder(View itemView) {
                super(itemView);
                mRivPhoto = (RoundImageView) itemView.findViewById(R.id.my_photo);
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mTvSchool = (TextView) itemView.findViewById(R.id.tv_school);
                mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            }
        }

        //当有新的访客进入时，调用此函数
        public void addItem(GuestItem guestItem,int position){
            mGuestItems.add(position, guestItem);
            notifyItemInserted(position);
            mRvGuest.scrollToPosition(position);
        }
    }



}
