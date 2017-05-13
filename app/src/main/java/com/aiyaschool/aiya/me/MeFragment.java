package com.aiyaschool.aiya.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiyaschool.aiya.MyApplication;
import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.me.activity.JifenAndGiftActivity;
import com.aiyaschool.aiya.me.activity.MemberActivity;
import com.aiyaschool.aiya.me.activity.MoreSettingActivity;
import com.aiyaschool.aiya.me.activity.MyEmotionActivity;
import com.aiyaschool.aiya.me.activity.MyGiftActivity;
import com.aiyaschool.aiya.me.activity.MyGuestActivity;
import com.aiyaschool.aiya.me.activity.PersonalDataActivity;
import com.aiyaschool.aiya.me.activity.PhotoAlbumActivity2;
import com.aiyaschool.aiya.me.bean.ImagePathItem;
import com.aiyaschool.aiya.me.view.RoundImageView;
import com.aiyaschool.aiya.multi_image_selector.MultiImageSelectorFragment;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.aiyaschool.aiya.activity.main.MainActivity.DESTROY_LOVE;

/**
 * Created by wewarriors on 2017/3/16.
 */

public class MeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Context mContext;
    private RoundImageView mRivMyPhoto;
    private TextView mTvMyName, mTvSignName, mTVJiFen, mTvGift, mTvMember;
    private LinearLayout mLlMyPhotoAlbum, mLlMyState, mLlMyGuest, mLlEmotion, mLlMyGift, mLlMoreSetting;
    private ImageView imageView1, imageView2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        System.out.println(555);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(333);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.view_me, container, false);
        System.out.println(444);
        initView(mView);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<ImagePathItem> list = DataSupport.findAll(ImagePathItem.class);
        System.out.println("list.size()" + list.size());

        List<String> mList = getImagePath(list);
        System.out.println("mList.size()" + mList.size());
        if (mList.size() == 1) {
            File imageFile = new File(mList.get(0));
            Picasso.with(getActivity())
                    .load(imageFile)
                    .placeholder(R.drawable.mis_default_error)
                    .tag(MultiImageSelectorFragment.TAG)
                    .resize(238, 181)
                    .centerCrop()
                    .into(imageView1);
        } else if (mList.size() == 2) {
            File imageFile = new File(mList.get(0));
            Picasso.with(getActivity())
                    .load(imageFile)
                    .placeholder(R.drawable.mis_default_error)
                    .tag(MultiImageSelectorFragment.TAG)
                    .resize(238, 181)
                    .centerCrop()
                    .into(imageView1);
            File imageFile1 = new File(mList.get(1));
            Picasso.with(getActivity())
                    .load(imageFile1)
                    .placeholder(R.drawable.mis_default_error)
                    .tag(MultiImageSelectorFragment.TAG)
                    .resize(238, 181)
                    .centerCrop()
                    .into(imageView2);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    private void initView(View view) {
        mRivMyPhoto = (RoundImageView) view.findViewById(R.id.my_photo);
        mTvMyName = (TextView) view.findViewById(R.id.tv_name);
        mTvSignName = (TextView) view.findViewById(R.id.tv_sign_name);
        mTVJiFen = (TextView) view.findViewById(R.id.tv_Jifen);
        mTvGift = (TextView) view.findViewById(R.id.tv_gift);
        mTvMember = (TextView) view.findViewById(R.id.tv_member);
        mLlMyPhotoAlbum = (LinearLayout) view.findViewById(R.id.my_photo_albun);
        mLlMyState = (LinearLayout) view.findViewById(R.id.my_state);
        mLlMyGuest = (LinearLayout) view.findViewById(R.id.my_guest);
        mLlEmotion = (LinearLayout) view.findViewById(R.id.my_emotion);
        mLlMyGift = (LinearLayout) view.findViewById(R.id.my_gift);
        mLlMoreSetting = (LinearLayout) view.findViewById(R.id.more_setting);
        imageView1 = (ImageView) view.findViewById(R.id.photo1);
        imageView2 = (ImageView) view.findViewById(R.id.photo2);
        mRivMyPhoto.setOnClickListener(this);
        mTvMyName.setOnClickListener(this);
        mTvSignName.setOnClickListener(this);
        mTVJiFen.setOnClickListener(this);
        mLlMyPhotoAlbum.setOnClickListener(this);
        mTvGift.setOnClickListener(this);
        mLlMyState.setOnClickListener(this);
        mLlMyGuest.setOnClickListener(this);
        mLlEmotion.setOnClickListener(this);
        mLlMyGift.setOnClickListener(this);
        mLlMoreSetting.setOnClickListener(this);
        mTvMember.setOnClickListener(this);

        /**
         * temptoken : f29c1317158d76821e8d11ccd8a007a358fc9984c178e
         * username : xihuan
         * phone : 15000000000
         * AccessToken : 48f418d380c1cf2ae948732780f6a39815000000000590494641c6de
         * school : 南京大学
         * loveid : 1
         * group : 1
         * province : 陕西
         * avatar : {"normal":{"face":"http://cdn.sinacloud.net/gxwy-user/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=aTVxXyiYsr&Expires=1493558756","background":"http://cdn.sinacloud.net/gxwy-user/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=9%2F2aGPHyih&Expires=1493558756"},"thumb":{"face":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/avatar/face0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=k1IE5rKli6&Expires=1493558756","background":"http://imgx.sinacloud.net/gxwy-user/c_fill,h_224,w_224/background/beijing0.jpg?KID=sina,2nc35s5cZOQiXwCUWQm7&ssig=r1nCbOvIRL&Expires=1493558756"}}
         * profile : 亨利就坐在那里，满眼望去，都是自己二十岁的影子
         * points : 200
         * gifttickets : 0
         * imgwall : {"rows":0,"url":null}
         * usersig : eJxNjV1PgzAYRv9Lb2dcCy0LJl4AY4A6EzLddtdUeCF1ArUUlBj-uw3B6O05z8cXeno4XIui6IbWcDMpQDcIo6sZyxJaIysJ2sJXofW0CKGULLkw3NXlv3xfXvisLCMUY0wYxu4i4VNJDVxUZp4jjDHHRhY7gu5l11rhYOIRTAj*k0Y2ME-6tsF8x--9k7XF*-g5yvJom95Dr8ZT9eHs3U36GJ5fgnW5CldTUt-R0BspOQRBErP3PKthRzu-b1Rz2ubFdFmnO*qF3ZCwWMTDJi*OEZEZHt6gqm-R9w-MC1Zg
         * logintoken : 03b0761c6c915924907bb9ebabdebad3160b4f30-1f89-11e7-967b-ebe175c2a0263b946bfe-d0fb-4357-aa30-42cdaef6dc50
         */

        //从MyApplication 中读取数据
        if (MyApplication.getUser() != null) {
            User user = MyApplication.getUser();
            if (TextUtils.isEmpty(user.getUsername())) {
                mTvMyName.setText(user.getUsername());
            }
            if (TextUtils.isEmpty(user.getProfile())) {
                mTvSignName.setText(user.getProfile());
            }
            if (TextUtils.isEmpty(user.getGiftTickets())) {

            }
            mTVJiFen.setText(user.getGiftTickets());
            System.out.println(user.getUsername());
        } else {
            //如果没有数据的话，可以从sharepreference中读取
        }

    }

    private List<String> getImagePath(List<ImagePathItem> list) {
        List<String> sList = new ArrayList<>();
        int m = 0;
        String s = R.drawable.uploadpic_226x226 + "";
        for (int i = 0; i < list.size(); i++) {
            List<String> nList = list.get(i).getImagePath();
            for (int j = 0; j < nList.size(); j++) {
                if (!s.equals(nList.get(j))) {
                    sList.add(nList.get(j));
                }
                if (sList.size() == 2) {
                    m = 1;
                    break;
                }
            }
            if (m == 1) {
                break;
            }
        }
        return sList;
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
                startActivity(intent);
                break;
            case R.id.tv_Jifen:
            case R.id.tv_gift:
                intent = new Intent(getActivity(), JifenAndGiftActivity.class);
                startActivity(intent);
                break;
            case R.id.my_photo_albun:
                intent = new Intent(getActivity(), PhotoAlbumActivity2.class);
                startActivityForResult(intent,1);

                break;
            case R.id.tv_member:
                intent = new Intent(getActivity(), MemberActivity.class);
                startActivity(intent);

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
            case R.id.my_gift:
                intent = new Intent(getActivity(), MyGiftActivity.class);
                startActivity(intent);
                break;
            case R.id.more_setting:
                intent = new Intent(getActivity(), MoreSettingActivity.class);
                startActivityForResult(intent, DESTROY_LOVE);
                break;

        }
    }
}
