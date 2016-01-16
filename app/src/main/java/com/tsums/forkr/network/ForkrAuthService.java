package com.tsums.forkr.network;

import com.tsums.forkr.data.AccessToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by trevor on 1/16/16.
 */
public interface ForkrAuthService {

    @POST("/user/code")
    Call<AccessToken> sendAuthCode(@Body AuthRequest authRequest);

}
