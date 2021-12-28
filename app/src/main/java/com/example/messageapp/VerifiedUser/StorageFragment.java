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


public class StorageFragment extends Fragment {

    TextView dataView,photoview,wifiView,allView,roamView,mediaView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_storage, container, false);
        dataView = root.findViewById(R.id.mobile);
        photoview = root.findViewById(R.id.photos);
        wifiView = root.findViewById(R.id.wifi);
        allView = root.findViewById(R.id.media);
        roamView = root.findViewById(R.id.roam);
        mediaView = root.findViewById(R.id.nomedia);

        dataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Photos", "Audio", "Videos", "Documents"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("When using mobile data");

                boolean[] checkedItems = new boolean[]{true, false, true, false, true}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        photoview.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
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

        wifiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Photos", "Audio", "Videos", "Documents"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("When connected on Wi-Fi");

                boolean[] checkedItems = new boolean[]{true, false, true, false, true}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                       allView.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
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

        roamView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Photos", "Audio", "Videos", "Documents"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("When using mobile data");

                boolean[] checkedItems = new boolean[]{true, false, true, false, true}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        mediaView.setText("Selected Item is" + listItems[which]);
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
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
   return root; }
}