package com.tsums.forkr.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by trevor on 1/16/16.
 */
public class TokenHelper {

    private static final String SP_NAME = "GHTokenStorage";
    private static final String SP_TOKEN_KEY = "GHToken";

    public static String getToken(Context context) {
        return getSharedPref(context).getString(SP_TOKEN_KEY, "");
    }

    public static void storeToken(Context context, String token) {
        getSharedPref(context).edit().putString(SP_TOKEN_KEY, token).commit();
    }

    public static void clearToken(Context context) {
        getSharedPref(context).edit().remove(SP_TOKEN_KEY).commit();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

}
