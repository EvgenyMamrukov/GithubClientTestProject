package com.example.evgeny.githubclient.api.services.events.request;

/**
 * Created by Evgeny on 13.04.18.
 */

public class OnGetRepositoriesEvent extends RequestEvent {

    private String query;

    public OnGetRepositoriesEvent(long requestCode, String query) {
        super(requestCode);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
