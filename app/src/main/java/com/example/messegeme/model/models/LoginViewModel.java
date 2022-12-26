package com.example.messegeme.model.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private FirebaseAuth auth;

    private MutableLiveData<String> mutableLiveDataErrors = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> mutableLiveDataUsers = new MutableLiveData<>();

    public LoginViewModel() {
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null){
                    mutableLiveDataUsers.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
    }

    public LiveData<String> getMutableLiveData() {
        return mutableLiveDataErrors;
    }

    public LiveData<FirebaseUser> getMutableLiveDataUsers() {
        return mutableLiveDataUsers;
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableLiveDataErrors.setValue(e.getMessage());
            }
        });
    }
}
