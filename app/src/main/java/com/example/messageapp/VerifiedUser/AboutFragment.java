package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messageapp.R;

public class AboutFragment extends Fragment {
    private static final String TAG = "AboutFragment";
    TextView setView,availView,busyView,schoolView,moviesView,workView,batteryView,talkView,meetingView,gymView,sleepingView,urgentView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        setView = root.findViewById(R.id.textview1);
        availView = root.findViewById(R.id.textview3);
        busyView = root.findViewById(R.id.textview4);
        schoolView = root.findViewById(R.id.textview5);
        moviesView = root.findViewById(R.id.textview6);
        workView = root.findViewById(R.id.textview7);
        batteryView = root.findViewById(R.id.textview8);
       talkView = root.findViewById(R.id.textview9);
      meetingView = root.findViewById(R.id.textview10);
       gymView = root.findViewById(R.id.textview11);
       sleepingView = root.findViewById(R.id.textview12);
       urgentView = root.findViewById(R.id.textview13);

        String available = availView.getText().toString();
        String busy = busyView.getText().toString();
        String school = schoolView.getText().toString();
        String movies = moviesView.getText().toString();
        String work = workView.getText().toString();
        String battery = batteryView.getText().toString();
        String talk = talkView.getText().toString();
        String meeting = meetingView.getText().toString();
        String gym= gymView.getText().toString();
        String sleeping = sleepingView.getText().toString();
        String urgent = urgentView.getText().toString();


        availView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(available);

            }
        });

        busyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(busy);
            }
        });

        schoolView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(school);
            }
        });
        moviesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(movies);
            }
        });
        workView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(work);
            }
        });
        batteryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(battery);
            }
        });
        talkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(talk);
            }
        });
        meetingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(meeting);
            }
        });
        gymView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(gym);
            }
        });
        sleepingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(sleeping);
            }
        });
        urgentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(urgent);
            }
        });


        detailsPhone();
        return root;  }


    public void detailsPhone() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("About",setView.getText().toString());
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: "+setView.getText().toString());

        Log.d(TAG, "details: "+preferences.getString("About",""));
    }
}