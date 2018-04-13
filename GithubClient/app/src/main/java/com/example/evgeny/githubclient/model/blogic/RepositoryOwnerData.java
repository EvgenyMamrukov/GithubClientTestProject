package com.example.evgeny.githubclient.model.blogic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Evgeny on 13.04.18.
 */

public class RepositoryOwnerData {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("login")
    private String login;
    @Expose
    @SerializedName("avatar_url")
    private String avatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
