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

import java.util.ArrayList;
import java.util.List;

public class RecieverAdapter extends RecyclerView.Adapter<RecieverAdapter.ViewHolder> {
    private static final String TAG = "RecieverAdapter";
    // creating variables for our ArrayList and context


        private List<String> userArrayList = new ArrayList<>();
        private Context context;
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
    String reciever = userArrayList.get(position);


//        reciever.setReceiver(username);
        holder.messageUser.setText(reciever);
        holder.messageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("user_shared_preference", MODE_PRIVATE);
                String username = sharedPreferences.getString("ConversationId", "");
                Bundle bundle = new Bundle();
                bundle.putString("ConersationID",username);
//                Navigation.findNavController(view).navigate(R.id.action_chats_to_personFragment);
            }
        });
//        reciever.setReceiver(username);
        Log.d(TAG, "onBindViewHolder: "+username);
        Log.d(TAG, "onBindViewHolder: "+reciever);
        holder.messageText.setText("");
        holder.messageTime.setText("");

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
         messageUser = itemView.findViewById(R.id.message_user);
            messageText = itemView.findViewById(R.id.message_text);
           messageTime = itemView.findViewById(R.id.message_time);
        }
    }
}
