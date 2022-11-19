package com.zeeshan_s.zee_link.AllAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zeeshan_s.zee_link.Fragment.CallFragment;
import com.zeeshan_s.zee_link.Fragment.ChatFragment;
import com.zeeshan_s.zee_link.Fragment.UserFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    String[] name = {"User", "Chat", "Call"};

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new UserFragment();
            case 1:
                return new ChatFragment();
            case 2:
                return new CallFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
