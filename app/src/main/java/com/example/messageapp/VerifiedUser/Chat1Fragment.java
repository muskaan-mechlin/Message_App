package com.example.messageapp.VerifiedUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;


public class Chat1Fragment extends Fragment {
           TextView fonttextview,fontView,languageView,languageView1,themeView,themeView1,backupView,historyView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_chat1, container, false);
        fonttextview = root.findViewById(R.id.textview3);
        fontView = root.findViewById(R.id.textview0);
        languageView = root.findViewById(R.id.textview4);
        languageView1 = root.findViewById(R.id.textview4_1);
        themeView = root.findViewById(R.id.textview);
        themeView1 = root.findViewById(R.id.textview1);
        backupView = root.findViewById(R.id.textview5);
        historyView = root.findViewById(R.id.textview6);



        fonttextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Small","Medium","Large"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Font size");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        fontView.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                });
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        languageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"English", "Hindi", "Bengali", "Marathi", "Gujarati","Punjabi","Kannada","Urdu","Telgu","Tamil","Malayalam"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("App Language");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        languageView1.setText("Phone's language" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                });

                builder.setPositiveButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        themeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Light","Dark"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Choose theme");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        fontView.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
            backupView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(R.id.action_chat1Fragment_to_backupFragment);
                }
            });
        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chat1Fragment_to_chathistoryFragment);
            }
        });
   return root; }
}