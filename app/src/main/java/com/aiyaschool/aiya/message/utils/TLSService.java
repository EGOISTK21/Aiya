package com.aiyaschool.aiya.message.utils;

import android.content.Context;

import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSUserInfo;

/**
 * Created by ShootHzj on 2016/10/31.
 */

public class TLSService {

    public static TLSLoginHelper loginHelper;
    public static TLSAccountHelper accountHelper;

    private static int lastErrno = -1;

    private static TLSService tlsService = null;

    private TLSService(){}
    public static TLSService getInstance() {
        if (tlsService == null) {
            tlsService = new TLSService();
        }
        return tlsService;
    }

    public static void setLastErrno(int errno) {
        lastErrno = errno;
    }

    public static int getLastErrno() {
        return lastErrno;
    }

    /**
     * @function: 初始化TLS SDK, 必须在使用TLS SDK相关服务之前调用
     * @param context: 关联的activity
     * */
    public void initTlsSdk(Context context) {

        loginHelper = TLSLoginHelper.getInstance().init(context,
                TLSConfiguration.SDK_APPID, TLSConfiguration.ACCOUNT_TYPE, TLSConfiguration.APP_VERSION);
        loginHelper.setTimeOut(TLSConfiguration.TIMEOUT);
        loginHelper.setLocalId(TLSConfiguration.LANGUAGE_CODE);

        accountHelper = TLSAccountHelper.getInstance().init(context,
                TLSConfiguration.SDK_APPID, TLSConfiguration.ACCOUNT_TYPE, TLSConfiguration.APP_VERSION);
//        accountHelper = TLSAccountHelper.getInstance().init(context,
//                1400017104, 8277, TLSConfiguration.APP_VERSION);
        accountHelper.setCountry(Integer.parseInt(TLSConfiguration.COUNTRY_CODE)); // 存储注册时所在国家，只须在初始化时调用一次
        accountHelper.setTimeOut(TLSConfiguration.TIMEOUT);
        accountHelper.setLocalId(TLSConfiguration.LANGUAGE_CODE);
        accountHelper.setTestHost("", true);                 // 走sso
        String xx = TLSConfiguration.SDK_APPID+"";
        String yy = TLSConfiguration.ACCOUNT_TYPE+"";
        String zz = TLSConfiguration.COUNTRY_CODE;
        String yyu = TLSConfiguration.LANGUAGE_CODE+"";
        String gg = "1";
    }

    public String getLastUserIdentifier() {
        TLSUserInfo userInfo = getLastUserInfo();
        if (userInfo != null)
            return userInfo.identifier;
        else
            return null;
    }

    public TLSUserInfo getLastUserInfo() {
        return loginHelper.getLastUserInfo();
    }

    public boolean needLogin(String identifier) {
        if (identifier == null)
            return true;
        return loginHelper.needLogin(identifier);
    }

}