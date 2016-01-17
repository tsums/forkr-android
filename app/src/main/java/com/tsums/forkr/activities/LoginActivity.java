package com.tsums.forkr.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tsums.forkr.BuildConfig;
import com.tsums.forkr.ForkrApp;
import com.tsums.forkr.R;
import com.tsums.forkr.data.GHToken;
import com.tsums.forkr.data.GHUser;
import com.tsums.forkr.data.LoginResponse;
import com.tsums.forkr.data.TokenHelper;
import com.tsums.forkr.network.ForkrNetworkService;
import com.tsums.forkr.network.GithubService;


import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.Icepick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by trevor on 1/16/16.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    @Inject
    public GithubService githubService;

    @Inject
    public ForkrNetworkService networkService;

    @Bind (R.id.activity_login_button)   LinearLayout loginButton;
    @Bind (R.id.activity_login_progress) ProgressBar  progressBar;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((ForkrApp) getApplication()).getComponent().inject(this);

        if (!TokenHelper.getToken(this).isEmpty()) {
            getUserAndProceed(TokenHelper.getToken(this));
        }
    }

    @Override
    protected void onResume () {
        super.onResume();

        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(getString(R.string.gh_redirect_uri))) {
            if (uri.getQueryParameter("error") != null) {
                Log.i(TAG, "Received error:" + uri.getQueryParameter("error"));
            } else if (uri.toString().contains("token")) {
                String token = uri.getQueryParameter("access_token");
                Log.i(TAG, "token: " + token);
            } else {
                String code = uri.getQueryParameter("code");
                if (code != null) {
                    Log.i(TAG, "Received code:" + code);
                    githubService.getAccessToken(BuildConfig.GH_CLIENT_ID, BuildConfig.GH_CLIENT_SECRET, code, getString(R.string.gh_redirect_uri_token)).enqueue(new Callback<GHToken>() {
                        @Override
                        public void onResponse (Call<GHToken> call, Response<GHToken> response) {
                            TokenHelper.storeToken(LoginActivity.this, response.body().access_token);
                            getUserAndProceed(response.body().access_token);
                        }

                        @Override
                        public void onFailure (Call<GHToken> call, Throwable t) {
                            Log.e(TAG, "err: ", t);
                        }
                    });
                }

            }
        }
    }

    private void getUserAndProceed(String accessToken) {
        networkService.login(accessToken).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse (Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.body().new_user) {
//                    // TODO new user flow
//                }

                if (response.body() == null) {
                    Log.e(TAG, "Response was null!!");
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("user", Parcels.wrap(response.body().user));
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure (Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "err: ", t);
            }
        });
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @OnClick (R.id.activity_login_button)
    public void onClickLogin() {
        Log.i(TAG, "clicked");

        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize" +
                                  "?client_id=" + BuildConfig.GH_CLIENT_ID +
                                  "&redirect_uri=" + getString(R.string.gh_redirect_uri) +
                                  "&scopes=user,user:email")); // TODO stop hard-coding scopes
        startActivity(intent);
    }

}
