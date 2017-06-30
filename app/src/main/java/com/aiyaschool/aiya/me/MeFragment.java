package com.aiyaschool.aiya.me;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.activity.MainActivity;
import com.aiyaschool.aiya.bean.Gallery;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.activity.JifenAndGiftActivity;
import com.aiyaschool.aiya.me.activity.MoreSettingActivity;
import com.aiyaschool.aiya.me.activity.MyEmotionActivity;
import com.aiyaschool.aiya.me.activity.MyGuestActivity;
import com.aiyaschool.aiya.me.activity.PersonalDataActivity;
import com.aiyaschool.aiya.me.activity.PhotoAlbumActivity;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.GlideCircleTransform;
import com.aiyaschool.aiya.util.UserUtil;
import com.aiyaschool.aiya.widget.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindViews;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

/**
 * Created by wewarriors on 2017/3/16.
 */

public class MeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private final static String TAG = "MeFragment";
    private Context mContext;
    private CircleImageView mRivMyPhoto;
    private TextView mTvMyName, mTvSignName, mTVJiFen, mTvGift, mTvMember;

    private LinearLayout mLLMeFragmentTop, mLlMyPhotoAlbum, mLlMyState, mLlMyGuest, mLlEmotion, mLlMoreSetting;

    @BindViews({R.id.photo1, R.id.photo2, R.id.photo3, R.id.photo4, R.id.photo5, R.id.photo6, R.id.photo7, R.id.photo8, R.id.photo9})
    ImageView[] imageViews;

    private ImageView mMember_icon;

    private ArrayList<String> mSelectPath;

    private static final int REQUEST_IMAGE = 202;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;

    private static final int REQUEST_DATA = 101;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initData();
