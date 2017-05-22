package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.main.MainActivity;
import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.mvpEmotionRecord.EmotionRecordContract;
import com.aiyaschool.aiya.me.mvpEmotionRecord.EmotionRecordPresenter;
import com.aiyaschool.aiya.me.view.RoundImageView;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.SignUtil;
import com.aiyaschool.aiya.util.UserUtil;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyEmotionActivity extends AppCompatActivity implements EmotionRecordContract.View {

    private static final String TAG = "MyEmotionActivity";
    private static final String LINE = "6";
    private int mPage = 1;
    private String mSex;
    private int mEmotionDataNum;
    private int lastVisibleItem;
    private List<EmotionRecordBean> mEmotionList;
    private boolean mNoData;

    private TextView mTvBack;
    private RecyclerView mRvEmotion;
    private LinearLayout mLlMyEmotion;
    private ImageView mIvEmotionNull;
    private SwipeRefreshLayout mEmotionSwipe;

    private EmotionAdapter mEmotionAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private EmotionRecordContract.Presenter mPresenter;

    private final static int TOP = 0;
    private final static int NORMAL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_emotion);
        initView();

        mEmotionList = new ArrayList<>();


        mPresenter = new EmotionRecordPresenter(this);
        User user = UserUtil.getUser();
        mSex = user.getSex();
        mPresenter.getEmotionRecord(mSex, Integer.toString(mPage++), "6");
    }

    private void initView() {


        mTvBack = (TextView) findViewById(R.id.tv_back);
        mRvEmotion = (RecyclerView) findViewById(R.id.rv_emotion);
        mLlMyEmotion = (LinearLayout) findViewById(R.id.ll_my_emotion);
        mIvEmotionNull = (ImageView) findViewById(R.id.emotion_null);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvEmotion.setLayoutManager(mLinearLayoutManager);
        mRvEmotion.addItemDecoration(new DividerItemDecoration(MyEmotionActivity.this, DividerItemDecoration.VERTICAL));
        mRvEmotion.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mEmotionAdapter != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mEmotionAdapter.getItemCount()) {
                        if (mNoData) {
                            Toast.makeText(MyEmotionActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        } else {
                            mPresenter.getEmotionRecord(mSex, Integer.toString(mPage++), "6");
                        }
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });


        mEmotionSwipe = (SwipeRefreshLayout) findViewById(R.id.emotion_swipe_refresh);
        mEmotionSwipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mEmotionSwipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mEmotionSwipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mEmotionSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPage = 1;
                mEmotionList.clear();
                mPresenter.getEmotionRecord(mSex, Integer.toString(mPage++), LINE);
                mNoData = false;
                mEmotionSwipe.setRefreshing(false);
            }
        });


        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setEmotionRecordData(List<EmotionRecordBean> emotionRecordData) {
        System.out.println(mEmotionList.size());
        mEmotionList.addAll(emotionRecordData);
        if (mEmotionAdapter == null) {
            mEmotionAdapter = new EmotionAdapter();
            mRvEmotion.setAdapter(mEmotionAdapter);
        } else {
            mEmotionAdapter.notifyDataSetChanged();
        }

//        Log.d(TAG, "setEmotionRecordData: "+mEmotionList.);
        Log.d(TAG, "setEmotionRecordData: " + mEmotionList.size());

    }

    @Override
    public void setEmotionRecordNum(int num) {
        mEmotionDataNum = num;
    }

    @Override
    public void setBackGroundIfNoData() {
        if (mEmotionList.size() == 0) {
            mLlMyEmotion.setVisibility(View.INVISIBLE);
            mIvEmotionNull.setVisibility(View.VISIBLE);
        } else {
            mNoData = true;
        }

    }

    public String parseDate(String date) {
        String timeString = date + "000";
        long timeLong = Long.valueOf(timeString);
//        Log.d(TAG, "parseDate: "+new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeLong)));
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timeLong));
    }

    class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder>{


        @Override
        public EmotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(MyEmotionActivity.this);
            View v = null;
            if(viewType == TOP){
                v = inflater.inflate(R.layout.emotion_record_item1,parent,false);
            }else if(viewType == NORMAL){
                v = inflater.inflate(R.layout.emotion_record_item2,parent,false);
            }
            EmotionViewHolder emotionViewHolder = new EmotionViewHolder(v, viewType);
            return emotionViewHolder;
        }

        @Override
        public void onBindViewHolder(EmotionViewHolder holder, int position) {
            holder.mTvName.setText(mEmotionList.get(position).getUsername());
            holder.mTvSchool.setText(mEmotionList.get(position).getSchool());
//            Log.d(TAG, "onBindViewHolder: startTime"+mEmotionList.get(position).getStarttime());
            holder.mStartTime.setText(parseDate(mEmotionList.get(position).getStarttime()));
            holder.mIntimacy.setText(mEmotionList.get(position).getIntimacy());
            holder.mRivPhoto.setImageResource(R.drawable.guanggao1);
            Glide.with(MyEmotionActivity.this).load(mEmotionList.get(position).getAvatar().getNormal())
                    .error(R.drawable.guanggao1)
                    .centerCrop()
                    .transform(new GlideCircleTransform(MyEmotionActivity.this))
                    .into(holder.mRivPhoto);
            switch (holder.getItemViewType()) {
                case TOP:
                    long a = System.currentTimeMillis() - Long.valueOf(parseDate(mEmotionList.get(position).getStarttime()));

                    holder.mTotalDay.setText(a / 1000 / 60 / 60 / 24 + "");
                    break;
                case NORMAL:
//                    Log.d(TAG, "onBindViewHolder: endTime"+mEmotionList.get(position).getEndtime());
                    holder.mEndTime.setText(parseDate(mEmotionList.get(position).getEndtime()));
                    break;
                default:
                    break;


            }
        }

        @Override
        public int getItemCount() {
            return mEmotionList.size();
        }

        class EmotionViewHolder extends RecyclerView.ViewHolder{

            private ImageView mRivPhoto;
            private TextView mTvName, mTvSchool, mStartTime, mEndTime, mIntimacy, mDestroyLove, mTotalDay;

            public EmotionViewHolder(View itemView, int viewType) {
                super(itemView);
                mRivPhoto = (ImageView) itemView.findViewById(R.id.my_photo);
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mTvSchool = (TextView) itemView.findViewById(R.id.tv_school);
                mStartTime = (TextView) itemView.findViewById(R.id.start_time);
                mIntimacy = (TextView) itemView.findViewById(R.id.intimacy);
                switch (viewType) {
                    case TOP:
                        mTotalDay = (TextView) itemView.findViewById(R.id.tv_total);
                        mDestroyLove = (TextView) itemView.findViewById(R.id.tv_destroy_love);
                        mDestroyLove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                APIUtil.getDestroyLoveApi()
                                        .destroyLove(UserUtil.getUser().getLoveId())
                                        .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .unsubscribeOn(Schedulers.io())
                                        .subscribe(new Observer<HttpResult>() {
                                            @Override
                                            public void onSubscribe(@NonNull Disposable d) {
                                                Log.i(TAG, "onSubscribe: destroyLove");
                                            }

                                            @Override
                                            public void onNext(@NonNull HttpResult httpResult) {
                                                Log.i(TAG, "onNext: destroyLove " + httpResult);
                                                if ("2000".equals(httpResult.getState())) {
                                                    UserUtil.setLoveId("0");
                                                    setResult(RESULT_OK, new Intent().putExtra("flag", "destroyLove"));
                                                }
                                            }

                                            @Override
                                            public void onError(@NonNull Throwable e) {
                                                Log.i(TAG, "onError: destroyLove");
                                            }

                                            @Override
                                            public void onComplete() {
                                                Log.i(TAG, "onComplete: destroyLove");
                                            }
                                        });
                            }
                        });
                        break;
                    case NORMAL:
                        mEndTime = (TextView) itemView.findViewById(R.id.end_time);
                        break;
                    default:
                        break;

                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 && mEmotionList.get(0).getEndtime().equals("0")) {
                return TOP;
            }else{
                return NORMAL;
            }
        }
    }
}
