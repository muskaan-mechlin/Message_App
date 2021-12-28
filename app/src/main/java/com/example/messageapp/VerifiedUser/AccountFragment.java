package com.example.messageapp.VerifiedUser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.messageapp.R;


public class AccountFragment extends Fragment {
     ImageButton privacyBtn,securityBtn,verifyBtn,changeBtn,requestBtn,deleteBtn;
     TextView privacyView,securityView,verifyView,changeView,requestView,deleteView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        privacyBtn = root.findViewById(R.id.imagebutton);
        privacyView = root.findViewById(R.id.textview);
       securityBtn = root.findViewById(R.id.imagebutton1);
        securityView = root.findViewById(R.id.textview1);
        verifyBtn = root.findViewById(R.id.imagebutton2);
        verifyView = root.findViewById(R.id.textview2);
        changeBtn = root.findViewById(R.id.imagebutton3);
        changeView= root.findViewById(R.id.textview3);
      requestBtn = root.findViewById(R.id.imagebutton4);
        requestView = root.findViewById(R.id.textview4);
        deleteBtn = root.findViewById(R.id.imagebutton5);
        deleteView = root.findViewById(R.id.textview5);

        privacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_privacyFragment);
            }
        });
        privacyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_privacyFragment);
            }
        });
        securityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_securityFragment);
            }
        });
        securityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_securityFragment);
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_twoStepFragment);
            }
        });
        verifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_twoStepFragment);
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_numberFragment);
            }
        });
        changeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_numberFragment);
            }
        });
        requestView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_requestFragment);

            }
        });
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_requestFragment);

            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_deleteFragment);
            }
        });
        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_accountFragment_to_deleteFragment);
            }
        });
   return  root; }
}