//    }

    private void initData() {
        APIUtil.getPhotoApi()
                .getPhoto("1", "9", null)
                .debounce(APIUtil.FILTER_TIMEOUT, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<HttpResult<List<Gallery>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull HttpResult<List<Gallery>> arrayListHttpResult) {
                        Log.d(TAG, "onNext: " + arrayListHttpResult.getState());
                        if (arrayListHttpResult.getState().equals("2000")) {
                            int size = arrayListHttpResult.getData().size();
                            for (int i = 0; i < size; i++) {
                                Picasso.with(getActivity())
                                        .load(arrayListHttpResult.getData().get(i).getImg().getThumb())
                                        .placeholder(R.drawable.mis_default_error)
                                        .tag(MultiImageSelectorFragment.TAG)
                                        .resize(238, 181)
                                        .centerCrop()
                                        .into(imageViews[i]);
                            }
                            for (int i = size; i < 9; i++) {
                                imageViews[i].setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.view_me, container, false);
        ButterKnife.bind(this, mView);
        initView(mView);
        return mView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    private void initView(View view) {

        mRivMyPhoto = (CircleImageView) view.findViewById(R.id.my_photo);
        mTvMyName = (TextView) view.findViewById(R.id.tv_name);
        mTvSignName = (TextView) view.findViewById(R.id.tv_sign_name);
        mTVJiFen = (TextView) view.findViewById(R.id.tv_Jifen);
        mTvGift = (TextView) view.findViewById(R.id.tv_gift);
        mTvMember = (TextView) view.findViewById(R.id.tv_member);
        mLlMyPhotoAlbum = (LinearLayout) view.findViewById(R.id.my_photo_album);
        mLlMyState = (LinearLayout) view.findViewById(R.id.my_state);
        mLlMyGuest = (LinearLayout) view.findViewById(R.id.my_guest);
        mLlEmotion = (LinearLayout) view.findViewById(R.id.my_emotion);
        mLlMoreSetting = (LinearLayout) view.findViewById(R.id.more_setting);
        mLLMeFragmentTop = (LinearLayout) view.findViewById(R.id.ll_me_fragment);

        mMember_icon = (ImageView) view.findViewById(R.id.member_icon);

        mRivMyPhoto.setOnClickListener(this);
        mTvMyName.setOnClickListener(this);
        mTvSignName.setOnClickListener(this);
        mTVJiFen.setOnClickListener(this);
        mLlMyPhotoAlbum.setOnClickListener(this);
        mTvGift.setOnClickListener(this);
        mLlMyState.setOnClickListener(this);
        mLlMyGuest.setOnClickListener(this);
        mLlEmotion.setOnClickListener(this);
        mLlMoreSetting.setOnClickListener(this);
        mTvMember.setOnClickListener(this);
        mMember_icon.setOnClickListener(this);
        mLLMeFragmentTop.setOnClickListener(this);
        if (MainActivity.mSelectPath.size() != 0) {
            mLLMeFragmentTop.setBackground(Drawable.createFromPath(MainActivity.mSelectPath.get(0)));
        }
        //从MyApplication 中读取数据
        Log.d(TAG, "initView: " + UserUtil.getUser().getAvatar().getThumb().getFace());
        if (UserUtil.getUser() != null) {
            User user = UserUtil.getUser();
            if (!TextUtils.isEmpty(user.getUsername())) {
                mTvMyName.setText(user.getUsername());
            }
            if (!TextUtils.isEmpty(user.getProfile())) {
                mTvSignName.setText("签名：" + user.getProfile());
            }
            if (!TextUtils.isEmpty(user.getGroup())) {
                if (user.getGroup().equals("2")) {
                    mTvMember.setText("会员");
                    mMember_icon.setImageResource(R.drawable.huanggaun);
                }
            }
            Log.d(TAG, "initView: " + user.getAvatar().getThumb().getFace());
            if (!TextUtils.isEmpty(user.getAvatar().getThumb().getFace())) {

                Glide.with(this).load(user.getAvatar().getThumb().getFace())
                        .error(R.drawable.guanggao1)
                        .centerCrop()
                        .transform(new GlideCircleTransform(getContext()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .crossFade()
                        .into(mRivMyPhoto);
            }
        }

        initData();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        Intent intent = new Intent();
        Intent intent;
        switch (id) {
            case R.id.my_photo:
            case R.id.tv_name:
            case R.id.tv_sign_name:
//                intent.setClass(mContext, PhotoAlbumActivity.class)
                intent = new Intent(getActivity(), PersonalDataActivity.class);
                startActivityForResult(intent, REQUEST_DATA);
                break;
            case R.id.tv_Jifen:
            case R.id.tv_gift:
                intent = new Intent(getActivity(), JifenAndGiftActivity.class);
                startActivity(intent);
                break;
            case R.id.my_photo_album:
                intent = new Intent(getActivity(), PhotoAlbumActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_member:
            case R.id.member_icon:
//                intent = new Intent(getActivity(), MemberActivity.class);
//                startActivity(intent);

                break;
//            mLlMyPhotoAlbum,mLlMyState,mLlMyGuest,mLlEmotion,mLlMyGift,mLlMoreSetting
//            case R.id.my_state:
//                intent = new Intent(getActivity(), MyStateActivity.class);
//                startActivity(intent);
//                break;
            case R.id.my_guest:
                intent = new Intent(getActivity(), MyGuestActivity.class);
                startActivity(intent);
                break;
            case R.id.my_emotion:
                intent = new Intent(getActivity(), MyEmotionActivity.class);
                startActivity(intent);
                break;
            case R.id.more_setting:
                intent = new Intent(getActivity(), MoreSettingActivity.class);
//                startActivityForResult(intent, DESTROY_LOVE);
                startActivity(intent);
                break;
            case R.id.ll_me_fragment:
                String choiceMode = "single";
                pickImage(choiceMode);
                break;

        }
    }


    private void pickImage(String choiceMode) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
//                    getString(R.string.mis_permission_rationale),
//                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
//        } else {
        boolean showCamera = false;
        int maxNum = 9;

        MultiImageSelector selector = MultiImageSelector.create(getActivity());
        selector.showCamera(showCamera);
        selector.count(maxNum);
        if (choiceMode.equals("single")) {
            selector.single();
        } else {
            selector.multi();
        }
        selector.origin(mSelectPath);
        selector.start(getActivity(), REQUEST_IMAGE);
//        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String choiceMode = "multi";
                pickImage(choiceMode);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
