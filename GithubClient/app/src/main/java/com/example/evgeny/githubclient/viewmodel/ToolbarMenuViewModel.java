package com.example.evgeny.githubclient.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.example.evgeny.githubclient.GithubClientApp;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.model.blogic.UserProfileData;
import com.example.evgeny.githubclient.model.ui.UIAction;
import com.example.evgeny.githubclient.utils.LocalStoringUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Evgeny on 13.04.18.
 */

public class ToolbarMenuViewModel extends RootViewModel {
    public static final String ACTION_LOGIN = "ACTION_LOGIN";

    private UserProfileData userProfileData;
    private ObservableField<String> userName;
    private ObservableField<String> loginButtonText;
    private ObservableField<Boolean> isOpened;

    public ToolbarMenuViewModel() {
        userProfileData = LocalStoringUtils.getData(LocalStoringUtils.USER_PROFILE_DATA, UserProfileData.class);
        userName = new ObservableField<>("");
        loginButtonText = new ObservableField<>("");
        isOpened = new ObservableField<>(false);

    }

    public void handleToolbarMenu(ImageView avatarView) {
        Context context = GithubClientApp.getContext();
        if (isUserAuthorized()) {
            Picasso.get().load(userProfileData.getAvatar()).placeholder(R.drawable.ic_logo).into(avatarView);
            userName.set(userProfileData.getName());
            loginButtonText.set(context.getResources().getString(R.string.logout_button_label));
        } else {
            userName.set(context.getResources().getString(R.string.guest));
            loginButtonText.set(context.getResources().getString(R.string.login_button_label));
        }
    }

    private boolean isUserAuthorized() {
        return userProfileData != null && userProfileData.getName() != null && !userProfileData.getName().isEmpty() && userProfileData.getId() != 0;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public void setLoginButtonText(String loginButtonText) {
        this.loginButtonText.set(loginButtonText);
    }

    public ObservableField<String> getLoginButtonText() {
        return loginButtonText;
    }

    public void onLoginButtonClicked() {
        UIAction uiAction = new UIAction(ACTION_LOGIN);
        processor.onNext(uiAction);
    }

    public void onProfileButtonClicked() {
        isOpened.set(!isOpened.get());
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened.set(isOpened);
    }

    public ObservableField<Boolean> getIsOpened() {
        return isOpened;
    }

}
