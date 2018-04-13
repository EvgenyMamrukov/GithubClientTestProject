package com.example.evgeny.githubclient.viewmodel;

import android.databinding.ObservableField;

import com.example.evgeny.githubclient.model.blogic.LoginData;
import com.example.evgeny.githubclient.model.ui.UIAction;

/**
 * Created by Evgeny on 08.04.18.
 */

public class LoginViewModel extends RootViewModel {

    public static final String ACTION_LOGIN = "ACTION_LOGIN";
    public static final String ACTION_PASS_INCOGNITO = "ACTION_PASS_INCOGNITO";

    private ObservableField<String> login;
    private ObservableField<String> password;
    private LoginData loginData;

    public LoginViewModel() {
        login = new ObservableField<>("");
        password = new ObservableField<>("");
        loginData = new LoginData();
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public ObservableField<String> getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public LoginData getLoginData() {
        loginData.setLogin(login.get());
        loginData.setPassword(password.get());
        return loginData;
    }

    public void onLoginButtonClicked() {
        UIAction uiAction = new UIAction(ACTION_LOGIN);
        processor.onNext(uiAction);
    }

    public void onPassIncognitoButtonClicked() {
        UIAction uiAction = new UIAction(ACTION_PASS_INCOGNITO);
        processor.onNext(uiAction);
    }

}
