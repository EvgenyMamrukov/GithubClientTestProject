package com.example.evgeny.githubclient.model.api.response;

import com.google.gson.annotations.Expose;

/**
 * Created by Evgeny on 14.04.18.
 */

public class LogoutResponse {
    @Expose
    private int status;

    public int getMessage() {
        return status;
    }

    public void setMessage(int status) {
        this.status = status;
    }
}
