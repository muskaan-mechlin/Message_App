package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.Firebase.ChatMessage;
import com.example.messageapp.Firebase.User_Model;
import com.example.messageapp.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public  class PersonFragment extends Fragment {
    FloatingActionButton fab;
    String phonenumber;
    DatabaseReference databaseReference;
    List<ChatMessage> listOfChatMessages;
    User_Model userModel;
    ChatMessage chatMessage;
    ListView listView;
    EditText editText;
    ImageButton attachButton;

    FirebaseListAdapter<ChatMessage> myAdapter;


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
        listView = root.findViewById(R.id.list_of_messages);
        editText = root.findViewById(R.id.edit);
        attachButton = root.findViewById(R.id.imagebutton);

        attachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String conversationId;
        conversationId = getArguments().getString("ConersationID");
        Log.d(TAG, "onCreateView: "+conversationId);


       Query query = databaseReference.child("conversations").child(conversationId).child("messages");
//       query.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                       ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
//               Log.d(TAG, "onDataChange: "+chatMessage.getMessageText());
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       });
//


       query.addChildEventListener(new ChildEventListener() {
           @SuppressLint("ResourceAsColor")
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               snapshot.getChildrenCount();
               ChatMessage message = snapshot.getValue(ChatMessage.class);
               Log.d(TAG, "onChildAdded: "+snapshot.getChildrenCount());
               Log.d(TAG, "onChildAdded: "+previousChildName);
               Log.d(TAG, "onChildAdded: "+message.getMessageText());

               User_Model userModel = new User_Model(phonenumber,FirebaseAuth.getInstance().getCurrentUser().getUid());




               if (userModel.getSender().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid()) ){
                   listView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

                   Log.d(TAG, "onChildAdded: Message"+message.getMessageText());
        }
        else{

            listView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//            listView.setBackgroundColor(R.color.red);

        }
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
        FirebaseListOptions<ChatMessage> options =
                new FirebaseListOptions.Builder<ChatMessage>()
                        .setLayout(R.layout.message)
                        .setQuery(query, ChatMessage.class)
                        .build();

        myAdapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                Log.d(TAG, "populateView: Messagetext "+model.getMessageText());
                // Format the date before showing it
                messageTime.setText(DateFormat.format( "hh:ss a", model.getMessageTime()));
            }
        };
        listView.setAdapter(myAdapter);

//       displayMessages();

//

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = root.findViewById(R.id.edit);
                messageText();
                SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
             String username = sharedPreferences3.getString("name", "");

             ChatMessage newChatMessage = new ChatMessage(input.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getUid(),"text");

//             listOfChatMessages.add(newChatMessage);
             databaseReference.child("conversations").child(conversationId).child("messages").push().setValue(newChatMessage);


                User_Model userModel = new User_Model(phonenumber,FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.child("conversations").child("1").child("participants").push().setValue(userModel);
                Log.d(TAG, "onClick: "+userModel.getReceiver());
                Log.d(TAG, "onClick: "+databaseReference.child("conversations").child("1").child("participants").getDatabase());
                Log.d(TAG, "onClick: "+databaseReference.child("conversations").child("1").child("participants").child(userModel.getReceiver()));


                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
//
//                FirebaseDatabase.getInstance()
//                        .getReference()
//                        .push()
//                        .child("messages")
//                        .setValue(new ChatMessage(input.getText().toString(),
//                                username,phonenumber, username
//                                ,"text", String.valueOf(System.currentTimeMillis()),new Date().getTime(),FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        );



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

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getView().getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog6);

        TextView document = bottomSheetDialog.findViewById(R.id.textview1);
        TextView gallery = bottomSheetDialog.findViewById(R.id.textview2);
        TextView camera = bottomSheetDialog.findViewById(R.id.textview3);
        TextView audio = bottomSheetDialog.findViewById(R.id.textview4);
        TextView payment = bottomSheetDialog.findViewById(R.id.textview5);
        TextView location = bottomSheetDialog.findViewById(R.id.textview6);
        TextView contact = bottomSheetDialog.findViewById(R.id.textview7);
        ImageButton btn1 = bottomSheetDialog.findViewById(R.id.imagebtn);
        ImageButton btn2 = bottomSheetDialog.findViewById(R.id.imagebtn1);
        ImageButton btn3 = bottomSheetDialog.findViewById(R.id.imagebtn2);
        ImageButton btn4 = bottomSheetDialog.findViewById(R.id.imagebtn3);
        ImageButton btn5 = bottomSheetDialog.findViewById(R.id.imagebtn4);
        ImageButton btn6 = bottomSheetDialog.findViewById(R.id.imagebtn5);
        ImageButton btn7 = bottomSheetDialog.findViewById(R.id.imagebtn6);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }

    public void messageText() {

        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Message",editText.getText().toString());
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: "+editText.getText().toString());

//        Log.d(TAG, "details: "+preferences.getString("ConversationId",""));
    }

//String message2 = chatMessage.getMessageText();
//     public void displayMessages() {
//         Query query = databaseReference.child("conversations").child("1").child("messages");
//
//         FirebaseListOptions<ChatMessage> options =
//                 new FirebaseListOptions.Builder<ChatMessage>()
//                         .setLayout(R.layout.message)
//                         .setQuery(query, ChatMessage.class)
//                         .build();
//
//         myAdapter = new FirebaseListAdapter<ChatMessage>(options) {
//             @Override
//             protected void populateView(View v, ChatMessage model, int position) {
//                 // Get references to the views of message.xml
//                 TextView messageText = (TextView) v.findViewById(R.id.message_text);
//                 TextView messageTime = (TextView) v.findViewById(R.id.message_time);
//
//                 // Set their text
//                 messageText.setText(model.getMessageText());
//                 Log.d(TAG, "populateView: Messagetext "+model.getMessageText());
//                 // Format the date before showing it
//                 messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));
//             }
//         };
//         listView.setAdapter(myAdapter);









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
    @Override
    public void onStart() {
        super.onStart();
        myAdapter.startListening();
    }



}