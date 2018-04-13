package com.example.evgeny.githubclient.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.evgeny.githubclient.BR;
import com.example.evgeny.githubclient.GithubClientApp;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.api.services.events.EventIds;
import com.example.evgeny.githubclient.api.services.events.request.OnGetRepositoriesEvent;
import com.example.evgeny.githubclient.api.services.events.request.OnLogoutEvent;
import com.example.evgeny.githubclient.databinding.ActivityRootBinding;
import com.example.evgeny.githubclient.databinding.CustomToolbarMenuBinding;
import com.example.evgeny.githubclient.databinding.ToolbarBinding;
import com.example.evgeny.githubclient.model.blogic.UserProfileData;
import com.example.evgeny.githubclient.model.ui.UIActionConsumer;
import com.example.evgeny.githubclient.utils.AppNavigationUtils;
import com.example.evgeny.githubclient.utils.LocalStoringUtils;
import com.example.evgeny.githubclient.viewmodel.ApplicationRootActivityViewModel;
import com.example.evgeny.githubclient.viewmodel.SearchViewViewModel;
import com.example.evgeny.githubclient.viewmodel.ToolbarMenuViewModel;
import com.example.evgeny.githubclient.viewmodel.ToolbarViewModel;

/**
 * Created by Evgeny on 07.04.18.
 */

public abstract class ApplicationRootActivity extends AppCompatActivity {

    protected UIActionConsumer consumer;
    protected ActivityRootBinding activityRootBinding;
    protected ToolbarBinding toolbarBinding;
    protected CustomToolbarMenuBinding toolbarMenuBinding;
    protected Toolbar toolbar;
    protected ToolbarViewModel toolbarViewModel;
    protected SearchViewViewModel searchViewViewModel;
    protected ToolbarMenuViewModel toolbarMenuViewModel;
    protected ApplicationRootActivityViewModel applicationRootActivityViewModel;
    protected View fragmentContainer;
    protected Fragment fragment;
    private UserProfileData userProfileData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userProfileData = LocalStoringUtils.getData(LocalStoringUtils.USER_PROFILE_DATA, UserProfileData.class);
        toolbarViewModel = new ToolbarViewModel();
        searchViewViewModel = new SearchViewViewModel();
        toolbarMenuViewModel = new ToolbarMenuViewModel();
        applicationRootActivityViewModel = new ApplicationRootActivityViewModel();
        activityRootBinding = DataBindingUtil.setContentView(this, R.layout.activity_root);
        toolbarBinding = activityRootBinding.toolbar;
        toolbarMenuBinding = activityRootBinding.toolbarMenu;
        fragmentContainer = activityRootBinding.rootFragmentContainer;
        toolbarBinding.setVariable(BR.viewModel, toolbarViewModel);
        toolbarBinding.setVariable(BR.searchViewModel, searchViewViewModel);
        toolbarMenuBinding.setVariable(BR.viewModel, toolbarMenuViewModel);
        activityRootBinding.setVariable(BR.viewModel, applicationRootActivityViewModel);

        applicationRootActivityViewModel.setIsToolbarEnabled(provideToolbar());
        toolbarViewModel.setIsBackButtonEnabled(provideBackButton());
        toolbarViewModel.setIsProfileButtonEnabled(provideProfileButton());
        toolbarViewModel.setToolbarTitle(provideToolbarTitle());
        toolbarViewModel.setIsSearchViewEnabled(provideSearchView());

        toolbarMenuViewModel.handleToolbarMenu(toolbarMenuBinding.profileImageView);

        initMainContent();
        customizeSearchView();
        initListener();
        provideHandlers(consumer);
    }

    private void customizeSearchView() {
        SearchView searchView = toolbarBinding.toolbarSearchView;
        TextView searchText = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchText.setTextColor(getResources().getColor(R.color.colorWhite));
        View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundResource(R.drawable.search_view_background);
    }

    private void initListener() {
        consumer = new UIActionConsumer();
        toolbarViewModel.registerListener(consumer);
        searchViewViewModel.registerListener(consumer);
        toolbarMenuViewModel.registerListener(consumer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toolbarViewModel.unregisterListener();
        searchViewViewModel.unregisterListener();
        toolbarMenuViewModel.unregisterListener();
    }

    public abstract Fragment getFragment();

    protected void insertFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), fragment);
            transaction
                    .commit();
        }
    }

    protected void initMainContent() {
        Fragment fragment = getFragment();
        if (fragment != null) {
            try {
                this.fragment = fragment;
                insertFragment(fragment);
            } catch (Exception e) {
                Log.e(ApplicationRootActivity.class.getCanonicalName(), "Can't insert fragment " + fragment.getClass().getCanonicalName(), e);
            }
        }
    }

    protected boolean provideToolbar() {
        return true;
    }

    protected boolean provideBackButton() {
        return true;
    }

    protected boolean provideProfileButton() {
        return false;
    }

    protected boolean provideSearchView() {
        return false;
    }

    protected String provideToolbarTitle() {
        return "";
    }

    protected void provideHandlers(UIActionConsumer consumer) {
        consumer.<String>register(ToolbarViewModel.ACTION_BACK_PRESSED, s -> {
            onBackPressed();
        }).<String>register(ToolbarViewModel.ACTION_PROFILE_PRESSED, s -> {
            boolean isOpened = toolbarMenuViewModel.getIsOpened().get();
            toolbarMenuViewModel.setIsOpened(!isOpened);
        }).<String>register(ToolbarMenuViewModel.ACTION_LOGIN, s -> {
            if (isUserAuthorized()) {
                setProgressBarEnabled(true);
                OnLogoutEvent onLogoutEvent = new OnLogoutEvent(EventIds.LOGOUT_EVENT);
                GithubClientApp.getEventBus().post(onLogoutEvent);
            } else {
                AppNavigationUtils.startSingleTopActivity(ApplicationRootActivity.this, LoginActivity.class);
            }
        }).<String>register(SearchViewViewModel.ACTION_SEARCH, s -> {
            if (s != null && !s.isEmpty()) {
                setProgressBarEnabled(true);
                OnGetRepositoriesEvent onGetRepositoriesEvent = new OnGetRepositoriesEvent(EventIds.GET_REPOSITORIES_EVENT, s);
                GithubClientApp.getEventBus().post(onGetRepositoriesEvent);
            }
        });
    }

    protected boolean isUserAuthorized() {
        return userProfileData != null && userProfileData.getName() != null && !userProfileData.getName().isEmpty() && userProfileData.getId() != 0;
    }

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar.make(activityRootBinding.getRoot(), message, Snackbar.LENGTH_LONG)
                .setAction(getResources().getString(R.string.close), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorDesignDarkRed));
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorDesignLightRed));
        int snackbarTextId = android.support.design.R.id.snackbar_text;
        TextView textView = snackbar.getView().findViewById(snackbarTextId);
        textView.setTextColor(getResources().getColor(R.color.colorDesignDarkRed));
        snackbar.show();
    }

    public void setProgressBarEnabled(boolean isProgressEnabled) {
        applicationRootActivityViewModel.setIsProgressBarEnabled(isProgressEnabled);
    }

}
