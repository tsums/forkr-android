package com.tsums.forkr.network;

import com.tsums.forkr.data.GHToken;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by trevor on 1/16/16.
 */
public interface GithubService {

    @POST("/login/oauth/access_token")
    @Headers ("Accept: application/json")
    Call<GHToken> getAccessToken(@Query("client_id") String clientID,
                                 @Query("client_secret") String clientSecret,
                                 @Query("code") String code,
                                 @Query("redirect_uri") String redirect_uri);

}
