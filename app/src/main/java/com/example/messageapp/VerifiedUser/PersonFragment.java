package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.FloatProperty;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.messageapp.Firebase.ChatMessage;
import com.example.messageapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class PersonFragment extends Fragment {
    FloatingActionButton fab;
    String phonenumber;


    private static final String TAG = "PersonFragment";

    public PersonFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_person, container, false);
        fab = root.findViewById(R.id.floating_action_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)root.findViewById(R.id.edit);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
//
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .child("messages")
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName(),phonenumber, FirebaseAuth.getInstance()
                                .getCurrentUser().getDisplayName()
                                ,"text", String.valueOf(System.currentTimeMillis()),new Date().getTime())
                        );



                // Clear the input
                input.setText("");

//                FirebaseDatabase.getInstance()
//                        .getReference()
//                        .push()
//                        .setValue("Id" + FirebaseAuth.getInstance().getCurrentUser().getUid());

            }
        });
        return root;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_app_bar2, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem setting = menu.findItem(R.id.btnsett1);
        MenuItem call = menu.findItem(R.id.btn1);
        MenuItem video = menu.findItem(R.id.btn2);
        MenuItem name = menu.findItem(R.id.text);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        phonenumber = sharedPreferences1.getString("DisplayName", "");

        Log.d(TAG, "onMenuItemClick: "+phonenumber);

        name.setTitle(phonenumber);

        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {





                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnsett1) {
            return true;
        }
        if (id == R.id.btn1) {
            return true;
        }
        if (id == R.id.btn2) {
            return true;
        }
        if (id == R.id.text) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}