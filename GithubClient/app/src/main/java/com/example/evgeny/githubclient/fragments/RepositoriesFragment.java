package com.example.evgeny.githubclient.fragments;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evgeny.githubclient.BR;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.activities.ApplicationRootActivity;
import com.example.evgeny.githubclient.activities.LoginActivity;
import com.example.evgeny.githubclient.activities.RepositoriesActivity;
import com.example.evgeny.githubclient.adapters.RepositoriesItemAdapter;
import com.example.evgeny.githubclient.api.services.events.EventIds;
import com.example.evgeny.githubclient.api.services.events.respond.OnErrorEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnGetRepositoriesRespondEvent;
import com.example.evgeny.githubclient.api.services.events.respond.OnLogoutRespondEvent;
import com.example.evgeny.githubclient.databinding.CustomAlertDialogBinding;
import com.example.evgeny.githubclient.databinding.FragmentRepositoriesBinding;
import com.example.evgeny.githubclient.model.blogic.RepositoryData;
import com.example.evgeny.githubclient.model.blogic.UserProfileData;
import com.example.evgeny.githubclient.model.ui.UIActionConsumer;
import com.example.evgeny.githubclient.utils.AppNavigationUtils;
import com.example.evgeny.githubclient.utils.LocalStoringUtils;
import com.example.evgeny.githubclient.viewmodel.CustomAlertDialogViewModel;
import com.example.evgeny.githubclient.viewmodel.RepositoriesFragmentViewModel;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny on 12.04.18.
 */

public class RepositoriesFragment extends AbstractBaseFragment {
    private FragmentRepositoriesBinding fragmentRepositoriesBinding;
    private UserProfileData userProfileData;
    private LayoutInflater layoutInflater;
    private ViewGroup layoutContainer;
    private RepositoriesFragmentViewModel repositoriesFragmentViewModel;

    private RepositoriesItemAdapter repositoriesItemAdapter;

    @Override
    protected View initBinding(LayoutInflater inflater, ViewGroup container) {
        repositoriesFragmentViewModel = new RepositoriesFragmentViewModel();
        layoutInflater = inflater;
        layoutContainer = container;
        fragmentRepositoriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_repositories, container, false);
        fragmentRepositoriesBinding.setVariable(BR.viewModel, repositoriesFragmentViewModel);
        userProfileData = LocalStoringUtils.getData(LocalStoringUtils.USER_PROFILE_DATA, UserProfileData.class);

        return fragmentRepositoriesBinding.getRoot();
    }

    @Override
    protected void init() {
        String currentlyAuthorizationValue = getActivity().getIntent().getStringExtra(LoginFragment.CURRENTLY_AUTHORIZED_KEY);
        if (currentlyAuthorizationValue != null && currentlyAuthorizationValue.equals(LoginFragment.CURRENTLY_AUTHORIZED_VALUE)) {
            showAuthorizationDialog();
        }

        repositoriesItemAdapter = new RepositoriesItemAdapter();

        fragmentRepositoriesBinding.repositoriesList.setAdapter(repositoriesItemAdapter);
        fragmentRepositoriesBinding.repositoriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showAuthorizationDialog() {
        CustomAlertDialogBinding customAlertDialogBinding = DataBindingUtil.inflate(layoutInflater, R.layout.custom_alert_dialog, layoutContainer, false);
        CustomAlertDialogViewModel customAlertDialogViewModel = new CustomAlertDialogViewModel(userProfileData);
        customAlertDialogBinding.setVariable(BR.viewModel, customAlertDialogViewModel);
        Picasso.get().load(userProfileData.getAvatar()).placeholder(R.drawable.ic_logo).into(customAlertDialogBinding.profileImageView);
        AlertDialog.Builder authorizationDialogBuilder = new AlertDialog.Builder(getActivity());
        AlertDialog authorizationDialog = authorizationDialogBuilder.create();
        customAlertDialogViewModel.setAlertDialog(authorizationDialog);
        authorizationDialog.setView(customAlertDialogBinding.getRoot());
        authorizationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        authorizationDialog.show();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void provideHandlers(UIActionConsumer consumer) {

    }

    @Subscribe
    public void onGetRepositories(OnGetRepositoriesRespondEvent event) {
        if (event.getRequestEvent().getRequestId() == EventIds.GET_REPOSITORIES_EVENT) {
            if (event.getResponse().getRepositoryDataList() == null || event.getResponse().getRepositoryDataList().isEmpty()) {
                repositoriesFragmentViewModel.setIsRepositoriesListEmpty(true);
            } else {
                repositoriesFragmentViewModel.setIsRepositoriesListEmpty(false);
            }
            ((RepositoriesActivity) getActivity()).setProgressBarEnabled(false);
            repositoriesItemAdapter.setItems(event.getResponse().getRepositoryDataList());
        }
    }

    @Subscribe
    public void onLogout(OnLogoutRespondEvent event) {
        if (event.getRequestEvent().getRequestId() == EventIds.LOGOUT_EVENT) {
            ((RepositoriesActivity) getActivity()).setProgressBarEnabled(false);
            LocalStoringUtils.removeData(LocalStoringUtils.USER_PROFILE_DATA);
            LocalStoringUtils.removeData(LocalStoringUtils.AUTHORIZATION_ID);
            LocalStoringUtils.removeData(LocalStoringUtils.AUTHORIZATION_ENCODED_DATA);
            AppNavigationUtils.startSingleTopActivity(getActivity(), LoginActivity.class);
        }
    }

    @Subscribe
    public void onError(OnErrorEvent errorEvent) {
        if (errorEvent.getRequestEvent().getRequestId() == EventIds.GET_REPOSITORIES_EVENT) {

            repositoriesFragmentViewModel.setIsRepositoriesListEmpty(true);
            List<RepositoryData> repositoryDataList = new ArrayList<>();
            repositoriesItemAdapter.setItems(repositoryDataList);

            ((RepositoriesActivity) getActivity()).setProgressBarEnabled(false);
            ((ApplicationRootActivity) getActivity()).showMessage(errorEvent.getErrorMessage());
        } else if (errorEvent.getRequestEvent().getRequestId() == EventIds.LOGOUT_EVENT) {
            ((RepositoriesActivity) getActivity()).setProgressBarEnabled(false);
            ((RepositoriesActivity) getActivity()).showMessage(errorEvent.getErrorMessage());
        }
    }

}
