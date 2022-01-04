package com.example.messageapp.Authentication;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;


public class ProfileInfo extends Fragment {
    private static final String TAG = "ProfileInfo";
    ImageButton btn ;
    ImageView imageView;
    Button nextbutton;
    TextInputEditText nameEt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile_info, container, false);
        btn = root.findViewById(R.id.image);
        imageView = root.findViewById(R.id.imageview);
        nextbutton = root.findViewById(R.id.nextbtn);
        nameEt = root.findViewById(R.id.edittext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showBottomSheetDialog();
            }
        });
        detailsPhone();
        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileInfo_to_nav_graph3);

            }
        });
//
  return  root;  }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getView().getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog1);

        TextView name = bottomSheetDialog.findViewById(R.id.textview);
        TextView name1 = bottomSheetDialog.findViewById(R.id.textview1);
        TextView name2 = bottomSheetDialog.findViewById(R.id.textview2);
        ImageButton btn1 = bottomSheetDialog.findViewById(R.id.imagebtn);
        ImageButton btn2 = bottomSheetDialog.findViewById(R.id.imagebtn1);

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

    public void detailsPhone() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",nameEt.getText().toString());
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: "+nameEt.getText().toString());

        Log.d(TAG, "details: "+preferences.getString("name",""));
    }


}
