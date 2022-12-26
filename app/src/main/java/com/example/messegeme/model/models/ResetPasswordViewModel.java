package com.example.messegeme.model.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordViewModel extends ViewModel {

    private FirebaseAuth auth;

    public ResetPasswordViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    private MutableLiveData<String> mutableLiveDataErrors = new MutableLiveData<>();
    private MutableLiveData<Boolean> succes = new MutableLiveData<>();

    public LiveData<String> getMutableLiveData() {
        return mutableLiveDataErrors;
    }

    public LiveData<Boolean> getSucces() {
        return succes;
    }

    public void resetPassword(String email){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                succes.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mutableLiveDataErrors.setValue(e.getMessage());
            }
        });
    }
}
