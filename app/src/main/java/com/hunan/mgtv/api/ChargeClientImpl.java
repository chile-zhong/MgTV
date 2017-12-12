/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 8/16/2016.
 */
package com.hunan.mgtv.api;

import android.content.Context;

import com.hunan.mgtv.bean.AnswerResponse;
import com.hunan.mgtv.bean.WatermarkResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO
 *
 * @author zhongzhi
 */
public class ChargeClientImpl implements ChargeClient {
    private static String LOG_TAG = "ChargeClientImpl";

    private ChargeClient chargeClient;

    public ChargeClientImpl(final Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
//                                .addHeader(SwiftLiveAPI.HEADERS.CONTENT_TYPE, SwiftLiveAPI.CONTENT_TYPE)
//                                .addHeader(SwiftLiveAPI.HEADERS.X_USER_AGENT, Utils.getUserAgent(context))
//                                .addHeader(SwiftLiveAPI.HEADERS.ACCEPT_LANGUAGE, Utils.getLocaleLanguage())
//                                .addHeader(SwiftLiveAPI.HEADERS.CACHE_CONTROL, "no-cache")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://106.15.50.103:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        chargeClient = retrofit.create(ChargeClient.class);

    }

    @Override
    public Call<WatermarkResponse> getResponseCallback(RequestBody body) {
        return chargeClient.getResponseCallback(body);
    }

    @Override
    public Call<AnswerResponse> getAnswerResponse(RequestBody body) {
        return chargeClient.getAnswerResponse(body);
    }


}
