package com.zeeshan_s.zee_link.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zeeshan_s.zee_link.R;
import com.zeeshan_s.zee_link.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    Intent intent;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        userId= intent.getStringExtra("userID");
        Log.i("TAG", "User--> "+ userId);



    }
}