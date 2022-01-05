package com.example.messageapp.Authentication;

import static android.content.Context.MODE_PRIVATE;
import static com.example.messageapp.R.color.lightyellow;
import static com.google.android.material.color.MaterialColors.getColor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.Model;
import com.example.messageapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.Objects;


public class PhoneNumber extends Fragment {

    private static final String TAG = "PhoneNumber";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    TextInputEditText codeet, phonenoet;
    CountryCodePicker countryCodePicker;
    String phonenumber2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_phone_number, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        Button btn = root.findViewById(R.id.nextbtn);
        codeet = root.findViewById(R.id.textView_code);
        phonenoet = root.findViewById(R.id.phoneNumber);
        countryCodePicker = root.findViewById(R.id.countryCode_picker);

        String countrycode = countryCodePicker.getSelectedCountryCodeWithPlus();
        Log.d(TAG, "onCreateView: " + countryCodePicker.getSelectedCountryCode());
        Log.d(TAG, "onCreateView: " + countryCodePicker.getCcpDialogShowNameCode());
        Log.d(TAG, "onCreateView: " + countryCodePicker.getDefaultCountryCode());
        Log.d(TAG, "onCreateView: " + countryCodePicker.getSelectedCountryNameCode());

        codeet.setText(countrycode);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               phonenumber2 = phonenoet.getText().toString();
               detailsPhone();

                addDataToFirestore(phonenoet.getText().toString());
                alertDialog();


                Navigation.findNavController(view).navigate(R.id.action_phoneNumber_to_verifying2);
            }
        });


        return root;
    }




    public void detailsPhone() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("phonenumber",phonenoet.getText().toString());
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: "+phonenoet.getText().toString());

        Log.d(TAG, "details: "+preferences.getString("phonenumber",""));
    }


    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getView().getContext());
        dialog.setMessage("Is this OK,or would you like to edit the number ?");
        dialog.setTitle("You entered the phone number:");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                                // below line is for checking weather the user
                                // has entered his mobile number or not.
                        String mobile = phonenoet.getText().toString().trim();

                        if(mobile.isEmpty() || mobile.length() < 10){
                            phonenoet.setError("Enter a valid mobile");
                            phonenoet.requestFocus();
                            return;
                        }
//                            Navigation.findNavController(requireView()).navigate(R.id.action_phoneNumber_to_verifying2);
//                        Toast.makeText(getActivity().getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


//                Toast.makeText(getActivity().getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                TextView message = alertDialog.findViewById(android.R.id.message);
                if (message != null) {
                    message.setTextSize(12);

                }
                //The following is to style dialog title. Note that id is alertTitle
                TextView title = alertDialog.findViewById(R.id.alertTitle);
                if (title != null) {
                    title.setTextSize(17);
                }
            }
        });


        alertDialog.show();

    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_app_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem setting = menu.findItem(R.id.btnsett1);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {



//                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getView().getContext());
//                materialAlertDialogBuilder.setTitle("Help");
//                materialAlertDialogBuilder.show();
//                MaterialAlertDialogBuilder(context)
//                        .setTitle(resources.getString(R.string.title))
//
//                    // Respond to item chosen
//                }
//        .show()
//                Navigation.findNavController(getView()).navigate(R.id.action_regisFragment_to_settingFragment);
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnsett1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void addDataToFirestore(String phonenumber2) {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbCourses = db.collection("Model");

        // adding our data to our courses object class.
        Model courses = new Model(phonenoet.getText().toString());

        // below method is use to add data to Firebase Firestore.
        dbCourses.add(courses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
//                Toast.makeText(MainActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onSuccess: Phonenumber");            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
//                Toast.makeText(MainActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: Failed"); }
        });
    }
}




