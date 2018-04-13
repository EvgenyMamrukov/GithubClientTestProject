package com.example.evgeny.githubclient.api.services.events.respond;

import com.example.evgeny.githubclient.api.services.events.request.RequestEvent;

/**
 * Created by Evgeny on 10.04.18.
 */

public class OnLoginRespondEvent extends RespondEvent {
    public OnLoginRespondEvent(RequestEvent requestEvent) {
        super(requestEvent);
    }
}
