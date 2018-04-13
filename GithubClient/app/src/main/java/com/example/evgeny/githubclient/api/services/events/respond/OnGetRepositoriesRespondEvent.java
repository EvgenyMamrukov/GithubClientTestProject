package com.example.evgeny.githubclient.api.services.events.respond;

import com.example.evgeny.githubclient.api.services.events.request.RequestEvent;
import com.example.evgeny.githubclient.model.api.response.RepositoriesResponse;

/**
 * Created by Evgeny on 13.04.18.
 */

public class OnGetRepositoriesRespondEvent extends RespondEvent {
    private RepositoriesResponse response;

    public OnGetRepositoriesRespondEvent(RequestEvent requestEvent, RepositoriesResponse response) {
        super(requestEvent);
        this.response = response;
    }

    public RepositoriesResponse getResponse() {
        return response;
    }

    public void setResponse(RepositoriesResponse response) {
        this.response = response;
    }
}
