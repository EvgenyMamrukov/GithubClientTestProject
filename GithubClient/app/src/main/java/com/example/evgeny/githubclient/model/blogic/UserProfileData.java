package com.example.evgeny.githubclient.model.blogic;

/**
 * Created by Evgeny on 12.04.18.
 */

public class UserProfileData {
    private long id;
    private String name;
    private String avatar;
    private String repositories;

    public UserProfileData() {

    }

    public UserProfileData(long id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRepositories() {
        return repositories;
    }

    public void setRepositories(String repositories) {
        this.repositories = repositories;
    }
}
