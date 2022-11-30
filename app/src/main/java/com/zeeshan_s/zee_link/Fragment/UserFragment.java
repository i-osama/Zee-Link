package com.zeeshan_s.zee_link.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zeeshan_s.zee_link.AllAdapter.UserAdapter;
import com.zeeshan_s.zee_link.Model.User;
import com.zeeshan_s.zee_link.R;
import com.zeeshan_s.zee_link.databinding.FragmentUserBinding;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }

    ArrayList<User> userList;
    DatabaseReference userDatabaseRef;
    FirebaseUser firebaseUser;
    FragmentUserBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(getLayoutInflater(), container, false);

        userList = new ArrayList<>();
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("user");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    if (!user.getUser_id().equals(firebaseUser.getUid())) {
                        userList.add(user);
                    }
                }
//                ----------------------- Recycler Adapter --------------
                UserAdapter adapter = new UserAdapter(userList, requireActivity());
                binding.userRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return binding.getRoot();
    }
}