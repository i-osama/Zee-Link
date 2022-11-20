package com.zeeshan_s.zee_link.AllViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeeshan_s.zee_link.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView profileImg;
    public ImageView msgImg, editImg;
    public TextView userName, userEmail;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImg = itemView.findViewById(R.id.userProfileImg);
        msgImg = itemView.findViewById(R.id.msgImg);
        editImg = itemView.findViewById(R.id.proAcEditOption);
        userName = itemView.findViewById(R.id.userName);
        userEmail = itemView.findViewById(R.id.userEmail);
    }
}
