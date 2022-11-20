package com.zeeshan_s.zee_link;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zeeshan_s.zee_link.Model.User;
import com.zeeshan_s.zee_link.databinding.ActivityEditProfileBinding;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    Intent intent;
    DatabaseReference databaseReference;
    StorageReference storageReferenceProfile, storageReferenceCover;

    boolean isProfileImg= false, isCoverImg = false, isImgChanged = false;
    String currentUserId;
    String profileURL, coverURL;
    Uri profileImgUri, coverImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        intent = getIntent();
        currentUserId= intent.getStringExtra("userId");


        if (currentUserId != null){

            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(currentUserId);
            storageReferenceProfile = FirebaseStorage.getInstance().getReference("Profile").child(currentUserId);
            storageReferenceCover = FirebaseStorage.getInstance().getReference("Cover");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    if (!user.equals("")){
                        binding.editName.setText(user.getUser_name());
                        binding.editEmail.setText(user.getUser_email());
                        binding.editPhone.setText(user.getUser_phone_number());

//                        ************** Setting up user images ***************
                        if (user.getUser_profile_img().equals("") || user.getUser_profile_img()==null){
                            binding.profileImage.setImageResource(R.drawable.pofile_img);
                        }
                        else{
                            profileURL = user.getUser_profile_img();
                            Glide.with(EditProfileActivity.this).load(user.getUser_profile_img()).into(binding.profileImage);
                        }
                        if (user.getUser_cover_img().equals("")){
                            binding.proAcCoverImg.setImageResource(R.drawable.pofile_img);
                        }else{
                            coverURL = user.getUser_cover_img();
                            Glide.with(EditProfileActivity.this).load(user.getUser_cover_img()).into(binding.proAcCoverImg);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


//-------------------------- Edit Option --------------------------------
        binding.editSaveOption.setOnClickListener(view -> {
            String name, email, phone;
            name = binding.editName.getText().toString();
            email = binding.editEmail.getText().toString();
            phone = binding.editPhone.getText().toString();

//            -------------- Sending Image data to the database [start] ----------
            if (isProfileImg){
                storageReferenceProfile.child("user-profile").putFile(profileImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(EditProfileActivity.this, "Profile image changed", Toast.LENGTH_SHORT).show();
                            Log.i("TAG", "---Succeed--- ");
                            storageReferenceProfile.child("user-profile").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                    Log.i("TAG", "URI-- "+uri);
                                    profileURL = String.valueOf(uri);
                                    isImgChanged = true;
                                    sendDataToServer(name,email, phone);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("TAG", "Failed profile img: "+ e.getLocalizedMessage());
                                }
                            });
                        }
                    }
                });
            }

//            ********************************** For cover image *****************************
            if (isCoverImg){
                storageReferenceCover.child("user-cover").putFile(coverImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditProfileActivity.this, "Cover image changed", Toast.LENGTH_SHORT).show();
                            storageReferenceCover.child("user-cover").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    coverURL = String.valueOf(uri);
                                    isImgChanged = true;
                                    sendDataToServer(name, email, phone);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("TAG", "Error in cover image");
                                }
                            });
                        }

                    }
                });
            }

//            --------------------- Sending Image data to the database [End] ---------------------------
            if (isImgChanged== false) {
                sendDataToServer(name, email, phone);
            }

        });

//        -------------- profile and cover image start -------------
        binding.profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            isProfileImg = true;
            startActivityForResult(intent, 101);
        });

        binding.proAcCoverImg.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            isCoverImg = true;
            startActivityForResult(intent, 102);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if (resultCode == RESULT_OK){
                if (data != null){
                    if (isProfileImg) {
                        profileImgUri = data.getData();
                        binding.profileImage.setImageURI(profileImgUri);
                    }
                }
            }
        }

        if (requestCode == 102 && resultCode== RESULT_OK && data != null){
            if (isCoverImg){
                coverImgUri = data.getData();
                binding.proAcCoverImg.setImageURI(coverImgUri);
            }

        }
    }
    //        -------------- profile and cover image end -------------

    //    ------------------------- Sending data to the server -------------------------------
    private void sendDataToServer(String name, String email, String phone) {

        if (name.isEmpty()){
            binding.editName.setError("");
        } else if (email.isEmpty()){
            binding.editEmail.setError("");
        } else if (phone.isEmpty()){
            binding.editPhone.setError("");
        }else{
            HashMap<String, Object> updateData = new HashMap<>();

            updateData.put("user_name", name);
            updateData.put("user_email", email);
            updateData.put("user_phone_number", phone);
            updateData.put("user_id", currentUserId);

            if (profileURL != null){updateData.put("user_profile_img", profileURL);}
            else{
                updateData.put("user_profile_img", "");
            }
            if (coverURL != null){updateData.put("user_cover_img", coverURL);}
            else{
                updateData.put("user_cover_img", "");
            }

            databaseReference.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(EditProfileActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(EditProfileActivity.this, "Error! couldn't update data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}