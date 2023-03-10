package com.example.messegeme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messegeme.model.Message;
import com.example.messegeme.model.User;
import com.example.messegeme.model.models.ChatViewModel;
import com.example.messegeme.model.models.ChatViewModelFactory;
import com.example.messegeme.model.models.UserViewModel;
import com.example.messegeme.ui.adapters.MessageAdapter;
import com.example.messegeme.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private TextView tvChatTitle;
    private View ViewStatus;
    private RecyclerView rvMesseges;
    private EditText editTextInChat;
    private ImageView imageViewSend;
    private static final String EXTRA_CURRENT_ID = "current_id";
    public static final String EXTRA_OTHER_ID = "other_id";
    private String currentIdUser;
    private String otherIdUser;
    private MessageAdapter messegeAdapter;

    private UserViewModel userViewModel;
    private ChatViewModel viewModel;
    private ChatViewModelFactory viewModelFactory;
    private MediaPlayer sendSound;
    private MediaPlayer recievedSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitView();
        InitSound();
        currentIdUser = getIntent().getStringExtra(EXTRA_CURRENT_ID);
        otherIdUser = getIntent().getStringExtra(EXTRA_OTHER_ID);
        viewModelFactory = new ChatViewModelFactory(currentIdUser, otherIdUser);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ChatViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        messegeAdapter = new MessageAdapter(currentIdUser);
        List<Message> messages = new ArrayList<>();
        rvMesseges.setAdapter(messegeAdapter);
        observeViewModel();

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(editTextInChat
                        .getText()
                        .toString()
                        .trim(),
                        currentIdUser,
                        otherIdUser);
                viewModel.sendMessage(message);
                sendSound.start();
            }
        });
    }

    private void observeViewModel() {
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messegeAdapter.setMessages(messages);
            }
        });
        viewModel.getErrors().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessege) {
                if (errorMessege != null) {
                    Toast.makeText(ChatActivity.this, errorMessege, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getMessageSend().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean send) {
                if (send) {
                    recievedSound.start();
                    editTextInChat.setText("");
                }
            }
        });
        viewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getName(), user.getSurname());
                tvChatTitle.setText(userInfo);
                int bgResId;
                if (user.getOnline()){
                    bgResId =  R.drawable.circle_green;
                }else{
                    bgResId =  R.drawable.circle_red;
                }
                Drawable bg = ContextCompat.getDrawable(ChatActivity.this, bgResId);
                ViewStatus.setBackground(bg);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.setUserStatus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        userViewModel.setUserStatus(false);
    }

    static Intent newIntent(Context context, String currentIdUSer, String otherIdUser) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_ID, currentIdUSer);
        intent.putExtra(EXTRA_OTHER_ID, otherIdUser);
        return intent;

    }
    private void InitSound(){
        sendSound = MediaPlayer.create(this, R.raw.sendmessege);
        recievedSound = MediaPlayer.create(this, R.raw.recievedmessege);
    }
    private void InitView() {
        tvChatTitle = findViewById(R.id.tvChatTitle);
        ViewStatus = findViewById(R.id.ViewStatus);
        rvMesseges = findViewById(R.id.rvMesseges);
        editTextInChat = findViewById(R.id.editTextInChat);
        imageViewSend = findViewById(R.id.imageViewSend);
    }
}