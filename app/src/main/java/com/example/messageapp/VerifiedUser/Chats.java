package com.example.messageapp.VerifiedUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.messageapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Chats extends Fragment {
    private static final String TAG = "Chats";
    ImageButton msgBtn;
    TextView startView;
    FloatingActionButton floatButton;


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
        startView = root.findViewById(R.id.start);
        floatButton = root.findViewById(R.id.floating_action_button);
        ListView listView = root.findViewById(R.id.list_of_messages);




//        msgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_chats_to_contactListFragment);
//            }
//        });
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                Navigation.findNavController(getView()).navigate(R.id.action_chats_to_contactListFragment);
//            }
//        }, 6000);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chats_to_contactListFragment);
            }
        });
//
//        startView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_chats_to_contactListFragment);
//            }
//        });

        return root;
    }
}
