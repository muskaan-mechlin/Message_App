package com.example.messageapp.Firebase;

import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.example.messageapp.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MessageAdapter extends FirebaseListAdapter<ChatMessage> {



    public MessageAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, R.layout.message, ref);

    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        TextView messageText = v.findViewById(R.id.message_text);
        TextView messageUser = v.findViewById(R.id.message_user);
        TextView messageTime = v.findViewById(R.id.message_time);
        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));



    }
}
