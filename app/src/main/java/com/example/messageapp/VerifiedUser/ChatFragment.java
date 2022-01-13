package com.example.messageapp.VerifiedUser;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;

import java.util.ArrayList;


public class ChatFragment extends Fragment {

    //This is our tablayout
    private TabLayout tabLayout;
    TabItem itemchat;

    //This is our viewPager
    private ViewPager2 viewPager;

    ViewPagerAdapter viewPagerAdapter;
    private final ArrayList<Fragment> arrayList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        //Initializing the tablayout
        tabLayout = root.findViewById(R.id.tablayout);
        itemchat = root.findViewById(R.id.chattab);
//        Button button = root.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_chatFragment_to_chats);
//            }
//        });

//        itemchat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Navigation.findNavController(view).navigate(R.id.action_chatFragment_to_chats);
//            }
//        });

        //Adding the tabs using addTab() method

//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_camera_alt_24));
//        tabLayout.addTab(tabLayout.newTab().setText("Chats"));
//        tabLayout.addTab(tabLayout.newTab().setText("Status"));
//        tabLayout.addTab(tabLayout.newTab().setText("Calls"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        //Initializing viewPager
        viewPager = root.findViewById(R.id.viewpager);
        // add Fragments in your ViewPagerFragmentAdapter class
        arrayList.add(new CameraFragment());
        arrayList.add(new Chats());

        arrayList.add(new Status());
        arrayList.add(new Calls());
//
     viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(), getLifecycle());
        // set Orientation in your ViewPager2
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   viewPager.setCurrentItem(tab.getPosition());
                                                   switch (tab.getPosition()) {
                                                       case 0:
                                                           return;
                                                       case 1:
                                                           switch (tab.getPosition()) {
                                                               case 0:
                                                                   return;
                                                               case 1:
                                tabLayout.getTabAt(1).select();
                        Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_chats);
//                                                                   Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_chats);


                                                           }
                                                   }


                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {

                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {


                                               }
                                           });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


        return root;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_app_bar1, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem setting = menu.findItem(R.id.btnsett1);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), getView());


                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());


                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity().getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        int id = item.getItemId();
                        if (id == R.id.three) {
                            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_linkedFragment);
                                  return true;

                        }
                        if (id == R.id.six){
                            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_settingFragment);
                            return true;
                        }
                        if (id == R.id.four){
                            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_starredFragment);
                            return true;
                        }
                        if (id == R.id.one) {
                            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_groupFragment);
                            return true;

                        }

                        if (id == R.id.two) {
                            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_broadcastFragment);
                            return true;

                        }

                        return true;
                    }
                                                 });



                popup.show(); //showing popup menu
                return true;


            }
        });








        MenuItem search = menu.findItem(R.id.searchbtn);
        search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnsett1) {


        }
        if (id == R.id.searchbtn){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}