package com.aiyaschool.aiya.util;

import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.User;

import java.util.List;
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
 * 基于Retrofit2的网络请求工具类，通过getXXXApi返回对应api请求接口
 * Created by EGOISTK21 on 2017/4/24.
 */

public class APIUtil {

    public static final int FILTER_TIMEOUT = 5;
    private static final int TIMEOUT = 5;
    private static final String ROOT = "https://lovefor7days.applinzi.com/";
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
    private static Converter.Factory sGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory sRxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static Retrofit sRetrofit;

    private APIUtil() {

    }

    /**
     * 用户短信上线(登录/注册统一接口) [GS1]
     * 'phone':用户手机号，
     * 'verification': 短信验证码
     * 为方便测试,短信验证码为1234时将无误通过
     */
    public interface VerificationInitApi {
        @Headers("accessToken:false")
        @POST("Home/GET/verificationInit")
        @FormUrlEncoded
        Observable<HttpResult<User>> loadUser(@Field("phone") String phone,
                                              @Field("verification") String verification);
    }

    /**
     * App已登录再次启动时获取AccessToken [GS2]
     * 'phone':用户手机号
     * 'logintoken':对应令牌
     */
    public interface InitApi {
        @Headers("accessToken:false")
        @POST("Home/GET/init")
        @FormUrlEncoded
        Observable<HttpResult<User>> loadUser(@Field("phone") String phone,
                                              @Field("logintoken") String loginToken);
    }

    /**
     * 用户注册后获取usersig与logintoken [GS3]
     * 此接口应该在app得到GS1接口 state = 5130 后立即调用
     * 'phone':用户电话号码
     * 'temptoken':在GS1接口获得的令牌
     */
    public interface TokenApi {
        @Headers("accessToken:false")
        @POST("GET/token")
        @FormUrlEncoded
        Observable<HttpResult<User>> loadUser(@Field("phone") String phone,
                                              @Field("temptoken") String tempToken);
    }

    /**
     * 新注册用户完善信息接口(起初始化作用) [GS4]
     * 'logintoken':从接口GS3获取的令牌
     * 'phone':用户手机号,
     * 'username':用户名,
     * 'gender':性别，1-男 2-女,
     * 'school':学校,
     * 'age':年龄,
     * ['height']:身高,
     * ['constellation']:星座,
     * ['hometown']:家乡,
     * ['hobby']:爱好
     * ['avatar']:头像 URI (若头像上传成功，则填对应的URI)
     */
    public interface FirstInitApi {
        @Headers("accessToken:false")
        @POST("Home/GET/firstInit")
        @FormUrlEncoded
        Observable<HttpResult<User>> submitUser(@Field("logintoken") String loginToken,
                                                @Field("phone") String phone,
                                                @Field("username") String username,
                                                @Field("gender") String gender,
                                                @Field("school") String school,
                                                @Field("age") String age,
                                                @Field("height") String height,
                                                @Field("constellation") String constellation,
                                                @Field("hometown") String hometown,
                                                @Field("hobby") String hobby,
                                                @Field("avatar") String avatar);
    }

    /**
     * ['keyword']:   学校关键字
     * ['province']: 学校所在省份
     * 传入keyword参数时 province将被忽略(搜索逻辑)
     * 单独传入province参数时为默认候选列表逻辑
     */
    public interface SearchSchoolApi {
        @Headers("accessToken:false")
        @POST("Community/GET/searchSchool")
        @FormUrlEncoded
        Observable<HttpResult<List<String>>> loadSchoolData(@Field("keyword") String keyword,
                                                            @Field("province") String hometown);
    }

    public static VerificationInitApi getVerificationInitApi() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                    .baseUrl(ROOT)
                    .addConverterFactory(sGsonConverterFactory)
                    .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                    .build();
        }
        return sRetrofit.create(VerificationInitApi.class);
    }

    public static InitApi getInitApi() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                    .baseUrl(ROOT)
                    .addConverterFactory(sGsonConverterFactory)
                    .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                    .build();
        }
        return sRetrofit.create(InitApi.class);
    }

    public static TokenApi getTokenApi() {
        return new Retrofit
                .Builder()
                .client(sOkHttpClient)
                .baseUrl("https://gxwylovesig.applinzi.com/")
                .addConverterFactory(sGsonConverterFactory)
                .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                .build().create(TokenApi.class);
    }

    public static FirstInitApi getFirstInitApi() {
        return sRetrofit.create(FirstInitApi.class);
    }

    public static SearchSchoolApi getSearchSchoolApi() {
        return sRetrofit.create(SearchSchoolApi.class);
    }

    //

}
