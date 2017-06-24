package com.aiyaschool.aiya.me;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.nereo.multi_image_selector.MultiImageSelectorFragment;

/**
 * Created by wewarriors on 2017/3/16.
 */

public class MeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private final static String TAG = "MeFragment";
    private Context mContext;
    private CircleImageView mRivMyPhoto;
    private TextView mTvMyName, mTvSignName, mTVJiFen, mTvGift, mTvMember;
    private LinearLayout mLlMyPhotoAlbum, mLlMyState, mLlMyGuest, mLlEmotion, mLlMoreSetting;
    private ImageView imageView1, imageView2;
    private ImageView mMember_icon;

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
                .getPhoto("1", "2", null)
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
                            if (size == 1) {
                                Picasso.with(getActivity())
                                        .load(arrayListHttpResult.getData().get(0).getImg().getThumb())
                                        .placeholder(R.drawable.mis_default_error)
                                        .tag(MultiImageSelectorFragment.TAG)
                                        .resize(238, 181)
                                        .centerCrop()
                                        .into(imageView1);
                            } else if (size == 2) {
                                Picasso.with(getActivity())
                                        .load(arrayListHttpResult.getData().get(0).getImg().getThumb())
                                        .placeholder(R.drawable.mis_default_error)
                                        .tag(MultiImageSelectorFragment.TAG)
                                        .resize(238, 181)
                                        .centerCrop()
                                        .into(imageView1);

                                Picasso.with(getActivity())
                                        .load(arrayListHttpResult.getData().get(1).getImg().getThumb())
                                        .placeholder(R.drawable.mis_default_error)
                                        .tag(MultiImageSelectorFragment.TAG)
                                        .resize(238, 181)
                                        .centerCrop()
                                        .into(imageView2);
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
        initView(mView);
        return mView;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        List<ImagePathItem> list = DataSupport.findAll(ImagePathItem.class);
//        System.out.println("list.size()" + list.size());
//
//        List<String> mList = getImagePath(list);
//        System.out.println("mList.size()" + mList.size());
//        if (mList.size() == 1) {
//            File imageFile = new File(mList.get(0));
//            Picasso.with(getActivity())
//                    .load(imageFile)
//                    .placeholder(R.drawable.mis_default_error)
//                    .tag(MultiImageSelectorFragment.TAG)
//                    .resize(238, 181)
//                    .centerCrop()
//                    .into(imageView1);
//        } else if (mList.size() == 2) {
//            File imageFile = new File(mList.get(0));
//            Picasso.with(getActivity())
//                    .load(imageFile)
//                    .placeholder(R.drawable.mis_default_error)
//                    .tag(MultiImageSelectorFragment.TAG)
//                    .resize(238, 181)
//                    .centerCrop()
//                    .into(imageView1);
//            File imageFile1 = new File(mList.get(1));
//            Picasso.with(getActivity())
//                    .load(imageFile1)
//                    .placeholder(R.drawable.mis_default_error)
//                    .tag(MultiImageSelectorFragment.TAG)
//                    .resize(238, 181)
//                    .centerCrop()
//                    .into(imageView2);
//        }
//
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    private void initView(View view) {
        mRivMyPhoto = view.findViewById(R.id.my_photo);
        mTvMyName = view.findViewById(R.id.tv_name);
        mTvSignName = view.findViewById(R.id.tv_sign_name);
        mTVJiFen = view.findViewById(R.id.tv_Jifen);
        mTvGift = view.findViewById(R.id.tv_gift);
        mTvMember = view.findViewById(R.id.tv_member);
        mLlMyPhotoAlbum = view.findViewById(R.id.my_photo_albun);
        mLlMyState = view.findViewById(R.id.my_state);
        mLlMyGuest = view.findViewById(R.id.my_guest);
        mLlEmotion = view.findViewById(R.id.my_emotion);
        mLlMoreSetting = view.findViewById(R.id.more_setting);
        imageView1 = view.findViewById(R.id.photo1);
        imageView2 = view.findViewById(R.id.photo2);
        mMember_icon = view.findViewById(R.id.member_icon);
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

//    private List<String> getImagePath(List<ImagePathItem> list) {
//        List<String> sList = new ArrayList<>();
//        int m = 0;
//        String s = R.drawable.uploadpic_226x226 + "";
//        for (int i = 0; i < list.size(); i++) {
//            List<String> nList = list.get(i).getImagePath();
//            for (int j = 0; j < nList.size(); j++) {
//                if (!s.equals(nList.get(j))) {
//                    sList.add(nList.get(j));
//                }
//                if (sList.size() == 2) {
//                    m = 1;
//                    break;
//                }
//            }
//            if (m == 1) {
//                break;
//            }
//        }
//        return sList;
//    }

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
            case R.id.my_photo_albun:
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

        }
    }
}
