package com.example.messegeme.model.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messegeme.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("User");

    private FirebaseAuth auth;

    private MutableLiveData<String> mutableLiveDataErrors = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> mutableLiveDataUsers = new MutableLiveData<>();

    public RegistrationViewModel() {
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

    public void signUp(String email,
                       String password,
                       String name,
                       String surname,
                       int age){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser firebaseUser = authResult.getUser();
                if (firebaseUser==null){
                    return;
                }
                User user = new User(firebaseUser.getUid(), name, surname, age, false);
                myRef.child(user.getId()).setValue(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            mutableLiveDataErrors.setValue(e.getMessage());
            }
        });
    }
}
