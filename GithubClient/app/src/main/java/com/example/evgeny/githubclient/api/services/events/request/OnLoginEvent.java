package com.example.evgeny.githubclient.api.services.events.request;

import com.example.evgeny.githubclient.model.blogic.LoginData;

/**
 * Created by Evgeny on 09.04.18.
 */

public class OnLoginEvent extends RequestEvent {
    private LoginData loginData;

    public OnLoginEvent(long requestCode, LoginData loginData) {
        super(requestCode);
        this.loginData = loginData;

    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }
}
