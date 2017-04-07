package xyz.egoistk21.aiya.util;

import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.egoistk21.aiya.bean.User;


/**
 * Created by EGOISTK21 on 2017/3/26.
 */

public class OkHttpUtil {

    final private static String ROOT = "http://lovefor7days.applinzi.com";
    final private static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.MILLISECONDS)
            .readTimeout(500, TimeUnit.MILLISECONDS)
            .writeTimeout(500, TimeUnit.MILLISECONDS)
            .build();
    private static User user;

    public static void initUser(String username) {
        user = new User(username);
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("username", user.getUsername());
                try {
                    Response response = OK_HTTP_CLIENT.newCall(new Request
                            .Builder()
                            .url("https://gxwylovesig.applinzi.com/GET/usersig")
                            .header("AccessToken", "False")
                            .post((builder.build()))
                            .build())
                            .execute();
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        user.setUsersig(jsonObject.getString("usersig"));
                        builder = new FormBody.Builder();
                        builder.add("username", user.getUsername());
                        builder.add("usersig", user.getUsersig());
                        response = OK_HTTP_CLIENT.newCall(new Request
                                .Builder()
                                .url("https://gxwylovesig.applinzi.com/GET/alltoken")
                                .header("AccessToken", "False")
                                .post((builder.build()))
                                .build())
                                .execute();
                        if (response.isSuccessful()) {
                            jsonObject = new JSONObject(response.body().string());
                            user.updateUsersig(jsonObject.getString("usersig"));
                            user.setLoginToken(jsonObject.getString("LoginToken"));
                            builder = new FormBody.Builder();
                            builder.add("username", user.getUsername());
                            builder.add("LoginToken", user.getLoginToken());
                            response = OK_HTTP_CLIENT.newCall(new Request
                                    .Builder()
                                    .url("https://lovefor7days.applinzi.com/Home/GET/init")
                                    .header("AccessToken", "False")
                                    .post((builder.build()))
                                    .build())
                                    .execute();
                            if (response.isSuccessful()) {
                                jsonObject = new JSONObject(response.body().string());
                                user.setAccesstoken(jsonObject.getString("AccessToken"));
                                Log.i("3", "Success");
                            } else {
                                Log.i("3", "Failure");
                            }
                        } else {
                            Log.i("2", "Failure");
                        }
                    } else {
                        Log.i("1", "Failure");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void post(String url, FormBody.Builder builder, Callback callback) {
        OK_HTTP_CLIENT.newCall(new Request
                .Builder()
                .url(ROOT + url)
                .header("AccessToken", user.getAccesstoken())
                .post(builder.build())
                .build())
                .enqueue(callback);
    }

}
