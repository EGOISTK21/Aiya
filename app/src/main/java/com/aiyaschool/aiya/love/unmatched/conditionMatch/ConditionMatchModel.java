package com.aiyaschool.aiya.love.unmatched.conditionMatch;

import com.aiyaschool.aiya.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by EGOISTK21 on 2017/3/23.
 */

class ConditionMatchModel implements ConditionMatchContract.Model {

    @Override
    public boolean getIsContactShield() {
        return true;
    }

    @Override
    public void commitIsContactShield(boolean isContactShield) {

    }

    @Override
    public void getSchoolData(final ConditionMatchContract.Presenter.OnServerReachableListener listener) {
        OkHttpUtil.post("/Community/GET/searchSchool",
                new FormBody.Builder().add("keyword", ""),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        listener.onFailure();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        List<String> data = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONObject(response.body().string()).getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                data.add(jsonArray.getString(i));
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                        listener.onSuccess(data);
                    }
                });
    }
}
