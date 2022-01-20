package com.example.messageapp.Firebase;

import static com.google.api.ResourceProto.resource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.messageapp.R;
import com.example.messageapp.VerifiedUser.ContactsInfo;
import com.example.messageapp.VerifiedUser.MyCustomAdapter;

import java.util.List;

public class MessageAdapter extends ArrayAdapter {
    private final List contactsInfoList;
    private final Context context;

    public MessageAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.contactsInfoList = objects;
        this.context = context;
    }

    public static class ViewHolder {
        TextView displayName;
        TextView messageText;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        com.example.messageapp.Firebase.MessageAdapter.ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.message1, null);

            holder = new com.example.messageapp.Firebase.MessageAdapter.ViewHolder();
            holder.displayName = convertView.findViewById(R.id.message_user);
            holder.messageText = convertView.findViewById(R.id.message_text);
            convertView.setTag(holder);
        } else {
            holder = (com.example.messageapp.Firebase.MessageAdapter.ViewHolder) convertView.getTag();
        }

        ContactsInfo contactsInfo = (ContactsInfo) contactsInfoList.get(position);
        holder.displayName.setText(contactsInfo.getDisplayName());
        holder.messageText.setText("");

        return convertView;
    }

}





