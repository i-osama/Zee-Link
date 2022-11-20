package com.zeeshan_s.zee_link.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zeeshan_s.zee_link.AllAdapter.FragmentAdapter;
import com.zeeshan_s.zee_link.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ViewPager fragmentViewPager;

    FragmentManager fragmentManager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

//        --------------- Fragment Start -----------------
        fragmentViewPager = findViewById(R.id.mainAcViewPager);
        tabLayout = findViewById(R.id.mainAcTab);
        fragmentManager = getSupportFragmentManager();

        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragmentManager, 70);
        fragmentViewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(fragmentViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.account_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.chat_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.call_icon);

//        ------------------ Fragment End ----------------------

        requestDevicePermission();

        Toolbar toolbar = findViewById(R.id.mainAcToolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:
                firebaseAuth.signOut();
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, FlashActivity.class));
                finish();

                break;


            case R.id.menuProfile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//                finish();
                break;

            case R.id.menuAbout:
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    private void requestDevicePermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
}