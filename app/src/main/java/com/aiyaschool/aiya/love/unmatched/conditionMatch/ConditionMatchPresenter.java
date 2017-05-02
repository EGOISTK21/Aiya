package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import android.content.Context;
import android.os.Handler;

/**
 * Created by EGOISTK21 on 2017/3/17.
 */

class ConditionMatchPresenter implements ConditionMatchContract.Presenter {

    private Context context;
    private ConditionMatchContract.View view;
    private ConditionMatchModel model;
    private Handler handler;

    ConditionMatchPresenter(Context context, ConditionMatchContract.View view) {
        this.context = context;
        attachView(view);
        model = new ConditionMatchModel();
        handler = new Handler();
    }

    @Override
    public void attachView(ConditionMatchContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void initIsContactShield() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.setIsContactShield(model.getIsContactShield());
                }
            }
        });
    }

    @Override
    public void commitIsContactShield(boolean isContactShield) {
        model.commitIsContactShield(isContactShield);
    }

    @Override
    public void loadSchoolData() {
//        if (OkHttpUtil.isNetworkReachable(context)) {
//            model.getSchoolData(new OnServerReachableListener() {
//                @Override
//                public void onFailure() {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            view.toastNetworkError();
//                        }
//                    });
//                }
//
//                @Override
//                public void onSuccess(final List<String> data) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (data.isEmpty()) {
//                                data.add("没有什么大学");
//                                view.toastNetworkError();
//                            }
//                            view.setSchoolData(data);
//                        }
//                    });
//                }
//            });
//        } else {
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    view.toastNetworkError();
//                    view.setSchoolData(new ArrayList<>(Arrays.asList("没有什么大学")));
//                }
//            });
//        }
    }
}
