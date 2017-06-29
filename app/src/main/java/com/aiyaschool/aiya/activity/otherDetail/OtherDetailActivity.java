package com.aiyaschool.aiya.activity.otherDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.AvatarActivity;
import com.aiyaschool.aiya.base.BaseActivity;
import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.activity.PhotoActivity;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

/**
 * Created by EGOISTK21 on 2017/4/8.
 */

public class OtherDetailActivity extends BaseActivity implements OtherDetailContract.View {

    private static final String TAG = "OtherDetailActivity";
    private String requestid;
    private String fromuserid;
    private User mUser;
    private List<Gallery> imgWall;
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
    @BindView(R.id.my_photo_album)
    LinearLayout mLlMyPhotoAlbum;
    @BindViews({R.id.iv, R.id.photo1, R.id.photo2, R.id.photo3, R.id.photo4, R.id.photo5, R.id.photo6, R.id.photo7, R.id.photo8})
    ImageView[] imageViews;
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

    private void initLover() {
        if (UserUtil.getUser().isMatched()) {
            APIUtil.getLoverInfoApi()
                    .getLoverInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(new Observer<HttpResult<User>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            Log.i(TAG, "onSubscribe: initView");
                        }

                        @Override
                        public void onNext(@NonNull HttpResult<User> userHttpResult) {
                            Log.i(TAG, "onNext: initView " + userHttpResult);
                            if ("2000".equals(userHttpResult.getState())) {
                                UserUtil.setTa(userHttpResult.getData());
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.i(TAG, "onError: initView " + e);
                        }

                        @Override
                        public void onComplete() {
                            Log.i(TAG, "onComplete: initView");
                        }
                    });
        }
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
                    initLover();
                    btnHit.setBackgroundColor(hitBG);
                    btnHit.setTextColor(hitText);
                    btnHit.setText("解除关系");
                    break;
            }
            mUser = bundle.getParcelable("other detail");
            mPresenter = new OtherDetailPresenter(this);
            mPresenter.loadImgWall(null, null, mUser.getId());
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
        this.imgWall = imgWall;
        if (imgWall.size() == 0) {
            mLlMyPhotoAlbum.setVisibility(View.GONE);
        } else if (imgWall.size() < 10) {
            for (int i = 0; i < imgWall.size(); i++) {
                Picasso.with(this)
                        .load(imgWall.get(i).getImg().getThumb())
                        .placeholder(R.drawable.mis_default_error)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(238, 181)
                        .centerCrop()
                        .into(imageViews[i]);
            }
            for (int i = imgWall.size(); i < 9; i++) {
                imageViews[i].setVisibility(View.GONE);
            }
        } else {
            for (int i = 0; i < imgWall.size(); i++) {
                Picasso.with(this)
                        .load(imgWall.get(i).getImg().getThumb())
                        .placeholder(R.drawable.mis_default_error)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(238, 181)
                        .centerCrop()
                        .into(imageViews[i]);
            }
        }
    }

    @OnClick(R.id.tv_back)
    void back() {
        finish();
    }

    @OnClick(R.id.iv_other_avatar)
    void showNormal() {
        startActivity(new Intent(OtherDetailActivity.this, AvatarActivity.class).putExtra("url", mUser.getAvatar().getNormal().getFace()));
    }

    private void showAlbum(int index) {
        List<Gallery> galleryList = new ArrayList<>();
        galleryList.addAll(imgWall);
        ArrayList<Rect> rects = new ArrayList<>();
        for (int i = 0; i < imgWall.size(); i++) {
            Rect rect = new Rect();
            final View child = new View(OtherDetailActivity.this);
            Glide.with(OtherDetailActivity.this).load(imgWall.get(i).getImg().getNormal()).asBitmap().error(R.drawable.guanggao1).centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE).into(new ViewTarget<View, Bitmap>(child) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    child.setBackground(new BitmapDrawable(resource));
                }
            });
            child.getGlobalVisibleRect(rect);
            rects.add(rect);
        }
        Intent intent = new Intent(OtherDetailActivity.this, PhotoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("gallery", (Serializable) galleryList);
        intent.putExtras(bundle);
        intent.putExtra("index", index);
        intent.putExtra("bounds", rects);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @OnClick(value = R.id.iv)
    void showAlbum0() {
        showAlbum(0);
    }

    @OnClick(value = R.id.photo1)
    void showAlbum1() {
        showAlbum(1);
    }

    @OnClick(value = R.id.photo2)
    void showAlbum2() {
        showAlbum(2);
    }

    @OnClick(value = R.id.photo3)
    void showAlbum3() {
        showAlbum(3);
    }

    @OnClick(value = R.id.photo4)
    void showAlbum4() {
        showAlbum(4);
    }

    @OnClick(value = R.id.photo5)
    void showAlbum5() {
        showAlbum(5);
    }

    @OnClick(value = R.id.photo6)
    void showAlbum6() {
        showAlbum(6);
    }

    @OnClick(value = R.id.photo7)
    void showAlbum7() {
        showAlbum(7);
    }

    @OnClick(value = R.id.photo8)
    void showAlbum8() {
        showAlbum(8);
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
