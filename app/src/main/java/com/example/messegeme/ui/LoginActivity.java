package com.example.messegeme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messegeme.model.models.LoginViewModel;
import com.example.messegeme.R;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText LoginUserEmail;
    private EditText LoginUserPassword;
    private Button LoginButton;
    private TextView LoginTextViewRegistration;
    private TextView LoginTextViewResetPassword;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setUpOnClickListener();
        observeViewModel();
    }

    private void setUpOnClickListener(){
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = LoginUserEmail.getText().toString().trim();
                String password = LoginUserPassword.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this,
                            "Поля не должны быть пустыми, заполните их",
                            Toast.LENGTH_SHORT).show();
                }else{
                    viewModel.login(email, password);
                }
            }
        });

        LoginTextViewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegistrationUserActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });


        LoginTextViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ResetPasswordActivity.newIntent(LoginActivity.this,
                        LoginUserEmail.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void observeViewModel(){
        viewModel.getMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String err) {
                if (err!=null) {
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getMutableLiveDataUsers().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser!=null){
                    Intent intent = UsersActivity.newIntent(LoginActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void InitViews(){
        LoginButton = findViewById(R.id.LoginButton);
        LoginUserEmail = findViewById(R.id.LoginUserEmail);
        LoginUserPassword = findViewById(R.id.LoginUserPassword);
        LoginTextViewRegistration = findViewById(R.id.LoginTextViewRegistration);
        LoginTextViewResetPassword = findViewById(R.id.LoginTextViewResetPassword);
    }
    public static Intent newIntent(Context context){
        return new Intent(context, LoginActivity.class);
    }
}