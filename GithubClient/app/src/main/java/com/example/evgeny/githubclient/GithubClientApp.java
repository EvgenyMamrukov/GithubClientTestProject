package com.example.evgeny.githubclient;

import android.app.Application;
import android.content.Context;

import com.example.evgeny.githubclient.api.services.ServicesManager;
import com.example.evgeny.githubclient.api.services.UserDataService;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evgeny on 10.04.18.
 */

public class GithubClientApp extends Application {

    private static Context context;
    private static EventBus eventBus;
    private static ServicesManager servicesManager;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        eventBus = EventBus.builder()
                .eventInheritance(false)
                .sendNoSubscriberEvent(false)
                .build();
        servicesManager = new ServicesManager();
        servicesManager.init(eventBus, context);
    }

    public static Context getContext() {
        return context;
    }

    public static EventBus getEventBus() {
        return eventBus;
    }

    public static UserDataService getUserDataService() {
        return (UserDataService) servicesManager.getUserDataService();
    }

}
