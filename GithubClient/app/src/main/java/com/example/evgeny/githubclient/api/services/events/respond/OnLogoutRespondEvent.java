package com.example.evgeny.githubclient.api.services.events.respond;

import com.example.evgeny.githubclient.api.services.events.request.RequestEvent;

/**
 * Created by Evgeny on 14.04.18.
 */

public class OnLogoutRespondEvent extends RespondEvent {
    public OnLogoutRespondEvent(RequestEvent requestEvent) {
        super(requestEvent);
    }
}
