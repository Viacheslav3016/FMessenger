package com.example.messegeme.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.messegeme.R;
import com.example.messegeme.model.User;
import com.example.messegeme.ui.adapters.UserAdapter;
import com.example.messegeme.model.models.UserViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_ID = "current_id";
    private UserViewModel viewModel;
    private RecyclerView rvUsers;
    private UserAdapter userAdapter;
    private String currentIdUser;

    private void InitViews(){
        rvUsers = findViewById(R.id.rvUsers);
        userAdapter = new UserAdapter();
        rvUsers.setAdapter(userAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        currentIdUser = getIntent().getStringExtra(EXTRA_CURRENT_ID);
        InitViews();
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        observeViewModel();
        userAdapter.setOnUserClickListener(new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Intent intent = ChatActivity.newIntent(UsersActivity.this, currentIdUser, user.getId());
                startActivity(intent);
            }
        });
    }

    public static Intent newIntent(Context context, String currentIdUSer){
        Intent intent =  new Intent(context, UsersActivity.class);
        intent.putExtra(EXTRA_CURRENT_ID, currentIdUSer);
        return intent;
    }

    private void observeViewModel(){
        viewModel.getMutableLiveDataUsers().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser==null) {
                    Intent intent = LoginActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUserList(users);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            viewModel.logOut();
        }
        return super.onOptionsItemSelected(item);
    }
}