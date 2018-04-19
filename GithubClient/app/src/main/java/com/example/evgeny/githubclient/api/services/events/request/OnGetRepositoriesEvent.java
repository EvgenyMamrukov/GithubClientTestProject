package com.example.evgeny.githubclient.api.services.events.request;

/**
 * Created by Evgeny on 13.04.18.
 */

public class OnGetRepositoriesEvent extends RequestEvent {

    private String query;
    private int page;
    private int perPage;

    public OnGetRepositoriesEvent(long requestCode, String query, int page, int perPage) {
        super(requestCode);
        this.query = query;
        this.page = page;
        this.perPage = perPage;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
