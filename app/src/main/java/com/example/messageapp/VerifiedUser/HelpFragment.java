package com.example.messageapp.VerifiedUser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.messageapp.R;


public class HelpFragment extends Fragment {

    ImageButton contactBtn;
    TextView contactView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        contactBtn = root.findViewById(R.id.imagebtn1);
        contactView= root.findViewById(R.id.textview1);

        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_helpFragment_to_contactFragment);
            }
        });

        contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_helpFragment_to_contactFragment);
            }
        });
    return root;}
}