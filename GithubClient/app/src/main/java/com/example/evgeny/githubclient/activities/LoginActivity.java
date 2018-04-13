package com.example.evgeny.githubclient.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.fragments.LoginFragment;

/**
 * Created by Evgeny on 07.04.18.
 */

public class LoginActivity extends ApplicationRootActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean provideBackButton() {
        return false;
    }

    @Override
    protected String provideToolbarTitle() {
        return getResources().getString(R.string.login_title);
    }

    @Override
    public Fragment getFragment() {
        return new LoginFragment();
    }


}
