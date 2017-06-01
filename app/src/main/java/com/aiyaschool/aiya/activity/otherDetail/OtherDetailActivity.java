package com.aiyaschool.aiya.activity.otherDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by EGOISTK21 on 2017/4/8.
 */

public class OtherDetailActivity extends BaseActivity implements OtherDetailContract.View {

    private static final String TAG = "OtherDetailActivity";
    private String requestid;
    private String fromuserid;
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
    @BindView(R.id.ll_response_liao)
    LinearLayout llRespanseLiao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_card;
    }

    protected void initView() {
        try {
            Bundle bundle = getIntent().getExtras();
            switch (bundle.getInt("card_flag")) {
                case 1:
                    mUser = bundle.getParcelable("other detail");
                    Glide.with(this).load(mUser.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                            .transform(new GlideCircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivOtherAvatar);
                    tvOtherUsername.setText(mUser.getUsername());
                    tvOtherProfile.setText(mUser.getProfile());
                    tvOtherSchool.setText(mUser.getSchool());
                    tvOtherAge.setText(mUser.getAge());
                    tvOtherHeight.setText(mUser.getHeight());
                    tvOtherCharacter.setText(mUser.getCharacter());
                    tvOtherHobby.setText(mUser.getHobby());
                    break;
                case 2:
                    mUser = bundle.getParcelable("other detail");
                    requestid = bundle.getString("requestid");
                    fromuserid = bundle.getString("fromuserid");
                    btnHit.setVisibility(View.INVISIBLE);
                    llRespanseLiao.setVisibility(View.VISIBLE);
                    Glide.with(this).load(mUser.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                            .transform(new GlideCircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivOtherAvatar);
                    tvOtherUsername.setText(mUser.getUsername());
                    tvOtherProfile.setText(mUser.getProfile());
                    tvOtherSchool.setText(mUser.getSchool());
                    tvOtherAge.setText(mUser.getAge());
                    tvOtherHeight.setText(mUser.getHeight());
                    tvOtherCharacter.setText(mUser.getCharacter());
                    tvOtherHobby.setText(mUser.getHobby());
                    break;
                case 3:
                    mUser = bundle.getParcelable("other detail");
                    btnHit.setText("去互动吧");
                    btnHit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    break;
                case 4:
                    mUser = UserUtil.getTa();
                    btnHit.setBackgroundColor(hitBG);
                    btnHit.setTextColor(hitText);
                    btnHit.setText("解除关系");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "initView: " + mUser);
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
            mPresenter.destroyLove();
        } else {
            setResult(RESULT_CANCELED, new Intent().putExtra("flag", "destroyLove"));
            mPresenter.touch(mUser.getId());
        }
    }

    @OnClick(R.id.btn_ignore_liao)
    void ignore() {
        mPresenter.reply(requestid, fromuserid, "no");
    }

    @OnClick(R.id.btn_accept_liao)
    void accept() {
        mPresenter.reply(requestid, fromuserid, "yes");
    }

    @Override
    public void finishToMain(int result, Intent intent) {
        if (intent != null) {
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
