package xyz.egoistk.aiya.love.unmatched.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;
import xyz.egoistk.aiya.love.unmatched.view.UnmatchedContract;
import xyz.egoistk.aiya.util.OkHttpUtil;

/**
 * Created by EGOISTK on 2017/3/23.
 */

public class UnmatchedModel {

    private UnmatchedContract.Presenter presenter;

    public UnmatchedModel(UnmatchedContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void loadSchoolMajorData(final UnmatchedContract.Presenter.OnDataListener onDataListener) {
        OkHttpUtil.post("/Community/GET/searchSchool",
                new FormBody.Builder().add("keyword", ""),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            onDataListener.onSuccess(new ArrayList<>(Arrays.asList(
                                    (new JSONObject(response.body().string())).getString("data").split(","))));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
