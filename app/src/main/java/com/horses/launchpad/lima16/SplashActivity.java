package com.horses.launchpad.lima16;

import android.content.Intent;

import io.paperdb.Paper;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate() {

        if (Paper.book().read("session", false)) {

            startActivity(new Intent(this, MainActivity.class));
        }
        else {

            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
    }
}
