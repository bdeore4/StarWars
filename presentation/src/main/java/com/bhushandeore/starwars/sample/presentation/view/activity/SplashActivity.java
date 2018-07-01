package com.bhushandeore.starwars.sample.presentation.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.bhushandeore.starwars.sample.presentation.R;

import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        getApplicationComponent().inject(this);

        navigate();
    }

    private void navigate() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                navigateToPeopleList();
            }
        }, 3000);
    }

    private void navigateToPeopleList() {
        this.navigator.navigateToPeopleList(this);
    }

}