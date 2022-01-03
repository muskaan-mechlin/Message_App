package com.example.messageapp.VerifiedUser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.legacy.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;


public class ViewPagerAdapter extends FragmentStateAdapter  {

        private final ArrayList<Fragment> arrayList = new ArrayList<>();


        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0 :
                    return new CameraFragment();
                case 1:
                    return new Chats();
                case 2:
                    return new Status();
                case 3:
                    return new Calls();
//                default:
//                    return new Chats();
            }

       return null; }

        @Override
        public int getItemCount() {
            return 4;
        }
    }