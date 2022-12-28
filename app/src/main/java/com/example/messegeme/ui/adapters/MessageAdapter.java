package com.example.messegeme.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messegeme.model.Message;
import com.example.messegeme.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessegeViewHolder> {

    private static final int MY_MESSAGE = 100;
    private static final int OTHER_MESSAGE = 101;

    List<Message> messages = new ArrayList<>();

    private String currentId = "";

    public MessageAdapter(String currentId) {
        this.currentId = currentId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessegeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int ResIdLayout;
        if (viewType == MY_MESSAGE) {
            ResIdLayout = R.layout.my_messege_item;
        } else {
            ResIdLayout = R.layout.other_messege_item;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(ResIdLayout, parent, false);
        return new MessegeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessegeViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.tvMessege.setText(message.getText());
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSendId().equals(currentId)) {
            return MY_MESSAGE;
        } else {
            return OTHER_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class MessegeViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessege;

        public MessegeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessege = itemView.findViewById(R.id.tvMessege);
        }
    }
}
