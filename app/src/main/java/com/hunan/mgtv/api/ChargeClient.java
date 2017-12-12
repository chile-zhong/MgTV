/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p>
 * Created on 2016/7/22.
 */
package com.hunan.mgtv.api;

import com.hunan.mgtv.bean.AnswerResponse;
import com.hunan.mgtv.bean.WatermarkResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * TODO
 *
 * @author zhongzhi
 */
public interface ChargeClient {


    @POST("ds02")
    Call<WatermarkResponse> getResponseCallback(@Body RequestBody body);

    @POST("d302")
    Call<AnswerResponse> getAnswerResponse(@Body RequestBody body);

}
