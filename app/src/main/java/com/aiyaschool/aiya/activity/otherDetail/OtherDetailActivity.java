package com.aiyaschool.aiya.activity.otherDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

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
    @BindView(R.id.rl_background)
    RelativeLayout rlBackground;
    @BindView(R.id.iv_other_avatar)
    CircleImageView ivOtherAvatar;
    @BindView(R.id.tv_other_username)
    TextView tvOtherUsername;
    @BindView(R.id.my_photo_albun)
    LinearLayout mLlMyPhotoAlbum;
    @BindView(R.id.photo1)
    ImageView imageView1;
    @BindView(R.id.photo2)
    ImageView imageView2;
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
                    break;
                case 2:
                    requestid = bundle.getString("requestid");
                    fromuserid = bundle.getString("fromuserid");
                    btnHit.setVisibility(View.INVISIBLE);
                    llRespanseLiao.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    btnHit.setText("去互动吧");
                    btnHit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    break;
                case 4:
                    btnHit.setBackgroundColor(hitBG);
                    btnHit.setTextColor(hitText);
                    btnHit.setText("解除关系");
                    break;
            }
            mUser = bundle.getParcelable("other detail");
            mPresenter = new OtherDetailPresenter(this);
            mPresenter.loadImgWall(mUser.getId());
            Glide.with(this).load(mUser.getAvatar().getThumb().getFace()).asBitmap().error(R.drawable.guanggao1).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(new ViewTarget<RelativeLayout, Bitmap>(rlBackground) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    rlBackground.setBackground(new BitmapDrawable(resource));
                }
            });
            Glide.with(this).load(mUser.getAvatar().getThumb().getFace()).error(R.drawable.guanggao1).centerCrop()
                    .transform(new GlideCircleTransform(this)).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().into(ivOtherAvatar);
            tvOtherUsername.setText(mUser.getUsername());
            tvOtherProfile.setText(mUser.getProfile());
            tvOtherSchool.setText(mUser.getSchool());
            tvOtherAge.setText(mUser.getAge());
            tvOtherHeight.setText(mUser.getHeight());
            tvOtherCharacter.setText(mUser.getCharacter());
            tvOtherHobby.setText(mUser.getHobby());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "initView: " + mUser);
    }

    @Override
    public void setImgWall(List<Gallery> imgWall) {
        switch (imgWall.size()) {
            case 1:
                Picasso.with(this)
                        .load(imgWall.get(0).getImg().getThumb())
                        .placeholder(R.drawable.mis_default_error)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(238, 181)
                        .centerCrop()
                        .into(imageView1);
                break;
            case 2:
                Picasso.with(this)
                        .load(imgWall.get(0).getImg().getThumb())
                        .placeholder(R.drawable.mis_default_error)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(238, 181)
                        .centerCrop()
                        .into(imageView1);
                Picasso.with(this)
                        .load(imgWall.get(1).getImg().getThumb())
                        .placeholder(R.drawable.mis_default_error)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(238, 181)
                        .centerCrop()
                        .into(imageView2);
                break;
        }
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

    @OnClick(value = R.id.my_photo_albun)
    void showAlbum() {

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
