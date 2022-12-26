package com.example.messegeme.ui.adapters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messegeme.R;
import com.example.messegeme.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList = new ArrayList<>();

    private OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserList(List<User> userList) {
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        String userInfo = String.format("%s %s,%s", user.getName(), user.getSurname(), user.getAge());
        holder.textViewUserInfo.setText(userInfo);
        int bgResId;
        if (user.getOnline()){
           bgResId =  R.drawable.circle_green;
        }else{
            bgResId =  R.drawable.circle_red;
        }
        Drawable bg = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);
        holder.onLineStatus.setBackground(bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUserClickListener!=null){
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface OnUserClickListener{
        void onUserClick(User user);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUserInfo;
        private View onLineStatus;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
        textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
        onLineStatus = itemView.findViewById(R.id.onLineStatus);
        }
    }
}
