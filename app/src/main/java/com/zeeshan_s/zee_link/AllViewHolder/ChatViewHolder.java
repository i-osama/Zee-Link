package com.zeeshan_s.zee_link.AllViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeeshan_s.zee_link.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    public TextView mainMsg, msgTime;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        mainMsg = itemView.findViewById(R.id.conversation);
        msgTime = itemView.findViewById(R.id.conversationTime);
    }
}
