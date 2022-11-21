package com.zeeshan_s.zee_link.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zeeshan_s.zee_link.R;
import com.zeeshan_s.zee_link.databinding.ActivityChatBinding;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}