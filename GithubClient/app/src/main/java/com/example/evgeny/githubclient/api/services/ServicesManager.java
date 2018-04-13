package com.example.evgeny.githubclient.api.services;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evgeny on 09.04.18.
 */

public class ServicesManager {

    private AbstractService userDataService;

    public void init(EventBus eventBus, Context context) {
        userDataService = new UserDataService();
        userDataService.init(eventBus, context, this);
    }

    public AbstractService getUserDataService() {
        return userDataService;
    }

}
