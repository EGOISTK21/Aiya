package com.aiyaschool.aiya.util;

import com.aiyaschool.aiya.bean.EmotionRecordBean;
import com.aiyaschool.aiya.bean.HttpResult;
import com.aiyaschool.aiya.bean.OuInfo;

import com.aiyaschool.aiya.bean.Task;
import com.aiyaschool.aiya.bean.User;
import com.aiyaschool.aiya.love.matched.today.Intimacy;

import com.aiyaschool.aiya.bean.UploadUrl;
import com.aiyaschool.aiya.me.bean.MyAvatar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

/**
 * 基于Retrofit2的网络请求工具类，通过getXXXApi返回对应api请求接口
 * Created by EGOISTK21 on 2017/4/24.
 */

public class APIUtil {

    public static final int FILTER_TIMEOUT = 5;
    private static final int TIMEOUT = 5;
    private static final String ROOT = "https://lovefor7days.applinzi.com/";
    private static final String IMG_ROOT = "http://up.sinacloud.net/";
    private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
    private static Converter.Factory sGsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory sRxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static Retrofit sRetrofit, sIMGRetrofit;

    private APIUtil() {

    }

    static void addAccessToken() {
        sOkHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .addHeader("accesstoken", UserUtil.getUser().getAccessToken()).build());
            }
        }).connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
        sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                .baseUrl(ROOT)
                .addConverterFactory(sGsonConverterFactory)
                .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                .build();
    }

    static void removeAccessToken() {
        sOkHttpClient = new OkHttpClient.Builder().connectTimeout(TIMEOUT, TimeUnit.SECONDS).build();
        sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                .baseUrl(ROOT)
                .addConverterFactory(sGsonConverterFactory)
                .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                .build();
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
                                              @Field("logintoken") String loginToken,
                                              @Field("sign") String flag);
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

    public interface IMGApi {
        @Headers("Content-Type:image/jpeg")
        @PUT()
        Observable<ResponseBody> submitIMG(@Url String upurl, @Body RequestBody img);
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
     * ['character']:性格,
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
                                                @Field("character") String character,
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

    /**
     * 社区-获取名片[CG103]
     * 参数：
     * 'userid':用户ID
     */

    public interface PersonApi {
        @POST("Community/GET/person")
        @FormUrlEncoded
        Observable<HttpResult<User>> loadOtherDetail(@Field("userid") String id);
    }

    /**
     * 撩TA[LPo101]
     * 参数：
     * 'userid':被撩用户ID
     */

    public interface TouchApi {
        @POST("Love/POST/task")
        @FormUrlEncoded
        Observable<HttpResult> touch(@Field("userid") String id);
    }

    /**
     * #匹配条件
     * ['minHeight']:身高下限，默认0/cm
     * ['maxHeight']:身高上限，默认210/cm
     * ['age']:年龄
     * #下四个条件模糊匹配
     * ['major']:专业
     * ['school']:学校
     * ['province']:家乡-省份
     * ['constellation']:星座
     * ['page']:同前
     * ['lines']:同前
     */

    public interface MatchingApi {
        @POST("Love/GET/matching")
        @FormUrlEncoded
        Observable<HttpResult<ArrayList<User>>> startConditionMatch(@Field("minHeight") String minHeight,
                                                                    @Field("maxHeight") String maxHeight,
                                                                    @Field("age") String maxAge,
                                                                    @Field("school") String school,
                                                                    @Field("character") String character,
                                                                    @Field("constellation") String constellation,
                                                                    @Field("page") String page,
                                                                    @Field("line") String line);
    }

    public interface LoverInfoApi {
        @POST("Love/GET/loverinfo")
        Observable<HttpResult<User>> getLoverInfo();
    }

    public interface IntimacyApi {
        @POST("Love/GET/intimacy")
        @FormUrlEncoded
        Observable<Intimacy> getIntimacy(@Field("loveid") String loveid);
    }

    public interface CtodayTaskApi {
        @POST("Love/GET/CtodayTask")
        @FormUrlEncoded
        Observable<HttpResult<Task>> getToadyTask(@Field("period") String period);
    }

    /**
     * #以下所有参数均为可选参数,但不可一个参数都不传
     * ['username']:用户名
     * ['age']:年龄,
     * ['height']:身高,
     * ['birthday']:生日 //格式 xxxx-xx-xx
     * ['constellation']:星座
     * ['school']:学校
     * ['enteryear']:入学年份 //格式 xxxx
     * ['major']:专业
     * ['avatar']:头像图URI
     * ['profile']:个人说明
     * ['character']:性格
     */
    public interface UpdateUserApi {
        @POST("Me/PUT/userinfo")
        @FormUrlEncoded
        Observable<HttpResult> startUpdateUser(@Field("height") String height);
    }

    public interface GetMeIndex {
        @POST("Me/GET/meIndex")
        @FormUrlEncoded
        Observable<HttpResult<User>> startGetMeIndex(@Field("demand") String demand);
    }

    public interface GetMeIndexAvatar {
        @POST("Me/GET/meIndex")
        @FormUrlEncoded
        Observable<HttpResult<MyAvatar>> startGetMeIndexAvatar(@Field("demand") String demand);
    }

    public interface GetMeIndexAvatar1 {
        @POST("Me/GET/meIndex")
        @FormUrlEncoded
        Observable<HttpResult> startGetMeIndexAvatar1(@Field("demand") String demand);
    }
    //更新用户信息
    public interface UpdateUserDataApi {
        @POST("Me/PUT/userinfo")
        @FormUrlEncoded
        Observable<HttpResult> startUpdateUserData(@FieldMap Map<String, String> map);
    }

    //获取上传头像图片地址
    public interface GetAvatarUploadUrlApi {
        @POST("Me/GET/genAvatarUploadUrl")
        Observable<HttpResult<UploadUrl>> startGetAvatarUploadUrl();
    }

    public interface FateMatchApi {
        @POST("Love/GET/fateMatching")
        Observable<HttpResult<User>> startFateMatch();
    }

    public interface DestroyLoveApi {
        @POST("Me/PUT/destroyLove")
        @FormUrlEncoded
        Observable<HttpResult> destroyLove(@Field("loveid") String loveId);
    }

    public interface GetGuestRecordApi {
        @POST("Me/GET/touchPeople")
        @FormUrlEncoded
        Observable<HttpResult<ArrayList<OuInfo>>> startGetGuestRecord(@Field("page") String page, @Field("lines") String lines);
    }

    public interface GetEmotionRecordApi {
        @POST("Me/GET/loveHistory")
        @FormUrlEncoded
        Observable<HttpResult<ArrayList<EmotionRecordBean>>> startGetEmotionRecord(@Field("sex") String sex,
                                                                                   @Field("page") String page,
                                                                                   @Field("lines") String lines);
    }



//    public interface GetEmotionRecordApi{
//        @POST("Me/GET/loveHistory")
//        @FormUrlEncoded
//        Observable<HttpResult> startGetEmotionRecord(@Field("sex") String sex,
//                                                                                   @Field("page") String page,
//                                                                                   @Field("lines") String lines);
//    }

    public interface FateSwitchApi {
        @POST("Me/PUT/fateSwitch")
        @FormUrlEncoded
        Observable<HttpResult> setFateSwitch(@Field("fateswitch") String fateSwitch);
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
                .build()
                .create(TokenApi.class);
    }

    public static IMGApi getIMGApi() {
        if (sIMGRetrofit == null) {
            sIMGRetrofit = new Retrofit
                    .Builder()
                    .client(new OkHttpClient())
                    .addCallAdapterFactory(sRxJavaCallAdapterFactory)
                    .baseUrl(IMG_ROOT)
                    .build();
        }
        return sIMGRetrofit.create(IMGApi.class);
    }

    public static FirstInitApi getFirstInitApi() {
        return sRetrofit.create(FirstInitApi.class);
    }

    public static SearchSchoolApi getSearchSchoolApi() {
        return sRetrofit.create(SearchSchoolApi.class);
    }

    public static PersonApi getPersonApi() {
        return sRetrofit.create(PersonApi.class);
    }

    public static TouchApi getTouchApi() {
        return sRetrofit.create(TouchApi.class);
    }

    public static MatchingApi getMatchingApi() {
        return sRetrofit.create(MatchingApi.class);
    }

    public static LoverInfoApi getLoverInfoApi() {
        return sRetrofit.create(LoverInfoApi.class);
    }

    public static IntimacyApi getIntimacyApi() {
        return sRetrofit.create(IntimacyApi.class);
    }

    public static CtodayTaskApi getCtodayApi() {
        return sRetrofit.create(CtodayTaskApi.class);
    }

    public static UpdateUserApi getUpdateUserApi() {
        return sRetrofit.create(UpdateUserApi.class);
    }

    public static FateMatchApi getFateMatchingApi() {
        return sRetrofit.create(FateMatchApi.class);
    }

    public static DestroyLoveApi getDestroyLoveApi() {
        return sRetrofit.create(DestroyLoveApi.class);
    }

    public static UpdateUserDataApi getUpdateUserDataApi() {
        return sRetrofit.create(UpdateUserDataApi.class);
    }

    public static GetGuestRecordApi getGuestRecordApi() {
        return sRetrofit.create(GetGuestRecordApi.class);
    }

    public static GetEmotionRecordApi getEmotionRecordApi() {
        return sRetrofit.create(GetEmotionRecordApi.class);
    }

    public static FateSwitchApi getFateSwitchApi() {
        return sRetrofit.create(FateSwitchApi.class);
    }

    public static GetMeIndex getMeIndexApi() {
        return sRetrofit.create(GetMeIndex.class);
    }

    public static GetAvatarUploadUrlApi getAvatarUploadUrlApi() {
        return sRetrofit.create(GetAvatarUploadUrlApi.class);
    }

    public static GetMeIndexAvatar getMeIndexAvatarApi() {
        return sRetrofit.create(GetMeIndexAvatar.class);
    }

    public static GetMeIndexAvatar1 getMeIndexAvatar1() {
        return sRetrofit.create(GetMeIndexAvatar1.class);
    }


}
