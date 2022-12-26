package com.example.messegeme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messegeme.model.Message;
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
    private String currentIdUSer;
    private String otherIdUser;
    private MessageAdapter messegeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InitView();
        currentIdUSer = getIntent().getStringExtra(EXTRA_CURRENT_ID);
        otherIdUser = getIntent().getStringExtra(EXTRA_OTHER_ID);
        messegeAdapter = new MessageAdapter(currentIdUSer);
        List<Message> messages = new ArrayList<>();
        rvMesseges.setAdapter(messegeAdapter);
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "text" +i, currentIdUSer, otherIdUser
            );
            messages.add(message);
        }

        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "text" +i, otherIdUser, currentIdUSer
            );
            messages.add(message);
        }
        messegeAdapter.setMessages(messages);
    }

    static Intent newIntent(Context context, String currentIdUSer, String otherIdUser){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_ID, currentIdUSer);
        intent.putExtra(EXTRA_OTHER_ID, otherIdUser);
        return intent;

    }
    private void InitView(){
        tvChatTitle = findViewById(R.id.tvChatTitle);
        ViewStatus = findViewById(R.id.ViewStatus);
        rvMesseges = findViewById(R.id.rvMesseges);
        editTextInChat = findViewById(R.id.editTextInChat);
        imageViewSend = findViewById(R.id.imageViewSend);
    }
}