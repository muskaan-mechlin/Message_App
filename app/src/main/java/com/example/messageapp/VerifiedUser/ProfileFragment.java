package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    ImageButton nameBtn,aboutBtn,phoneBtn,name1Btn,about1Btn,profileBtn;
    TextInputEditText aboutEt,nameEt,phoneEt;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        nameBtn = root.findViewById(R.id.imagebtn1);
        profileBtn = root.findViewById(R.id.imagebtn);
        name1Btn = root.findViewById(R.id.imagebtn2);
        aboutBtn = root.findViewById(R.id.imagebtn3);
        about1Btn = root.findViewById(R.id.imagebtn4);
        phoneBtn = root.findViewById(R.id.imagebtn5);
        aboutEt = root.findViewById(R.id.about);
        nameEt = root.findViewById(R.id.name);
        phoneEt = root.findViewById(R.id.phone);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String username = sharedPreferences.getString("name", "");

       nameEt.setText(username);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String phonenumber = sharedPreferences1.getString("phonenumber", "");

       phoneEt.setText(phonenumber);

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String status = sharedPreferences2.getString("about", "");

        aboutEt.setText(status);
        Log.d(TAG, "onCreateView: "+status);
        Log.d(TAG, "onCreateView: "+aboutEt);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog1();
            }
        });




        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        nameEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        name1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_aboutFragment);
            }
        });
        about1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_aboutFragment);
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_numberFragment);
            }
        });

        return root;
    }
    private void showBottomSheetDialog1() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getView().getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog5);

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


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getView().getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

       TextInputEditText name = bottomSheetDialog.findViewById(R.id.name);
        Button btn1 = bottomSheetDialog.findViewById(R.id.textButton);
        Button btn2 = bottomSheetDialog.findViewById(R.id.textButton1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Clicked ", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name",name.getText().toString());

                editor.apply();
                editor.commit();
                Log.d(TAG, "onClick: "+name);
                Log.d(TAG, "onClick: "+name.getText().toString());
                bottomSheetDialog.dismiss();

            }

        });

        bottomSheetDialog.show();


    }

    }
