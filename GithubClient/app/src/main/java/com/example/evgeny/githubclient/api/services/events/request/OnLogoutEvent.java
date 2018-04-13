package com.example.evgeny.githubclient.api.services.events.request;

/**
 * Created by Evgeny on 14.04.18.
 */

public class OnLogoutEvent extends RequestEvent {
    public OnLogoutEvent(long requestCode) {
        super(requestCode);
    }
}
