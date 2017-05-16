package com.aiyaschool.aiya.me.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.me.mvpEmotionRecord.EmotionRecordContract;
import com.aiyaschool.aiya.me.mvpEmotionRecord.EmotionRecordPresenter;

import java.util.List;

public class MyEmotionActivity extends AppCompatActivity implements EmotionRecordContract.View {

    private static final String TAG = "MyEmotionActivity";

    private TextView mTvBack;
    private RecyclerView mRvEmotion;

    private EmotionRecordContract.Presenter mPresenter;

    private final static int TOP = 0;
    private final static int NORMAL = 1;

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
        mRvEmotion.setLayoutManager(new LinearLayoutManager(this));
        mRvEmotion.addItemDecoration(new DividerItemDecoration(MyEmotionActivity.this, DividerItemDecoration.VERTICAL));
        mRvEmotion.setAdapter(new EmotionAdapter());


        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setEmotionRecordData(List<EmotionRecordBean> emotionRecordData) {
        Log.d(TAG, "setEmotionRecordData: " + emotionRecordData.size());
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
            EmotionViewHolder emotionViewHolder = new EmotionViewHolder(v);
            return emotionViewHolder;
        }

        @Override
        public void onBindViewHolder(EmotionViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class EmotionViewHolder extends RecyclerView.ViewHolder{
            public EmotionViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(position==0){
                return TOP;
            }else{
                return NORMAL;
            }
        }
    }
}
