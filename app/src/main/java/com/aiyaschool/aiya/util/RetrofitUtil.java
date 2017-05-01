package com.aiyaschool.aiya.util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by EGOISTK21 on 2017/4/27.
 */

public class RetrofitUtil {

    private final static String _ROOT_ = "https://lovefor7days.applinzi.com/";
    private static Retrofit sRetrofit;
    private static String phone;
    private static String loginToken;

    interface VerificationInitService {
        @Headers("accessToken:false")
        @POST("Home/GET/verificationInit")
        @FormUrlEncoded
        Call<ResponseBody> loadUser(@Field("phone") String phone,
                                    @Field("verification") String verification);
    }

    interface InitServise {
        @Headers("accessToken:false")
        @POST("Home/GET/init")
        @FormUrlEncoded
        Call<ResponseBody> loadUser(@Field("phone") String phone,
                                    @Field("LoginToken") String logintoken);
    }

    public static void sign(String phone, String verification) {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().baseUrl(_ROOT_).build();
        }
        Call<ResponseBody> call = sRetrofit.create(VerificationInitService.class).loadUser(phone, verification);

    }

    public static void init() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().baseUrl(_ROOT_).build();
        }
        Call<ResponseBody> call = sRetrofit.create(InitServise.class).loadUser(phone, loginToken);

    }

}
