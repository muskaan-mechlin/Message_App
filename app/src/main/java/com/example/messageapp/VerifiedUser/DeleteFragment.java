package com.example.messageapp.VerifiedUser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.messageapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;


public class DeleteFragment extends Fragment {
    private static final String TAG = "Delete Fragment";
    TextInputEditText codeet, phonenoet;
    CountryCodePicker countryCodePicker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_delete, container, false);
        codeet = root.findViewById(R.id.textView_code);
        phonenoet = root.findViewById(R.id.phoneNumber);
        countryCodePicker = root.findViewById(R.id.countryCode_picker);

        String countrycode = countryCodePicker.getSelectedCountryCodeWithPlus();
        Log.d(TAG, "onCreateView: " + countryCodePicker.getSelectedCountryCode());
        Log.d(TAG, "onCreateView: " + countryCodePicker.getCcpDialogShowNameCode());
        Log.d(TAG, "onCreateView: " + countryCodePicker.getDefaultCountryCode());
        Log.d(TAG, "onCreateView: " + countryCodePicker.getSelectedCountryNameCode());

        codeet.setText(countrycode);


        return  root; }
}