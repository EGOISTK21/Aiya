package com.aiyaschool.aiya.love.matched;

import android.util.Log;

import com.aiyaschool.aiya.R;
import com.aiyaschool.aiya.base.BaseFragment;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.love.matched.main.MatchedFragment;
import com.aiyaschool.aiya.util.APIUtil;
import com.aiyaschool.aiya.util.UserUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by EGOISTK21 on 2017/4/9.
 */

public class MatchedContainerFragment extends BaseFragment {

    private static final String TAG = "MatchedContainer";

    public static MatchedContainerFragment newInstance() {
        return new MatchedContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_love_container;
    }

    @Override
    protected void initView() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container_love, MatchedFragment.newInstance()).commit();
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
                        Log.i(TAG, "onError: initView");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: initView");
                    }
                });
    }
}
