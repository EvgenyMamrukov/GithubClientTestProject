package com.example.evgeny.githubclient.api.services.events.respond;

import com.example.evgeny.githubclient.api.services.events.request.RequestEvent;

/**
 * Created by Evgeny on 10.04.18.
 */

public class RespondEvent {
    public RequestEvent getRequestEvent() {
        return requestEvent;
    }

    protected RequestEvent requestEvent;

    public RespondEvent(RequestEvent requestEvent) {
        this.requestEvent = requestEvent;
    }

    public long getRequestCode() {
        return requestEvent.getRequestId();
    }

}
