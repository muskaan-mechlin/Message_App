package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.messageapp.Firebase.ChatMessage;
import com.example.messageapp.Firebase.MessageAdapter;
import com.example.messageapp.Firebase.User_Model;
import com.example.messageapp.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Chats extends Fragment {
    private static final String TAG = "Chats";
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    ImageButton msgBtn;
    TextView startView;
    List<ContactsInfo> contactsInfoList;
    FloatingActionButton floatButton;
    DatabaseReference databaseReference;
    MyCustomAdapter adapter = null;
    ListView listView;
    private FirebaseFirestore db;
    FirebaseListAdapter<User_Model> myAdapter;
    ValueEventListener valueEventListener;



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
        listView = root.findViewById(R.id.list_of_messages);
        listView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        String conversationId,message;
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        conversationId = sharedPreferences1.getString("ConversationId", "");
        message = sharedPreferences1.getString("Message", "");
        String displayName = sharedPreferences1.getString("DisplayName", "");


        ChatMessage newChatMessage = new ChatMessage(message, displayName,"text");
        User_Model userModel = new User_Model(displayName,FirebaseAuth.getInstance().getCurrentUser().getUid());

        Query query = databaseReference.child("conversations").child(conversationId).child("participants");


        query.addChildEventListener(new ChildEventListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                snapshot.getChildrenCount();
                User_Model message = snapshot.getValue(User_Model.class);
                Log.d(TAG, "onChildAdded: " + snapshot.getChildrenCount());
                Log.d(TAG, "onChildAdded: " + snapshot.getValue());
                Log.d(TAG, "onChildAdded: " + snapshot.getKey());
                Log.d(TAG, "onChildAdded: " + snapshot.getValue(User_Model.class).toString());
                Log.d(TAG, "onChildAdded: " + query.get().toString());

                Log.d(TAG, "onChildAdded: " + previousChildName);

                Log.d(TAG, "onChildAdded: " + message.getSender());
                Log.d(TAG, "onChildAdded: " + message.getReceiver());


            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                FirebaseListOptions<User_Model> options =
                new FirebaseListOptions.Builder<User_Model>()
                        .setLayout(R.layout.message1)
                        .setQuery(query, User_Model.class)
                        .build();

        myAdapter = new FirebaseListAdapter<User_Model>(options) {
            @Override
            protected void populateView(View v, User_Model model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                ImageButton messageImage = v.findViewById(R.id.message_image);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText("");
                messageUser.setText(model.getReceiver());
                Log.d(TAG, "populateView: "+model.getReceiver());

                // Format the date before showing it
                messageTime.setText("");
            }
        };
        listView.setAdapter(myAdapter);
//        User_Model userModel1 = new User_Model(displayName,FirebaseAuth.getInstance().getCurrentUser().getUid());
//        databaseReference.child("conversations").child(conversationId).child("participants").push().setValue(userModel1);
//

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chats_to_contactListFragment);
            }
        });
//
//

        return root;
    }



//





    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }
}
