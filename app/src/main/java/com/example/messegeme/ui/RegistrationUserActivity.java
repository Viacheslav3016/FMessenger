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
import android.widget.Toast;

import com.example.messegeme.R;
import com.example.messegeme.model.models.RegistrationViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationUserActivity extends AppCompatActivity {

    private EditText LoginUserEmail;
    private EditText LoginUserPassword;
    private EditText RegistartionName;
    private EditText RegistartionSurname;
    private EditText RegistartionAge;
    private Button LoginRegButton;

    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);
        InitViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        LoginRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTrimValue(LoginUserEmail);
                String password = getTrimValue(LoginUserPassword);
                String name = getTrimValue(RegistartionName);
                String Surname = getTrimValue(RegistartionSurname);
                int age = Integer.parseInt(getTrimValue(RegistartionAge));

                if(email.isEmpty() || password.isEmpty()|| name.isEmpty() || Surname.isEmpty()){
                    Toast.makeText(RegistrationUserActivity.this,
                            "Поля не должны быть пустыми, заполните их",
                            Toast.LENGTH_SHORT).show();
                }else {
                    viewModel.signUp(email, password, name, Surname, age);
                    observeViewModel();
                }


            }
        });
    }

    private void observeViewModel(){
        viewModel.getMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error!=null){
                    Toast.makeText(RegistrationUserActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getMutableLiveDataUsers().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser!=null){
                    Intent intent = UsersActivity.newIntent(RegistrationUserActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private String getTrimValue(EditText editText){
        return editText.getText().toString().trim();
    }
    private void InitViews(){
        LoginUserEmail = findViewById(R.id.LoginUserEmail);
        LoginUserPassword = findViewById(R.id.LoginUserPassword);
        RegistartionName = findViewById(R.id.RegistartionName);
        RegistartionSurname = findViewById(R.id.RegistartionSurname);
        RegistartionAge = findViewById(R.id.RegistartionAge);
        LoginRegButton = findViewById(R.id.LoginRegButton);

    }

    public static Intent newIntent(Context context){
        return new Intent(context, RegistrationUserActivity.class);
    }
}