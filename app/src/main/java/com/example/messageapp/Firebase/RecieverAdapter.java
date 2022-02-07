package com.example.messageapp.Firebase;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecieverAdapter extends RecyclerView.Adapter<RecieverAdapter.ViewHolder> {
    private static final String TAG = "RecieverAdapter";
String reciever1,rphoneno,ConversationID;
    // creating variables for our ArrayList and context


        private List<String> userArrayList = new ArrayList<>();
        private final Context context;
    public RecieverAdapter(List<String> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecieverAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message1, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecieverAdapter.ViewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_shared_preference", MODE_PRIVATE);
        String username = sharedPreferences.getString("DisplayName", "");
        String uid = sharedPreferences.getString("Userid", "");
        String reciever = userArrayList.get(position);


//        reciever.setReceiver(username);
        holder.messageUser.setText(reciever);
        holder.messageUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:RR " + holder.messageUser.getText());
                reciever1 = (String) holder.messageUser.getText();
                Log.d(TAG, "onClick: 1 " + reciever1);
                detailsName();
//                db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Contacts").





//
                Log.d(TAG, "onBindViewHolder: " + username);
                Log.d(TAG, "onBindViewHolder: " + reciever);
                holder.messageText.setText("");
                holder.messageTime.setText("");

            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView messageUser;
        private final TextView messageText;
        private final TextView messageTime;
        private final FirebaseFirestore db;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            db = FirebaseFirestore.getInstance();
         messageUser = itemView.findViewById(R.id.message_user);
            messageText = itemView.findViewById(R.id.message_text);
           messageTime = itemView.findViewById(R.id.message_time);
            Log.d(TAG, "ViewHolder: "+messageUser);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: 1111 "+itemView.findViewById(R.id.message_user));
                    Log.d(TAG, "onClick:212 "+messageUser.getText().toString());
                    String otherUser = messageUser.getText().toString();
                    Log.d(TAG, "onClick: otheruser "+otherUser);
                    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    db.collection("Users").document(currentUser).collection("Contacts").whereEqualTo("Name",otherUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                Log.d(TAG, "onSuccess: 1 "+documentSnapshot.getId());
                                Log.d(TAG, "onSuccess:2 "+documentSnapshot.getString("PhoneNumber"));
                                String abc = documentSnapshot.getString("PhoneNumber");
                                Log.d(TAG, "onSuccess: 3 "+abc);
                               rphoneno = documentSnapshot.getString("PhoneNumber");
                               detailsPhone();
                                Log.d(TAG, "onSuccess:4 "+rphoneno);
                                break;
                            }
                        }
                    });
                    checkConversationExists(view);
                }
            });
        }
    }

    private void checkConversationExists(View v) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        SharedPreferences sharedPreferences1 = context.getSharedPreferences("user_shared_preference", MODE_PRIVATE);
        String recphone = sharedPreferences1.getString("Rphoneno", "");
        Log.d(TAG, "checkConversationExists: rrr" +recphone);
        Log.d(TAG, "checkConversationExists: rrn "+rphoneno);


        CollectionReference dbConversation = db.collection("Conversations");
        String currentUserPhoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d(TAG, "checkExistingConversation: "+currentUserPhoneNo);
        String otherUserPhoneNo = recphone;
        Set<String> setOfExistingPhoneNumber = new HashSet<>();
        setOfExistingPhoneNumber.add(currentUserPhoneNo);
        setOfExistingPhoneNumber.add(otherUserPhoneNo);
        Log.d(TAG, "checkExistingConversation: 10909 "+recphone);
        Query query = dbConversation.whereArrayContainsAny("ParticipantsPhoneNo", Arrays.asList(currentUserPhoneNo,recphone));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: conversation 11 " + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: 22 " + queryDocumentSnapshot.getData());
                        Log.d(TAG, "onComplete: 33 " + queryDocumentSnapshot.getData().get("ParticipantsPhoneNo"));

                        ConversationID = queryDocumentSnapshot.getId();
//                        detailsConversationId();

                        Set<String> setOfDatabasePhoneNumber = new HashSet<>();
                        setOfDatabasePhoneNumber.addAll((Collection<? extends String>) queryDocumentSnapshot.getData().get("ParticipantsPhoneNo"));
                        Log.d(TAG, "onComplete: SetMatches  " + setOfDatabasePhoneNumber.containsAll(setOfExistingPhoneNumber));
                        if (setOfDatabasePhoneNumber.containsAll(setOfExistingPhoneNumber)) {
                            navigateWithConversationID(queryDocumentSnapshot.getId(), v);

//                            break;
                        }

                    }

                }

            }

        });

    }

    private void navigateWithConversationID(String id,View v) {
        Log.d(TAG, "navigateWithConversationId: "+id);
        Bundle bundle = new Bundle();
        bundle.putString("ConersationID1", id);
        Log.d(TAG, "navigateWithConversationId: "+bundle);
        Navigation.findNavController(v).navigate(R.id.action_chats_to_personFragment,bundle);

    }

    public void detailsPhone() {
        SharedPreferences preferences = context.getSharedPreferences("user_shared_preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("RecieverName", reciever1);
        editor.putString("Rphoneno",rphoneno);

        editor.apply();
        editor.commit();
        Log.d(TAG, "detailsPhone: 1 "+preferences.getString("Rphoneno",""));
    }
    public void detailsName() {
        SharedPreferences preferences = context.getSharedPreferences("user_shared_preference", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("RecieverName", reciever1);
        editor.apply();
        editor.commit();

        Log.d(TAG, "detailsName: "+preferences.getString("RecieverName",""));
    }

}

