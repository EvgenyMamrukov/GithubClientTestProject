package com.example.evgeny.githubclient.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.evgeny.githubclient.BR;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.activities.ApplicationRootActivity;
import com.example.evgeny.githubclient.activities.LoginActivity;
import com.example.evgeny.githubclient.activities.RepositoriesActivity;
import com.example.evgeny.githubclient.api.services.events.EventIds;
import com.example.evgeny.githubclient.api.services.events.request.OnLoginEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnErrorEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnLoginRespondEvent;
import com.example.evgeny.githubclient.databinding.FragmentLoginBinding;
import com.example.evgeny.githubclient.model.blogic.LoginData;
import com.example.evgeny.githubclient.model.ui.UIActionConsumer;
import com.example.evgeny.githubclient.utils.AppNavigationUtils;
import com.example.evgeny.githubclient.viewmodel.LoginViewModel;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Evgeny on 07.04.18.
 */

public class LoginFragment extends AbstractBaseFragment {

    public static final String CURRENTLY_AUTHORIZED_KEY = "CURRENTLY_AUTHORIZED_KEY";
    public static final String CURRENTLY_AUTHORIZED_VALUE = "CURRENTLY_AUTHORIZED_VALUE";
    private FragmentLoginBinding fragmentLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        loginViewModel = new LoginViewModel();
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        fragmentLoginBinding.setVariable(BR.viewModel, loginViewModel);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initListener() {
        loginViewModel.registerListener(consumer);
        listenerRegistered = true;
    }

    private boolean isLoginDataValid() {
        String login = loginViewModel.getLogin().get();
        String password = loginViewModel.getPassword().get();
        return !login.isEmpty() && !password.isEmpty();
    }

    @Override
    protected void provideHandlers(UIActionConsumer consumer) {
        consumer.<String>register(LoginViewModel.ACTION_LOGIN, s -> {
            if (isLoginDataValid()) {
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                ((LoginActivity)getActivity()).setProgressBarEnabled(true);
                LoginData loginData = loginViewModel.getLoginData();
                OnLoginEvent onLoginEvent = new OnLoginEvent(EventIds.LOGIN_EVENT, loginData);
                eventBus.post(onLoginEvent);
            } else {
                ((ApplicationRootActivity)getActivity()).showMessage(getResources().getString(R.string.invalid_data_message));
            }
        }).<String>register(LoginViewModel.ACTION_PASS_INCOGNITO, s -> {
            AppNavigationUtils.startActivity(getActivity(), RepositoriesActivity.class);
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!listenerRegistered) {
            loginViewModel.registerListener(consumer);
            listenerRegistered = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (listenerRegistered) {
            loginViewModel.unregisterListener();
            listenerRegistered = false;
        }
    }

    @Subscribe
    public void onLoginRespond(OnLoginRespondEvent event) {
        if (event.getRequestEvent().getRequestId() == EventIds.LOGIN_EVENT) {
            ((LoginActivity)getActivity()).setProgressBarEnabled(false);
            Bundle bundle = new Bundle();
            bundle.putString(CURRENTLY_AUTHORIZED_KEY, CURRENTLY_AUTHORIZED_VALUE);
            AppNavigationUtils.startSingleTopActivity(getActivity(), RepositoriesActivity.class, bundle);
        }
    }

    @Subscribe
    public void onError(OnErrorEvent errorEvent) {
        if (errorEvent.getRequestEvent().getRequestId() == EventIds.LOGIN_EVENT) {
            ((LoginActivity) getActivity()).setProgressBarEnabled(false);
            ((ApplicationRootActivity) getActivity()).showMessage(errorEvent.getErrorMessage());
        }
    }

}
