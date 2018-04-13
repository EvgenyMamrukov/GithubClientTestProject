package com.example.evgeny.githubclient.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.fragments.RepositoriesFragment;

/**
 * Created by Evgeny on 12.04.18.
 */

public class RepositoriesActivity extends ApplicationRootActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarMenuViewModel.handleToolbarMenu(toolbarMenuBinding.profileImageView);
    }

    @Override
    protected boolean provideBackButton() {
        return false;
    }

    @Override
    protected boolean provideProfileButton() {
        return true;
    }

    @Override
    protected String provideToolbarTitle() {
        return "";
    }

    @Override
    protected boolean provideSearchView() {
        return true;
    }

    @Override
    public Fragment getFragment() {
        return new RepositoriesFragment();
    }

}
