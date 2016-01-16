package com.tsums.forkr.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tsums.forkr.BuildConfig;
import com.tsums.forkr.ForkrApp;
import com.tsums.forkr.R;
import com.tsums.forkr.data.AccessToken;
import com.tsums.forkr.network.AuthRequest;
import com.tsums.forkr.network.ForkrAuthService;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.State;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trevor on 1/16/16.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    public ForkrAuthService authService;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((ForkrApp) getApplication()).getComponent().inject(this);
    }

    @Override
    protected void onResume () {
        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(getString(R.string.gh_redirect_uri))) {
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            if (code != null) {
                Log.i(TAG, "Received code:" + code);
                authService.sendAuthCode(new AuthRequest(code)).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse (Response<AccessToken> response) {
                        Log.i(TAG, "Received code: " + response.body());
                    }

                    @Override
                    public void onFailure (Throwable t) {
                        Log.e(TAG, "uh-oh", t);
                    }
                });
            } else if (uri.getQueryParameter("error") != null) {
                Log.i(TAG, "Received error:" + uri.getQueryParameter("error"));
                // TODO graceful error handling
            }
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @OnClick (R.id.activity_login_button)
    public void onClickLogin() {
        Log.i("LOGIN_A", "clicked");
        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize" +
                                  "?client_id=" + BuildConfig.GH_CLIENT_ID +
                                  "&redirect_uri=" + getString(R.string.gh_redirect_uri) +
                                  "&scopes=user,user:email")); // TODO stop hard-coding scopes
        startActivity(intent);
    }

}
