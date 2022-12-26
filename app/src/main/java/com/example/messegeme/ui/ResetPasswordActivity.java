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
import com.example.messegeme.model.models.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";

    private EditText ResetMail;
    private Button ResetSendButton;
    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        InitViews();
        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        observeViewModel();
        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        ResetMail.setText(email);
        ResetSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ResetMail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(ResetPasswordActivity.this,
                            "Поле не должно быть пустым, заполните его",
                            Toast.LENGTH_SHORT).show();
                }else{
                    viewModel.resetPassword(email);

                }
            }
        });

    }

    private void observeViewModel() {
        viewModel.getMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(ResetPasswordActivity.this, error,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getSucces().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean succes) {
                if (succes){
                    Toast.makeText(ResetPasswordActivity.this,
                            "The reset link has been successfully send",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    };

    public static Intent newIntent(Context context, String email){
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }
    private void InitViews(){
        ResetMail = findViewById(R.id.ResetMail);
        ResetSendButton = findViewById(R.id.ResetSendButton);
    }
}