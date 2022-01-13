package com.example.messageapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends Fragment {

    private static final String TAG = "MainScreen";
    ImageView imageView;
    ConstraintLayout constraintLayout;
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        mAuth.getCurrentUser().getPhoneNumber();

        Log.d(TAG, "onCreateView: "+mAuth.getCurrentUser());
        Log.d(TAG, "onCreateView: "+mAuth.getCurrentUser().getPhoneNumber());
        Log.d(TAG, "onCreateView: "+mAuth.getCurrentUser().getDisplayName());

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            Navigation.findNavController(view).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Navigation.findNavController(view).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
                }
            }, 6000);

        } else {
            // User is already signed in, show list of messages
            Navigation.findNavController(view).navigate(R.id.action_navigationMainScreen_to_nav_graph3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_screen, container, false);
        imageView = root.findViewById(R.id.imageview);

        constraintLayout = root.findViewById(R.id.constraint);

//        mAuth.getCurrentUser().getPhoneNumber();
//
//        Log.d(TAG, "onCreateView: "+mAuth.getCurrentUser());
//        Log.d(TAG, "onCreateView: "+mAuth.getCurrentUser().getPhoneNumber());
//        Log.d(TAG, "onCreateView: "+mAuth.getCurrentUser().getDisplayName());
//
//        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//            // Start sign in/sign up activity
//            Navigation.findNavController(getView()).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
//        } else {
//            // User is already signed in, show list of messages
//            Navigation.findNavController(getView()).navigate(R.id.action_navigationMainScreen_to_nav_graph3);
//        }

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                Navigation.findNavController(getView()).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
//            }
//        }, 6000);

      constraintLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Navigation.findNavController(view).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
          }
      });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
            }
        });
//        root.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Navigation.findNavController(view).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
//                return true;
//            }
//        });
   return root; }

}