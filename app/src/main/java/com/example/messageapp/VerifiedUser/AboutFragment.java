package com.example.messageapp.VerifiedUser;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messageapp.R;

public class AboutFragment extends Fragment {
          TextView setView,availView,busyView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        setView = root.findViewById(R.id.textview1);
        availView = root.findViewById(R.id.textview3);
        busyView = root.findViewById(R.id.textview4);

        String available = availView.getText().toString();
        String busy = busyView.getText().toString();


        availView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(available);
            }
        });

        busyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(busy);
            }
        });


  return root;  }
}