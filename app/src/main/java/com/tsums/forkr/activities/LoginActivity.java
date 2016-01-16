package com.tsums.forkr.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tsums.forkr.BuildConfig;
import com.tsums.forkr.R;
import com.tsums.forkr.data.AccessToken;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trevor on 1/16/16.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(getString(R.string.gh_redirect_uri))) {
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            if (code != null) {
                Log.i(TAG, "Received code:" + code);
            } else if (uri.getQueryParameter("error") != null) {
                Log.i(TAG, "Received error:" + uri.getQueryParameter("error"));
            }
        }
    }

    @OnClick (R.id.activity_login_button)
    public void onClickLogin() {
        Log.i("LOGIN_A", "clicked");
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize" +
                                  "?client_id=" + BuildConfig.GH_CLIENT_ID +
                                  "&redirect_uri=" + getString(R.string.gh_redirect_uri) +
                                  "&scopes=user,user:email&state=" + UUID.randomUUID().toString())); // TODO stop hard-coding scopes
        startActivity(intent);
    }

}
