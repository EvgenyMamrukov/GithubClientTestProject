package com.example.evgeny.githubclient.api.services;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Evgeny on 09.04.18.
 */

public abstract class AbstractService {
    protected EventBus eventBus;
    protected Context context;
    protected ServicesManager holder;

    public void init(EventBus eventBus, Context context, ServicesManager holder) {
        this.eventBus = eventBus;
        this.context = context;
        this.holder = holder;
        eventBus.register(this);
    }
}
