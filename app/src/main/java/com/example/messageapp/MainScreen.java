package com.example.messageapp;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainScreen extends Fragment {

    ImageView imageView;
    ConstraintLayout constraintLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main_screen, container, false);
        imageView = root.findViewById(R.id.imageview);
        constraintLayout = root.findViewById(R.id.constraint);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Navigation.findNavController(getView()).navigate(R.id.action_navigationMainScreen_to_nav_graph2);
            }
        }, 7000);

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