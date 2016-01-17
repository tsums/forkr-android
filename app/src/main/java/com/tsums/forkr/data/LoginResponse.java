package com.tsums.forkr.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by trevor on 1/16/16.
 */
public class LoginResponse {

    public GHUser user;

    @SerializedName("new")
    public boolean new_user;

}
