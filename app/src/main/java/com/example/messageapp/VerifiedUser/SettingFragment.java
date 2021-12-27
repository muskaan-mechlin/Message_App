package com.example.messageapp.VerifiedUser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;


public class SettingFragment extends Fragment {
  TextView profiletext,abouttext,acctextview,privatextview,chatview,themeview;
  ImageButton profilebtn,keybutton,msgbtn,notbtn,setbtn;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        profiletext = root.findViewById(R.id.textview);
        abouttext = root.findViewById(R.id.textview1);
        profilebtn = root.findViewById(R.id.imagebutton);
        acctextview = root.findViewById(R.id.textview2);
        privatextview = root.findViewById(R.id.textview3);
        keybutton = root.findViewById(R.id.imageview0);
        chatview = root.findViewById(R.id.textview4);
        themeview = root.findViewById(R.id.textview5);
        msgbtn = root.findViewById(R.id.imageview);
        notbtn = root.findViewById(R.id.imageview1);
        setbtn = root.findViewById(R.id.imageview2);

//        ListAdapter adapter=new ListAdapter(getActivity(),maintitle, subtitle);

        profiletext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_profileFragment);
            }
        });
        abouttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_profileFragment);
            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_profileFragment);
            }
        });

        acctextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_accountFragment);
            }
        });
        privatextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_accountFragment);
            }
        });
        keybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_accountFragment);
            }
        });

        msgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_chat1Fragment);
            }
        });
        chatview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_chat1Fragment);
            }
        });
        themeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_chat1Fragment);
            }
        });
               notbtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_notificationFragment2);

                   }
               });
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settingFragment_to_storageFragment);

            }
        });



        return  root;
    }
}