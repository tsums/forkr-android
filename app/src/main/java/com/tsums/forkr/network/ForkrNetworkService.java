package com.tsums.forkr.network;

import com.tsums.forkr.data.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by trevor on 1/16/16.
 */
public interface ForkrNetworkService {

    @POST("/users/login")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("token") String access_token);

}
