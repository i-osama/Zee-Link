package com.zeeshan_s.zee_link;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.MailTo;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FlashActivity extends AppCompatActivity {

    TextView login, register;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        login = findViewById(R.id.flash_ac_loginOption);
        register = findViewById(R.id.flash_ac_registerOption);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(FlashActivity.this, MainActivity.class));
            finish();
        }

        login.setOnClickListener(view -> {
            startActivity(new Intent(FlashActivity.this, StartActivity.class));
//            finish();
        });
        register.setOnClickListener(view -> {
            startActivity(new Intent(FlashActivity.this, UserRegisterActivity.class));
//            finish();
        });


    }
}