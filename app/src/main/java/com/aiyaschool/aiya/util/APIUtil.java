package com.aiyaschool.aiya.util;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by EGOISTK21 on 2017/4/24.
 */

public class APIUtil {

    private static final int TIMEOUT = 5;
    private static final String ROOT = "https://lovefor7days.applinzi.com/";
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
    private static Converter.Factory sGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory sRxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static Retrofit sRetrofit;
    private static VerificationInitApi sVerificationInitApi;
    private static InitApi sInitApi;

    private APIUtil() {

    }

    public interface VerificationInitApi {
        @Headers("accessToken:false")
        @POST("Home/GET/verificationInit")
        @FormUrlEncoded
        Observable<HttpResult<User>> loadUser(@Field("phone") String phone,
                                              @Field("verification") String verification);
    }

    public interface InitApi {
        @Headers("accessToken:false")
        @POST("Home/GET/init")
        @FormUrlEncoded
        Observable<HttpResult<User>> loadUser(@Field("phone") String phone,
                                              @Field("LoginToken") String loginToken);
    }

    public static VerificationInitApi getVerificationInitApi() {
        if (sVerificationInitApi == null) {
            if (sRetrofit == null) {
                sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                        .baseUrl(ROOT)
                        .addConverterFactory(sGsonConverterFactory)
                        .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                        .build();
            }
            sVerificationInitApi = sRetrofit.create(VerificationInitApi.class);
        }
        return sVerificationInitApi;
    }

    public static InitApi getInitApi() {
        if (sInitApi == null) {
            if (sRetrofit == null) {
                sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                        .baseUrl(ROOT)
                        .addConverterFactory(sGsonConverterFactory)
                        .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                        .build();
            }
            sInitApi = sRetrofit.create(InitApi.class);
        }
        return sInitApi;
    }

}
