package xyz.egoistk21.aiya.love.unmatched.bean;

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
import xyz.egoistk21.aiya.love.unmatched.view.UnmatchedContract;
import xyz.egoistk21.aiya.util.OkHttpUtil;

/**
 * Created by EGOISTK on 2017/3/23.
 */

public class UnmatchedModel {

    public void getSchoolMajorData(final UnmatchedContract.Presenter.OnDataListener onDataListener) {
        OkHttpUtil.post("/Community/GET/searchSchool",
                new FormBody.Builder().add("keyword", ""),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onDataListener.onFailure();
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
                        onDataListener.onSuccess(data);
                    }
                });
    }
}
