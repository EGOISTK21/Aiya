package com.aiyaschool.aiya.activity.otherDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/4/8.
 */

public class OtherDetailActivity extends BaseActivity implements OtherDetailContract.View {

    private static final String TAG = "OtherDetailActivity";
    private User mUser;
    private OtherDetailContract.Presenter mPresenter;
    @BindColor(R.color.colorFutureRed)
    int hitText;
    @BindColor(R.color.colorPureWhite)
    int hitBG;
    @BindView(R.id.iv_other_avatar)
    CircleImageView ivOtherAvatar;
    @BindView(R.id.tv_other_username)
    TextView tvOtherUsername;
    @BindView(R.id.tv_other_profile)
    TextView tvOtherProfile;
    @BindView(R.id.tv_other_school)
    TextView tvOtherSchool;
    @BindView(R.id.tv_other_age)
    TextView tvOtherAge;
    @BindView(R.id.tv_other_height)
    TextView tvOtherHeight;
    @BindView(R.id.tv_other_character)
    TextView tvOtherCharacter;
    @BindView(R.id.tv_other_hobby)
    TextView tvOtherHobby;
    @BindView(R.id.btn_hit)
    Button btnHit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_card;
    }

    protected void initView() {
        try {
            Bundle bundle = getIntent().getExtras();
            mUser = bundle.getParcelable("other detail");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mUser == null) {
            mUser = UserUtil.getTa();
            btnHit.setBackgroundColor(hitBG);
            btnHit.setTextColor(hitText);
            btnHit.setText("解除关系");
        }
        Log.i(TAG, "initView: " + mUser);
        if (mUser != null) {
            Glide.with(this).load(mUser.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                    .transform(new GlideCircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivOtherAvatar);
            tvOtherUsername.setText(mUser.getUsername());
            tvOtherProfile.setText(mUser.getProfile());
            tvOtherSchool.setText(mUser.getSchool());
            tvOtherAge.setText(mUser.getAge());
            tvOtherHeight.setText(mUser.getHeight());
            tvOtherCharacter.setText(mUser.getCharacter());
            tvOtherHobby.setText(mUser.getHobby());
        }
        mPresenter = new OtherDetailPresenter(this);
    }

    @OnClick(R.id.tv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.iv_other_avatar)
    void showNormal() {
        ImageView imageView = new ImageView(this);
        Glide.with(this).load(mUser.getAvatar().getNormal().getFace()).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivOtherAvatar);
        AlertDialog.Builder imageDialog = new AlertDialog.Builder(this);
        imageDialog.setView(imageView);
        imageDialog.create();
        imageDialog.show();
    }

    @OnClick(R.id.btn_hit)
    void hit() {
        if (UserUtil.getUser().isMatched()) {
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
                                finish();
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
        } else {
            mPresenter.touch(mUser.getId());
            setResult(RESULT_CANCELED, new Intent().putExtra("flag", "destroyLove"));
        }
    }
}
