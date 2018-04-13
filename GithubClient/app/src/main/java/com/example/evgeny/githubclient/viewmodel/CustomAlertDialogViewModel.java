package com.example.evgeny.githubclient.viewmodel;

import android.databinding.ObservableField;
import android.support.v7.app.AlertDialog;

import com.example.evgeny.githubclient.databinding.CustomAlertDialogBinding;
import com.example.evgeny.githubclient.model.blogic.UserProfileData;

/**
 * Created by Evgeny on 12.04.18.
 */

public class CustomAlertDialogViewModel extends RootViewModel {
    private UserProfileData userProfileData;
    private ObservableField<String> userName;
    private AlertDialog alertDialog;

    public CustomAlertDialogViewModel(UserProfileData userProfileData) {
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
        }
        this.userProfileData = userProfileData;
        userName = new ObservableField<>(userProfileData.getName());
    }

    public void setUserProfileData(UserProfileData userProfileData) {
        userName.set(userProfileData.getName());
    }

    public ObservableField<String> getUserName() {
        return userName;
    }

    public void onContinueButtonClicked() {
        alertDialog.dismiss();
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }
}
