package com.example.evgeny.githubclient.model.blogic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Evgeny on 13.04.18.
 */

public class RepositoryData {
    @Expose
    @SerializedName("id")
    private long id;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("full_name")
    private String fullName;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("owner")
    private RepositoryOwnerData ownerData;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RepositoryOwnerData getOwnerData() {
        return ownerData;
    }

    public void setOwnerData(RepositoryOwnerData ownerData) {
        this.ownerData = ownerData;
    }
}
