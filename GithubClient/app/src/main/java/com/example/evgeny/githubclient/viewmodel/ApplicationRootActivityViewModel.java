package com.example.evgeny.githubclient.viewmodel;

import android.databinding.ObservableField;

/**
 * Created by Evgeny on 07.04.18.
 */

public class ApplicationRootActivityViewModel extends RootViewModel {

    private ObservableField<Boolean> isToolbarEnabled;
    private ObservableField<Boolean> isProgressBarEnabled;

    public ApplicationRootActivityViewModel() {
        isToolbarEnabled = new ObservableField<>(true);
        isProgressBarEnabled = new ObservableField<>(false);

    }

    public void setIsToolbarEnabled(boolean isToolbarEnabled) {
        this.isToolbarEnabled.set(isToolbarEnabled);
    }

    public ObservableField<Boolean> getIsToolbarEnabled() {
        return isToolbarEnabled;
    }

    public void setIsProgressBarEnabled(boolean isProgressBarEnabled) {
        this.isProgressBarEnabled.set(isProgressBarEnabled);
    }

    public ObservableField<Boolean> getIsProgressBarEnabled() {
        return isProgressBarEnabled;
    }

}
