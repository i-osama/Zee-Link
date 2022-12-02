package com.zeeshan_s.zee_link.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshan_s.zee_link.AllAdapter.ChatAdapter;
import com.zeeshan_s.zee_link.Model.ChatModel;
import com.zeeshan_s.zee_link.Model.User;
import com.zeeshan_s.zee_link.R;
import com.zeeshan_s.zee_link.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    Intent intent;
    String myUserId, otherUserId;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    List<ChatModel> chatModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        otherUserId= intent.getStringExtra("userID");
        chatModelList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myUserId = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();

//        ------------------------ Setting data to the View -----------------------
        databaseReference.child("user").child(otherUserId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.chatProgressBar.setVisibility(View.VISIBLE);    // Setting progressBar

                User user = snapshot.getValue(User.class);

                binding.chatProfileName.setText(user.getUser_name());
                binding.chatProfileEmail.setText(user.getUser_email());

                if (user.getUser_profile_img().equals("")){
                    binding.chatProfileImg.setImageResource(R.drawable.pofile_img);
                }else {
                    Glide.with(ChatActivity.this).load(user.getUser_profile_img()).into(binding.chatProfileImg);
                }
                binding.chatProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //        ---------------- Receiving Messages from firebase storage --------------

        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModelList.clear();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);

                    if (chatModel.getSenderID().equals(myUserId) && chatModel.getReceiverID().equals(otherUserId) ||
                            chatModel.getReceiverID().equals(myUserId) && chatModel.getSenderID().equals(otherUserId)){

                        chatModelList.add(chatModel);
                    }
                }

                Log.i("TAG", "Before the Recycler option!----");
                setChatToAdapter(chatModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        ----------- Sending Message -----------
        binding.chatMsgSendBtn.setOnClickListener(view -> {
            String message = binding.chatMsgBox.getText().toString().trim();

            long currentMillis = System.currentTimeMillis();
            String chatKey = databaseReference.push().getKey();

//            --------------- Passing Data to the firebase -----------------
            ChatModel chatModel = new ChatModel(myUserId, otherUserId, message,chatKey, currentMillis);

            databaseReference.child("chat").child(chatKey).setValue(chatModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        binding.chatMsgBox.setText("");
                    }else {
                        Toast.makeText(ChatActivity.this, "Error in sending message", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });


//        ------------- Back btn -------------
        binding.chatBackImg.setOnClickListener(view -> {
            finish();
        });

    }

    private void setChatToAdapter(List<ChatModel> modelList) {
        Log.i("TAG", "Inside the Recycler UI setting function");
        Log.i("TAG", "Model:"+ modelList.get(0).getTimeMillis());
        ChatAdapter adapter = new ChatAdapter(ChatActivity.this, modelList, myUserId);
        binding.chatRecycler.setAdapter(adapter);

    }
}