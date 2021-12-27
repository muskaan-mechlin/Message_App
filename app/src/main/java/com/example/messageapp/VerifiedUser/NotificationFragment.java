package com.example.messageapp.VerifiedUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;


public class NotificationFragment extends Fragment {
   TextView vibtview,vibtview1,popuptview,popuptview1,lighttview,lighttview1,vibtview2,vibtview3,popuptview2,popuptview3,lightview3,lightview4;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notification, container, false);
        vibtview = root.findViewById(R.id.vibrate);
        vibtview1 = root.findViewById(R.id.vibrate1);
        popuptview = root.findViewById(R.id.popup);
        popuptview1 = root.findViewById(R.id.popup1);
        lighttview = root.findViewById(R.id.light);
        lighttview1 = root.findViewById(R.id.light1);
        vibtview2 = root.findViewById(R.id.vibratee);
        vibtview3 = root.findViewById(R.id.vibratee1);
        popuptview2 = root.findViewById(R.id.popupp);
        popuptview3 = root.findViewById(R.id.popupp1);
        lightview3 = root.findViewById(R.id.lightt);
        lightview4 = root.findViewById(R.id.lightt1);

        vibtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Off","Default","Short","Long"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Vibrate");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        vibtview1.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        popuptview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] listItems = {"No popup","only when screen on ","only when screen off","Always show popup"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Popup notification");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        popuptview1.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        lighttview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"None","White","Red","Yellow","Green","Cyan","Blue","Purple"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Font size");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        lighttview1.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        vibtview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Off","Default","Short","Long"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Vibrate");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        vibtview3.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        popuptview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] listItems = {"No popup","only when screen on ","only when screen off","Always show popup"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Popup notification");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        popuptview3.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        lightview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"None","White","Red","Yellow","Green","Cyan","Blue","Purple"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Light");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       fontView.setText("Selected Item is : " + listItems[which]);
                        lightview4.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                }).setPositiveButton(" ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });



        return  root; }
}