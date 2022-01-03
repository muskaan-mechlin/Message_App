package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;


public class ProfileFragment extends Fragment {
    ImageButton nameBtn,aboutBtn,phoneBtn;
    TextInputEditText aboutEt,nameEt;



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
        aboutBtn = root.findViewById(R.id.imagebtn3);
        phoneBtn = root.findViewById(R.id.imagebtn5);
        aboutEt = root.findViewById(R.id.about);
        nameEt = root.findViewById(R.id.name);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String username = sharedPreferences.getString("name", "");

       nameEt.setText(username);




        nameBtn.setOnClickListener(new View.OnClickListener() {
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

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_numberFragment);
            }
        });
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String status = sharedPreferences1.getString("About", "");

        aboutEt.setText(status);

        return root;
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

        bottomSheetDialog.show();


    }

    }
