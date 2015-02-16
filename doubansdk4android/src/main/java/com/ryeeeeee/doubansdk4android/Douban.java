/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Ryeeeeee
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ryeeeeee.doubansdk4android;

import android.content.Context;

import com.ryeeeeee.doubansdk4android.auth.IAuthListener;
import com.ryeeeeee.doubansdk4android.auth.oauth.AccessTokenResponse;
import com.ryeeeeee.doubansdk4android.auth.oauth.OAuth;
import com.ryeeeeee.doubansdk4android.util.JsonUtil;
import com.ryeeeeee.doubansdk4android.util.LogUtil;
import com.ryeeeeee.doubansdk4android.util.PreferenceUtil;

/**
 * @author Ryeeeeee
 * @since 2015-01-23
 */
public class Douban {

    private final static String TAG = "Douban";


    /** 应用的 API Key */
    private static String sApiKey;
    /** 应用的 Secret */
    private static String sSecret;
    /** 应用的回调接口 */
    private static String sRedirectURI;
    /** 应用的访问权限 */
    private static String sScope;
    /** 应用的上下文环境 */
    private static Context sContext;
    /** Douban SDK 是否初始化 */
    private static boolean sIsInited = false;


    /**
     * Douban SDK 初始化接口，在调用其他接口之前，必须确保初始化成功
     *
     * @param context
     * @param apiKey
     * @param secret
     * @param redirectURI
     * @return
     */
    public static boolean init(Context context, String apiKey, String secret, String redirectURI) {
        return init(context, apiKey, secret, redirectURI, "");
    }

    /**
     *  Douban SDK 初始化接口，在调用其他接口之前，必须确保初始化成功
     * @param apiKey
     * @param secret
     * @param redirectURI
     * @return
     */
    public static boolean init(Context context, String apiKey, String secret, String redirectURI,
                               String scope) {
        if (isIsInited()) {
            LogUtil.w(TAG, "Douban has already inited");
            return false;
        }

        sContext = context;
        sApiKey = apiKey;
        sSecret = secret;
        sRedirectURI = redirectURI;
        sScope = scope;

        sIsInited = true;
        return true;
    }

    /**
     * 进行 OAuth 认证，首先先判断本地是否有缓存的认证信息
     * 如果存在并且没有过期，则复用之前的 access token
     * 如果存在但是过期了，则获取 fresh token 换取 access token
     * 如果不存在缓存的信息，则重新进行 OAuth 认证
     * @param listener
     */
    public static void authorize(IAuthListener listener){
        authorize(null, listener);
    }

    public static void authorize(String scope, IAuthListener listener) {
        // TODO check Douban init()

        // TODO 检查 本地是否有着权限
//        String[] scopes = scope.split(",");
//        String[] localScopes = PreferenceUtil.getString(sContext, OAuth.SCOPE_KEY).split(",");
//        if localScopes.


        // 检查 access token 是否在有效期内
        long expires_time = PreferenceUtil.getLong(sContext, OAuth.EXPIRES_TIME_KEY);
        if (expires_time != -1) {
            if (expires_time > System.currentTimeMillis()) {
                listener.onAuthSuccess(PreferenceUtil.getString(sContext, OAuth.USER_ID_KEY));
            } else {
                String refreshToken = PreferenceUtil.getString(sContext, OAuth.REFRESH_TOKEN_KEY);
                OAuth.refreshAccessToken(refreshToken, listener);
            }
            return;
        }

        OAuth.authorize(sContext, scope, listener);

    }

    /**
     *
     * @return
     */
    public static boolean isIsInited() {
        return sIsInited;
    }

    /**
     *
     * @return
     */
    public static String getApiKey() {
        return sApiKey;
    }

    /**
     *
     * @return
     */
    public static String getSecret() {
        return sSecret;
    }

    /**
     *
     * @return
     */
    public static String getRedirectURI() {
        return sRedirectURI;
    }

    /**
     *
     * @return
     */
    public static Context getContext() {
        return sContext;
    }
}
