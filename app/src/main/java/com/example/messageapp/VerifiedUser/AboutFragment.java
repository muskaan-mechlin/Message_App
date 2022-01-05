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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

public class AboutFragment extends Fragment {
    private static final String TAG = "AboutFragment";
    TextView availView, busyView, schoolView, moviesView, workView, batteryView, talkView, meetingView, gymView, sleepingView, urgentView;
    EditText setView;
    ImageButton imageButton;

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
        imageButton = root.findViewById(R.id.imagebtn2);

        String available = availView.getText().toString();
        String busy = busyView.getText().toString();
        String school = schoolView.getText().toString();
        String movies = moviesView.getText().toString();
        String work = workView.getText().toString();
        String battery = batteryView.getText().toString();
        String talk = talkView.getText().toString();
        String meeting = meetingView.getText().toString();
        String gym = gymView.getText().toString();
        String sleeping = sleepingView.getText().toString();
        String urgent = urgentView.getText().toString();


        availView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(available);
                detailsPhone();
            }
        });

        busyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(busy);
                detailsPhone();
            }
        });

        schoolView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(school);
                detailsPhone();
            }
        });
        moviesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(movies);
                detailsPhone();
            }
        });
        workView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(work);
                detailsPhone();
            }
        });
        batteryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(battery);
                detailsPhone();
            }
        });
        talkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(talk);
                detailsPhone();
            }
        });
        meetingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(meeting);
                detailsPhone();
            }
        });
        gymView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(gym);
                detailsPhone();
            }
        });
        sleepingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(sleeping);
                detailsPhone();
            }
        });
        urgentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setView.setText(urgent);
                detailsPhone();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
                detailsPhone();
            }
        });

        Log.d(TAG, "onCreateView: " + setView);


        detailsPhone();

        return root;
    }


    public void detailsPhone() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("about", setView.getText().toString());
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: about " + setView.getText().toString());

        Log.d(TAG, "details: " + preferences.getString("About", ""));
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getView().getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog2);

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
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  setView.setText(name.getText().toString());
                  bottomSheetDialog.dismiss();
                  detailsPhone();
            }
        });


        bottomSheetDialog.show();


    }
}