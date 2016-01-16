package com.tsums.forkr.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tsums.forkr.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by trevor on 1/16/16.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick (R.id.activity_login_button)
    public void onClickLogin() {
        Log.i("LOGIN_A", "clicked");
    }

}
