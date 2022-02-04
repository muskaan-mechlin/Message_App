package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    //This is our tablayout
    private TabLayout tabLayout;
    TabItem itemchat;

    //This is our viewPager
    private ViewPager2 viewPager;
    private FirebaseFirestore db;
    ViewPagerAdapter viewPagerAdapter;
    private final ArrayList<Fragment> arrayList = new ArrayList<>();
    String UserID;


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
        db = FirebaseFirestore.getInstance();
        checkUserExists();
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

                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(),requireView());


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
                        if (id == R.id.six) {
                            Navigation.findNavController(getView()).navigate(R.id.action_chatFragment_to_settingFragment);
                            return true;
                        }
                        if (id == R.id.four) {
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
        if (id == R.id.searchbtn) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkUserExists() {
        CollectionReference dbUser = db.collection("Users");
        String currentUserPhoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d(TAG, "checkExistingUser:PhoneNumber " + currentUserPhoneNo);
        Log.d(TAG, "checkUserExists: Number " + currentUserPhoneNo);
        Query query1 = dbUser.whereEqualTo("FullPhoneNumber", currentUserPhoneNo);
        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean isUserExistAlready = false;
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: result ");
                        Log.d(TAG, "onComplete:usersId  " + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: user " + queryDocumentSnapshot.getData());
                        Log.d(TAG, "onComplete:user " + queryDocumentSnapshot.getData().get("FullPhoneNumber"));

                        UserID = queryDocumentSnapshot.getId();
                        Log.d(TAG, "onComplete: " + UserID);
                        detailsContacts();


                        isUserExistAlready = true;


                    }
                    if(!isUserExistAlready) {
                        Log.d(TAG, "onComplete: UsernotExist");
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
                        String username = sharedPreferences.getString("name", "");
                        String phonenumber = sharedPreferences.getString("phonenumber", "");
                        createUserDetails(currentUserPhoneNo,"+91",username,phonenumber);


                    }

                }


            }

        });


    }

    private void createUserDetails(String fullphoneno,String countryCode,String name,String phoneNumber) {
        Map<String, Object> userDocument = new HashMap<>();
        userDocument.put("FullPhoneNumber",fullphoneno);
        userDocument.put("CountryCode",countryCode);
        userDocument.put("Name",name);
        userDocument.put("PhoneNumber",phoneNumber);
        db.collection("Users")
                .add(userDocument)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: user " + documentReference.getId());
                        Log.d(TAG, "onSuccess: ID  "+documentReference.getId());
                        UserID = documentReference.getId();
                        detailsContacts();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void detailsContacts(){
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Userid", UserID);

        Log.d(TAG, "detailsContacts:IIII "+UserID);
        editor.apply();
        editor.commit();

    }

}