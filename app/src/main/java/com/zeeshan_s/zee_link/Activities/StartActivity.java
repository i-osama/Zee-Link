package com.zeeshan_s.zee_link.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.zeeshan_s.zee_link.R;
import com.zeeshan_s.zee_link.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {
    private ActivityStartBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        //            ---------------- Login button -------------
        binding.startAcLoginBtn.setOnClickListener(view -> {

            binding.startAcProgressBar.setVisibility(View.VISIBLE);
            binding.startAcLoginBtn.setVisibility(View.INVISIBLE);

            String email = binding.startAcEmailEdt.getText().toString().trim();
            String password = binding.startAcPassword.getText().toString();

            if (email.equals("")){binding.startAcEmailEdt.setError("Email address is needed");}
            else if (password.equals("")){binding.startAcPassword.setError("Please enter the password!");}
            else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            binding.startAcProgressBar.setVisibility(View.GONE);
                            binding.startAcLoginBtn.setVisibility(View.VISIBLE);

                            Toast.makeText(StartActivity.this, "Login Success!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(StartActivity.this, MainActivity.class));
                            finish();
                        } else {
                            showAlert("Error!!", task.getException().getLocalizedMessage());

                            binding.startAcProgressBar.setVisibility(View.GONE);
                            binding.startAcLoginBtn.setVisibility(View.VISIBLE);

                        }
                    }
                });
                }
        });

        binding.registerOption.setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, UserRegisterActivity.class));
        });
    }

    private void showAlert(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(R.drawable.warning_icon);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}