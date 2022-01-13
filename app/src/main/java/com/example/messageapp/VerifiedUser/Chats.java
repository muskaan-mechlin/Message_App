package com.example.messageapp.VerifiedUser;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.messageapp.Firebase.ChatMessage;
import com.example.messageapp.Firebase.MessageAdapter;
import com.example.messageapp.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

public class Chats extends Fragment {
    private static final String TAG = "Chats";
    ImageButton msgBtn;
    TextView startView;
    FloatingActionButton floatButton;
    MessageAdapter messageAdapter;

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
        listView.setAdapter(messageAdapter);



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
