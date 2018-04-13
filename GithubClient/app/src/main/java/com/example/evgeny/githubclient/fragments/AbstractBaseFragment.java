package com.example.evgeny.githubclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evgeny.githubclient.GithubClientApp;
import com.example.evgeny.githubclient.api.services.events.respond.OnErrorEvent;
import com.example.evgeny.githubclient.model.ui.UIActionConsumer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Evgeny on 07.04.18.
 */

public abstract class AbstractBaseFragment extends Fragment {

    protected UIActionConsumer consumer = new UIActionConsumer();
    protected EventBus eventBus;
    protected boolean registered = false;
    protected boolean listenerRegistered = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.eventBus = GithubClientApp.getEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initBinding(inflater, container);
        init();
        initListener();
        provideHandlers(consumer);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!registered) {
            registered = true;
            eventBus.register(this);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (registered) {
            registered = false;
            eventBus.unregister(this);
        }
    }

    @Subscribe
    public void onError(OnErrorEvent event) {
        Log.e("error", event.getErrorMessage());
    }

    protected abstract View initBinding(LayoutInflater inflater, ViewGroup container);

    protected abstract void init();

    protected abstract void initListener();

    protected abstract void provideHandlers(UIActionConsumer consumer);

}
