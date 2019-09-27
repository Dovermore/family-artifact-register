package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;
import com.example.family_artifact_register.FoundationLayer.SocialModel.UserRepository;

public class ContactDetailViewModel extends AndroidViewModel {

    public static final String TAG = ContactDetailViewModel.class.getSimpleName();

    private LiveData<User> user;
    private UserRepository repository;

    public ContactDetailViewModel(Application application, String username) {
        super(application);
        Log.i(TAG, "enter view model cons");
        repository = new UserRepository(application);
        user = repository.getUser(username);
    }

    public LiveData<User> getUser() { return user; }
}
