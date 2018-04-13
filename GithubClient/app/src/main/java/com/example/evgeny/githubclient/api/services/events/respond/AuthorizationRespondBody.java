package com.example.evgeny.githubclient.api.services.events.respond;

/**
 * Created by Evgeny on 14.04.18.
 */

public class AuthorizationRespondBody {

    private int id;
    private String token;

    public AuthorizationRespondBody(int id, String token) {
        this.id = id;
        this.token = token;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("***** AuthorizationResponseBody Details *****\n");
        stringBuilder.append("id = " + id + "\n");
        stringBuilder.append("token = " + token + "\n");
        stringBuilder.append("*******************************");
        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
