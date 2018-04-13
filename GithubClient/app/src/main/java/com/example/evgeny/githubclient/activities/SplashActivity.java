package com.example.evgeny.githubclient.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.fragments.SplashFragment;

public class SplashActivity extends ApplicationRootActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean provideToolbar() {
        return false;
    }

    @Override
    protected boolean provideBackButton() {
        return true;
    }

    @Override
    protected String provideToolbarTitle() {
        return getResources().getString(R.string.login_title);
    }

    @Override
    public Fragment getFragment() {
        return new SplashFragment();
    }
}
