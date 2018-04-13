package com.example.evgeny.githubclient.model.api.response;

import com.example.evgeny.githubclient.model.blogic.RepositoryData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Evgeny on 13.04.18.
 */

public class RepositoriesResponse {
    @Expose
    @SerializedName("items")
    List<RepositoryData> repositoryDataList;

    public List<RepositoryData> getRepositoryDataList() {
        return repositoryDataList;
    }

    public void setRepositoryDataList(List<RepositoryData> repositoryDataList) {
        this.repositoryDataList = repositoryDataList;
    }
}
