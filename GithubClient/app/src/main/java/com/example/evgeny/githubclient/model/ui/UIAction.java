package com.example.evgeny.githubclient.model.ui;

import android.support.annotation.NonNull;

/**
 * Created by Evgeny on 07.04.18.
 */

public class UIAction {
    private Object data;

    @NonNull
    private String actionId;

    public UIAction(@NonNull String actionId) {
        this.actionId = actionId;
    }

    public UIAction(@NonNull String actionId, Object data) {
        this.data = data;
        this.actionId = actionId;
    }

    public Object getData() {
        return data;
    }

    @NonNull
    public String getActionId() {
        return actionId;
    }
}
