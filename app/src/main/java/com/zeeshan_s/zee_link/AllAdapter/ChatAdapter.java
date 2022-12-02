package com.zeeshan_s.zee_link.AllAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zeeshan_s.zee_link.AllViewHolder.ChatViewHolder;
import com.zeeshan_s.zee_link.Model.ChatModel;
import com.zeeshan_s.zee_link.R;

import java.sql.Date;

import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private Context context;
    private List<ChatModel> modelList;
    private String myUserID;
    final int RIGHT_UI=1;
    final int LEFT_UI=2;

    public ChatAdapter(Context context, List<ChatModel> modelList, String myUserID) {
        Log.i("TAG", "Inside the adapter constructor");
        this.context = context;
        this.modelList = modelList;
        this.myUserID = myUserID;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "View binding---");
        View view;
        if (viewType == RIGHT_UI){
            view = LayoutInflater.from(context).inflate(R.layout.chat_right_decoration, parent, false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_left_decoration, parent, false);
        }

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatModel model = modelList.get(position);

        Log.i("TAG", "It comes to the Main message--");
        holder.mainMsg.setText(model.getMessage());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(model.getTimeMillis());

        holder.msgTime.setText(simpleDateFormat.format(date));

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (modelList.get(position).getSenderID().equals(myUserID)){
            return RIGHT_UI;
        }else {
            return LEFT_UI;
        }
    }
}
