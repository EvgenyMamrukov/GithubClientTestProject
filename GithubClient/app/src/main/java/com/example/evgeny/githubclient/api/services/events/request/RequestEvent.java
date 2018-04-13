package com.example.evgeny.githubclient.api.services.events.request;

/**
 * Created by Evgeny on 09.04.18.
 */

public class RequestEvent {
    protected long requestId;

    public long getRequestId() {
        return requestId;
    }

    public RequestEvent(long requestCode) {
        this.requestId = requestCode;
    }
}
