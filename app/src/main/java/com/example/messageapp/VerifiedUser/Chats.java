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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.Firebase.ChatMessage;
import com.example.messageapp.Firebase.MessageAdapter;
import com.example.messageapp.Firebase.Reciever;
import com.example.messageapp.Firebase.RecieverAdapter;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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
    private RecyclerView usermodelview;
    private ArrayList<Reciever> userArrayList;
    RecieverAdapter recieverAdapter;
    ListView listView;
    private FirebaseFirestore db;
    FirebaseListAdapter<User_Model> myAdapter;
    ValueEventListener valueEventListener;
    LinearLayoutManager linearLayoutManager;
//    String UserID;



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
        usermodelview = root.findViewById(R.id.list_of_messages);

        db = FirebaseFirestore.getInstance();
       userArrayList = new ArrayList<>();
        usermodelview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        usermodelview.setItemViewCacheSize(10);
////        recieverAdapter = new RecieverAdapter(userArrayList,getActivity().getApplicationContext());
//
//        // setting adapter to our recycler view.
//        usermodelview.setAdapter(recieverAdapter);



        databaseReference = FirebaseDatabase.getInstance().getReference();
        String conversationId,message;
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        conversationId = sharedPreferences1.getString("ConversationId", "");
        message = sharedPreferences1.getString("Message", "");
        String displayName = sharedPreferences1.getString("DisplayName", "");
        String userid = sharedPreferences1.getString("Userid","");
        Log.d(TAG, "onCreateView: "+userid);




        ChatMessage newChatMessage = new ChatMessage(message, displayName,"text");
        User_Model userModel = new User_Model(displayName,FirebaseAuth.getInstance().getCurrentUser().getUid());
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Users").document(userid).collection("Contacts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        list.add(document.getString("Name"));
                        Log.d(TAG, "onComplete:1 "+document.getData());
                        Log.d(TAG, "onComplete:2 "+document.getId());
                        Log.d(TAG, "onComplete:3 "+document.getString("Name"));
//                        recieverAdapter = new RecieverAdapter(list,getActivity().getApplicationContext());
//
//                        // setting adapter to our recycler view.
//                        usermodelview.setAdapter(recieverAdapter);
                    }

                    Log.d(TAG, "onComplete: "+list.toString());
                    recieverAdapter = new RecieverAdapter(list,getActivity().getApplicationContext());
                    usermodelview.setAdapter(recieverAdapter);
                    }
                else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_chats_to_contactListFragment);
            }
        });


        return root;
    }

    }
//





//





//    @Override
//    public void onStart() {
//        super.onStart();
//        myAdapter.startListening();
//    }

