package com.zeeshan_s.zee_link.AllAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zeeshan_s.zee_link.Activities.ProfileActivity;
import com.zeeshan_s.zee_link.AllViewHolder.UserViewHolder;
import com.zeeshan_s.zee_link.Model.User;
import com.zeeshan_s.zee_link.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    ArrayList<User> userList;
    Context context;

    public UserAdapter(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_recycler_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getUser_name());
        holder.userEmail.setText(user.getUser_email());
        if (user.getUser_profile_img().equals("")){
            holder.profileImg.setImageResource(R.drawable.pofile_img);
        }else {
            Glide.with(context).load(user.getUser_profile_img()).into(holder.profileImg);
        }

        holder.msgImg.setOnClickListener(view -> {

        });

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("onlyShow", true);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
