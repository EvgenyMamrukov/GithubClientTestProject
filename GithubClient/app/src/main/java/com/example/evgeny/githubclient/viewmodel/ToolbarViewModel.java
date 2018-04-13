package com.example.evgeny.githubclient.viewmodel;

import android.databinding.ObservableField;

import com.example.evgeny.githubclient.model.ui.UIAction;

/**
 * Created by Evgeny on 07.04.18.
 */

public class ToolbarViewModel extends RootViewModel {

    public static final String ACTION_BACK_PRESSED = "ACTION_BACK_PRESSED";
    public static final String ACTION_PROFILE_PRESSED = "ACTION_PROFILE_PRESSED";

    private ObservableField<Boolean> isBackButtonEnabled;
    private ObservableField<String> toolbarTitle;
    private ObservableField<Boolean> isSearchViewEnabled;
    private ObservableField<Boolean> isProfileButtonEnabled;

    public ToolbarViewModel() {
        isBackButtonEnabled = new ObservableField<>(true);
        toolbarTitle = new ObservableField<>("");
        isSearchViewEnabled = new ObservableField<>(false);
        isProfileButtonEnabled = new ObservableField<>(false);
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle.set(toolbarTitle);
    }

    public ObservableField<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public void setIsBackButtonEnabled(boolean isBackButtonEnabled) {
        this.isBackButtonEnabled.set(isBackButtonEnabled);
    }

    public ObservableField<Boolean> getIsBackButtonEnabled() {
        return isBackButtonEnabled;
    }

    public void setIsProfileButtonEnabled(boolean isProfileButtonEnabled) {
        this.isProfileButtonEnabled.set(isProfileButtonEnabled);
    }

    public ObservableField<Boolean> getIsProfileButtonEnabled() {
        return isProfileButtonEnabled;
    }

    public void setIsSearchViewEnabled(boolean isSearchViewEnabled) {
        this.isSearchViewEnabled.set(isSearchViewEnabled);
    }

    public ObservableField<Boolean> getIsSearchViewEnabled() {
        return isSearchViewEnabled;
    }

    public void onBackButtonPressed() {
        UIAction uiAction = new UIAction(ACTION_BACK_PRESSED);
        processor.onNext(uiAction);
    }

    public void onProfileButtonPressed() {
        UIAction uiAction = new UIAction(ACTION_PROFILE_PRESSED);
        processor.onNext(uiAction);
    }

}
