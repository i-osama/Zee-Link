package com.zeeshan_s.zee_link.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshan_s.zee_link.Model.User;
import com.zeeshan_s.zee_link.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageView coverPic, editOption;
    CircleImageView profilePic;
    TextView usrName, usrEmail, usrPhone;
    ProgressBar progressBar;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String currentUser;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        coverPic = findViewById(R.id.proAcCoverImg);
        profilePic = findViewById(R.id.profile_image);
        editOption = findViewById(R.id.proAcEditOption);

        usrName = findViewById(R.id.proAcName);
        usrEmail = findViewById(R.id.proAcEmail);
        usrPhone = findViewById(R.id.proAcPhone);
        progressBar = findViewById(R.id.proAcProgressBar);

        intent = getIntent();

//        --------------- Getting data from firebase start --------------
        progressBar.setVisibility(View.VISIBLE); //-----Setting progressbar

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (intent.getBooleanExtra("onlyShow",false)){
                editOption.setVisibility(View.INVISIBLE);
                currentUser = intent.getStringExtra("userID");
            Log.i("TAG", "User from adapter: "+ currentUser);
        }else{
            currentUser = firebaseUser.getUid();
        }


        if (firebaseUser != null){
            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(currentUser);
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (!user.equals("")){
                    usrName.setText("Name: "+user.getUser_name());
                    usrEmail.setText("Email: "+user.getUser_email());
                    usrPhone.setText("Phone number: "+user.getUser_phone_number());

                    if (user.getUser_profile_img().equals("") || user.getUser_profile_img()==null){
                        profilePic.setImageResource(R.drawable.pofile_img);
                    }
                    else{
                        Glide.with(ProfileActivity.this).load(user.getUser_profile_img()).into(profilePic);
                    }
                    if (user.getUser_cover_img().equals("")){
                        coverPic.setImageResource(R.drawable.pofile_img);
                    }else{
                        Glide.with(ProfileActivity.this).load(user.getUser_cover_img()).into(coverPic);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        --------------- Getting data from firebase end --------------


        editOption.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            intent.putExtra("userId", currentUser);
            startActivity(intent);
        });

    }
}