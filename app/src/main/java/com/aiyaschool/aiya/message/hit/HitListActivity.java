package com.aiyaschool.aiya.message.hit;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.OuInfo;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EGOISTK21 on 2017/5/27.
 */

public class HitListActivity extends BaseActivity implements HitListContract.View {

    private HitListContract.Presenter mPresenter;
    private static final String LINE = "10";
    private int mPage = 1;
    private int mGuestNum;
    private boolean mNoData;

    private TextView mTvBack;
    private RecyclerView mRvGuest;
    private LinearLayout mLlMyGuest;
    private ImageView mIvGuestNull;
    private SwipeRefreshLayout mGuestSwipe;
    private LinearLayoutManager linearLayoutManager;

    private GuestAdapter mGuestAdapter;

    private List<OuInfo> mGuestList;

    private int lastVisibleItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_hit;
    }

    @Override
    protected void initView() {
        mGuestList = new ArrayList<>();
        mPresenter = new HitListPresenter(this);
        mPresenter.getGuestRecord(Integer.toString(mPage++), LINE);
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

                mPage = 1;
                mGuestList.clear();
                mPresenter.getGuestRecord(Integer.toString(mPage++), LINE);
                mNoData = false;
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

        mRvGuest.addItemDecoration(new DividerItemDecoration(HitListActivity.this, DividerItemDecoration.VERTICAL));
        mRvGuest.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mGuestAdapter != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mGuestAdapter.getItemCount()) {
                        if (mNoData) {
                            Toast.makeText(HitListActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        } else {
                            mPresenter.getGuestRecord(Integer.toString(mPage++), LINE);
                        }
                    }
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
        mGuestList.addAll(guestItem);
        if (mGuestAdapter == null) {
            mGuestAdapter = new GuestAdapter();
            mRvGuest.setAdapter(mGuestAdapter);
        } else {
            mGuestAdapter.notifyDataSetChanged();
        }

    }

//    @Override
//    public void retrieveGuestRecord(List<OuInfo> guestItem) {
//        if (mGuestList != null) {
//            mGuestList = null;
//        }
//        mGuestList = guestItem;
//        mGuestAdapter.notifyDataSetChanged();
//    }

    @Override
    public void setBackGroundIfNoData() {
        if (mGuestList.size() == 0) {
            mLlMyGuest.setVisibility(View.INVISIBLE);
            mIvGuestNull.setVisibility(View.VISIBLE);
        } else {
            mNoData = true;
        }

    }

    @Override
    public void setGuestDataNum(int num) {
        mGuestNum = num;
    }

    class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> implements View.OnClickListener {

        @Override
        public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            System.out.println("onCreateViewHolder");
            View view = LayoutInflater.from(HitListActivity.this).inflate(R.layout.list_item_hit, parent, false);
            GuestViewHolder mGuestViewHolder = new GuestViewHolder(view);
            return mGuestViewHolder;
        }

        @Override
        public void onBindViewHolder(final GuestViewHolder holder, int position) {

            Glide.with(HitListActivity.this).load(mGuestList.get(position).getAvatar().getNormal())
                    .error(R.drawable.guanggao1)
                    .centerCrop()
                    .transform(new GlideCircleTransform(HitListActivity.this))
                    .into(holder.mCivAvatar);
            holder.mTvUsername.setText(mGuestList.get(position).getUsername());
            holder.mTvSchool.setText(mGuestList.get(position).getSchool());
            final OuInfo ouInfo = mGuestList.get(position);
            switch (ouInfo.getStatus()) {
                case "0":
                    holder.btnIgnore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.reply(ouInfo.getRequestid(), ouInfo.getUserid(), "no");
                        }
                    });
                    holder.btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPresenter.reply(ouInfo.getRequestid(), ouInfo.getUserid(), "yes");
                        }
                    });
                    break;
                case "1":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("匹配成功");
                    break;
                case "2":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("匹配成功");
                    break;
                case "3":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("未知");
                    break;
                case "4":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("已拒绝");
                    break;
                case "5":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("未知");
                    break;
                case "6":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("随缘匹配中");
                    break;
                case "7":
                    holder.llButton.setVisibility(View.INVISIBLE);
                    holder.mTvResult.setVisibility(View.VISIBLE);
                    holder.mTvResult.setText("已过期");
                    break;
            }
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

        class GuestViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView mCivAvatar;
            private TextView mTvUsername, mTvSchool, mTvResult;
            private LinearLayout llButton;
            private Button btnIgnore, btnAccept;

            public GuestViewHolder(View itemView) {
                super(itemView);
                mCivAvatar = (CircleImageView) itemView.findViewById(R.id.civ_avatar);
                mTvUsername = (TextView) itemView.findViewById(R.id.tv_username);
                mTvSchool = (TextView) itemView.findViewById(R.id.tv_school);
                mTvResult = (TextView) itemView.findViewById(R.id.tv_result);
                llButton = (LinearLayout) itemView.findViewById(R.id.ll_button);
                btnIgnore = (Button) itemView.findViewById(R.id.btn_ignore);
                btnAccept = (Button) itemView.findViewById(R.id.btn_accept);
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
