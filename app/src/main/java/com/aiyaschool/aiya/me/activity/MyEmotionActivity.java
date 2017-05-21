package com.aiyaschool.aiya.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.me.mvpEmotionRecord.EmotionRecordContract;
import com.aiyaschool.aiya.me.mvpEmotionRecord.EmotionRecordPresenter;
import com.aiyaschool.aiya.me.view.RoundImageView;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.UserUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyEmotionActivity extends AppCompatActivity implements EmotionRecordContract.View {

    private static final String TAG = "MyEmotionActivity";

    private TextView mTvBack;
    private RecyclerView mRvEmotion;
    private LinearLayout mLlMyEmotion;
    private ImageView mIvEmotionNull;
    private EmotionAdapter mEmotionAdapter;

    private EmotionRecordContract.Presenter mPresenter;

    private final static int TOP = 0;
    private final static int NORMAL = 1;
    private List<EmotionRecordBean> mEmotionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_emotion);
        initView();

        mPresenter = new EmotionRecordPresenter(this);
        mPresenter.getEmotionRecord("1", "1", "3");
    }

    private void initView() {
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mRvEmotion = (RecyclerView) findViewById(R.id.rv_emotion);
        mLlMyEmotion = (LinearLayout) findViewById(R.id.ll_my_emotion);
        mIvEmotionNull = (ImageView) findViewById(R.id.emotion_null);

        mRvEmotion.setLayoutManager(new LinearLayoutManager(this));
        mRvEmotion.addItemDecoration(new DividerItemDecoration(MyEmotionActivity.this, DividerItemDecoration.VERTICAL));



        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setEmotionRecordData(List<EmotionRecordBean> emotionRecordData) {
        mEmotionList = emotionRecordData;
        mEmotionAdapter = new EmotionAdapter();
        mRvEmotion.setAdapter(mEmotionAdapter);
        Log.d(TAG, "setEmotionRecordData: " + mEmotionList.size());

    }

    @Override
    public void setBackGroundIfNoData() {
        mLlMyEmotion.setVisibility(View.INVISIBLE);
        mIvEmotionNull.setVisibility(View.VISIBLE);
    }

    class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder>{

        private int mViewType;
        @Override
        public EmotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mViewType = viewType;
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
            holder.mStartTime.setText(mEmotionList.get(position).getStarttime());
            switch (mViewType) {
                case TOP:
                    holder.mIntimacy.setText(mEmotionList.get(position).getIntimacy());
                    break;
                case NORMAL:
                    holder.mEndTime.setText(mEmotionList.get(position).getEndtime());
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

            private RoundImageView mRivPhoto;
            private TextView mTvName, mTvSchool, mStartTime, mEndTime, mIntimacy, mDestroyLove;

            public EmotionViewHolder(View itemView, int viewType) {
                super(itemView);
                mRivPhoto = (RoundImageView) itemView.findViewById(R.id.my_photo);
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mTvSchool = (TextView) itemView.findViewById(R.id.tv_school);
                mStartTime = (TextView) itemView.findViewById(R.id.start_time);
                switch (viewType) {
                    case TOP:
                        mIntimacy = (TextView) itemView.findViewById(R.id.intimacy);
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
