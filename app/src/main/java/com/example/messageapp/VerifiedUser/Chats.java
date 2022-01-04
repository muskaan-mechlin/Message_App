package com.example.messageapp.VerifiedUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.messageapp.R;

public class Chats extends Fragment {
    ImageButton msgBtn;

    public Chats() {
        // required empty public constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chats, container, false);
        msgBtn = root.findViewById(R.id.imagbtn);

        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_chats_to_contactListFragment);
            }
        });
        return root;
    }
}
