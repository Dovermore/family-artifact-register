package com.unimelb.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * this class is only used to provide additional param to instantiate view model object
 */
public class ContactSearchResultViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private String param;

    public ContactSearchResultViewModelFactory(Application application, String param) {
        this.application = application;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ContactSearchResultViewModel(application, param);
    }
}
