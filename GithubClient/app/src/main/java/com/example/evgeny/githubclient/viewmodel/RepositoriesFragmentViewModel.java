package com.example.evgeny.githubclient.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by Evgeny on 14.04.18.
 */

public class RepositoriesFragmentViewModel extends RootViewModel {

    private ObservableField<Boolean> isRepositoriesListEmpty;

    public RepositoriesFragmentViewModel() {
        isRepositoriesListEmpty = new ObservableField<>(true);
    }

    public void setIsRepositoriesListEmpty(boolean isRepositoriesListEmpty) {
        this.isRepositoriesListEmpty.set(isRepositoriesListEmpty);
    }

    public ObservableField<Boolean> getIsRepositoriesListEmpty() {
        return isRepositoriesListEmpty;
    }

}
