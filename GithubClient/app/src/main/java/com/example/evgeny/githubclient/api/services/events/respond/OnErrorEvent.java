package com.example.evgeny.githubclient.api.services.events.respond;

import com.example.evgeny.githubclient.api.services.events.request.RequestEvent;

/**
 * Created by Evgeny on 12.04.18.
 */

public class OnErrorEvent extends RespondEvent {
    private String errorMessage;

    public OnErrorEvent(RequestEvent requestEvent, String errorMessage) {
        super(requestEvent);
        this.errorMessage = errorMessage;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}
