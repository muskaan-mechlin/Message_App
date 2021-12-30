package com.example.messageapp.VerifiedUser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;


public class ChathistoryFragment extends Fragment {
       TextView exportView,archiveView,clearView,deleteView;
       ImageButton exportButton,archiveButton,clearButton,deleteButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_chathistory, container, false);
       exportButton = root.findViewById(R.id.imagebtn);
       exportView = root.findViewById(R.id.export);
       archiveButton = root.findViewById(R.id.imagbtn1);
       archiveView = root.findViewById(R.id.archive);
       clearButton = root.findViewById(R.id.imagbtn2);
       clearView = root.findViewById(R.id.clear);
       deleteButton = root.findViewById(R.id.imagbtn3);
       deleteView = root.findViewById(R.id.delete);

       archiveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder dialog = new AlertDialog.Builder(getView().getContext());
               dialog.setMessage("Are you sure you want to unarchive ALL chats?");
//               dialog.setTitle("You entered the phone number:");
               dialog.setPositiveButton("OK",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog,
                                               int which) {


//                        Toast.makeText(getActivity().getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                           }
                       });
               dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {


//                Toast.makeText(getActivity().getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
                   }
               });
               AlertDialog alertDialog = dialog.create();
               alertDialog.show();

           }
       });

        archiveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getView().getContext());
                dialog.setMessage("Are you sure you want to unarchive ALL chats?");
//               dialog.setTitle("You entered the phone number:");
                dialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {


//                        Toast.makeText(getActivity().getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                            }
                        });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


//                Toast.makeText(getActivity().getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();

            }
        });

        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Delete media in chats", "Delete starred messages"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                builder.setTitle("Clear messages in chats?");
//                builder.setMessage("Messages in all chats will disappear forever.");

                boolean[] checkedItems = new boolean[]{true, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("CLEAR MESSAGES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Delete media in chats", "Delete starred messages"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Clear messages in chats?");
//                builder.setMessage("Messages in all chats will disappear forever.");

                boolean[] checkedItems = new boolean[]{true, false}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("CLEAR MESSAGES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Delete media in chats"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Choose items");
                builder.setTitle("Are u sure u want to\ndelete ALL chats and their\nmessages?");

                boolean[] checkedItems = new boolean[]{true}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listItems = {"Delete media in chats"};

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setTitle("Choose items");
                builder.setTitle("Are u sure u want to\ndelete ALL chats and their\nmessages?");

                boolean[] checkedItems = new boolean[]{true}; //this will checked the items when user open the dialog
                builder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getActivity().getApplicationContext(), "Position: " + which + " Value: " + listItems[which] + " State: " + (isChecked ? "checked" : "unchecked"), Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
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