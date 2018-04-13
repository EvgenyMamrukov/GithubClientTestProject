package com.example.evgeny.githubclient.fragments;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.activities.LoginActivity;
import com.example.evgeny.githubclient.activities.RepositoriesActivity;
import com.example.evgeny.githubclient.databinding.FragmentSplashBinding;
import com.example.evgeny.githubclient.model.blogic.UserProfileData;
import com.example.evgeny.githubclient.model.ui.UIActionConsumer;
import com.example.evgeny.githubclient.utils.AppNavigationUtils;
import com.example.evgeny.githubclient.utils.LocalStoringUtils;

/**
 * Created by Evgeny on 07.04.18.
 */

public class SplashFragment extends AbstractBaseFragment {

    private static final int SPLASH_DELAY = 1500;
    private FragmentSplashBinding fragmentSplashBinding;
    private Handler handler = new Handler();

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        fragmentSplashBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        return fragmentSplashBinding.getRoot();
    }

    @Override
    protected void init() {
        Runnable runnable;
        UserProfileData userProfileData = LocalStoringUtils.getData(LocalStoringUtils.USER_PROFILE_DATA, UserProfileData.class);
        if (userProfileData != null && userProfileData.getName() != null && !userProfileData.getName().isEmpty() && userProfileData.getId() != 0) {
            runnable = () -> {
                AppNavigationUtils.startSingleTopActivity(getActivity(), RepositoriesActivity.class);
            };
        } else {
            runnable = () -> {
                AppNavigationUtils.startSingleTopActivity(getActivity(), LoginActivity.class);
            };
        }
        handler.postDelayed(runnable, SPLASH_DELAY);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void provideHandlers(UIActionConsumer consumer) {

    }
}
