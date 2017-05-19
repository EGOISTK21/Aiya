package com.aiyaschool.aiya.me.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.me.mvpGuestRecord.GuestDataContract;
import com.aiyaschool.aiya.me.mvpGuestRecord.GuestDataPresenter;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataContract;
import com.aiyaschool.aiya.me.mvpPersonData.PersonDataPresenter;
import com.aiyaschool.aiya.me.view.RoundImageView;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

public class MyGuestActivity extends AppCompatActivity implements GuestDataContract.View {

    private static final String TAG = "MyGuestActivity";

    private TextView mTvBack;
    private RecyclerView mRvGuest;
    private LinearLayout mLlMyGuest;
    private ImageView mIvGuestNull;
    private SwipeRefreshLayout mGuestSwipe;
    private LinearLayoutManager linearLayoutManager;

    private GuestAdapter mGuestAdapter;

    private List<OuInfo> mGuestList;

    private int lastVisibleItem;

    private GuestDataContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guest);
        initView();

        presenter = new GuestDataPresenter(this);
        presenter.getGuestRecord("1", "6");

    }

    private void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mRvGuest = (RecyclerView) findViewById(R.id.rv_guest);
        mLlMyGuest = (LinearLayout) findViewById(R.id.my_guest);
        mIvGuestNull = (ImageView) findViewById(R.id.guest_null);

        mGuestSwipe = (SwipeRefreshLayout) findViewById(R.id.guest_swipe_refresh);
        mGuestSwipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mGuestSwipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mGuestSwipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mGuestSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                presenter.retrieveGuestRecord("1", "6");
                mGuestSwipe.setRefreshing(false);

            }
        });


        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        mRvGuest.setLayoutManager(linearLayoutManager);

        mRvGuest.addItemDecoration(new DividerItemDecoration(MyGuestActivity.this, DividerItemDecoration.VERTICAL));
        mRvGuest.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mGuestAdapter.getItemCount()) {

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


    }


    @Override
    public void setGuestRecordData(List<OuInfo> guestItem) {
        mGuestList = guestItem;
        mGuestAdapter = new GuestAdapter();
        mRvGuest.setAdapter(mGuestAdapter);
        Log.d(TAG, "setGuestRecordData: " + mGuestList.size());
    }

    @Override
    public void retrieveGuestRecord(List<OuInfo> guestItem) {
        if (mGuestList != null) {
            mGuestList = null;
        }
        mGuestList = guestItem;
        mGuestAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBackGroundIfNoData() {
        mLlMyGuest.setVisibility(View.INVISIBLE);
        mIvGuestNull.setVisibility(View.VISIBLE);
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
//            holder.mRivPhoto.setImageResource(mGuestItems.get(position).getId());
//            Glide.with(MyGuestActivity.this).load(mGuestList.get(position).getAvatar().getNormal())
//                    .error(R.drawable.guanggao1).into(holder.mRivPhoto);
            holder.mTvName.setText(mGuestList.get(position).getUsername());
            holder.mTvSchool.setText(mGuestList.get(position).getSchool());
            holder.mTvTime.setText(mGuestList.get(position).getCreatetime());
        }



        @Override
        public int getItemCount() {
            return mGuestList.size();
        }

        @Override
        public void onClick(View v) {
            int position = mRvGuest.getChildAdapterPosition(v);
            //可以在这个地方进入访客的具体资料
            //处理点击事件

        }

        class GuestViewHolder extends RecyclerView.ViewHolder{

            private ImageView mRivPhoto;
            private TextView mTvName,mTvSchool,mTvTime;

            public GuestViewHolder(View itemView) {
                super(itemView);
                mRivPhoto = (ImageView) itemView.findViewById(R.id.my_photo);
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mTvSchool = (TextView) itemView.findViewById(R.id.tv_school);
                mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
            }
        }

        //当有新的访客进入时，调用此函数
        public void addItem(OuInfo guestItem, int position) {
            mGuestList.add(position, guestItem);
            notifyItemInserted(position);
            mRvGuest.scrollToPosition(position);
        }
    }



}
