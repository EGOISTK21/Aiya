package com.aiyaschool.aiya.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * jjjjjlllllllssss
 * Created by EGOISTK21 on 2017/3/26.
 */

public class OkHttpUtil {

    final private static String ROOT = "http://lovefor7days.applinzi.com";
    final private static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.MILLISECONDS)
            .readTimeout(500, TimeUnit.MILLISECONDS)
            .writeTimeout(500, TimeUnit.MILLISECONDS)
            .build();

    private OkHttpUtil() {
    }

    public static boolean isNetworkReachable(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static Response initUser(final String phone, final String verification) {
        Response response = null;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", phone);
        builder.add("verification", verification);
        try {
            response = OK_HTTP_CLIENT.newCall(new Request.Builder()
                    .url("https://lovefor7days.applinzi.com/Home/GET/verificationInit")
                    .header("accesstoken", "false")
                    .post(builder.build()).build())
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void post(String url, FormBody.Builder builder, Callback callback) {
//        OK_HTTP_CLIENT.newCall(new Request
//                .Builder()
//                .url(ROOT + url)
//                .header("AccessToken", user == null ? "False" : user.getAccessToken())
//                .post(builder.build())
//                .build())
//                .enqueue(callback);
    }

}
