package com.example.messageapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verifying extends Fragment {
    private static final int REQ_USER_CONSENT = 200;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    Button smsbtn, callbtn, generateOTPBtn;
    String verificationId;
    CustomEditText edotp;
    SmsBroadcastReceiver smsBroadcastReceiver;
    private static Verifying inst;








    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_verifying, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        smsbtn = root.findViewById(R.id.textButton1);
        callbtn = root.findViewById(R.id.textButton2);
        Button wrobtn = root.findViewById(R.id.textButton);

        edotp = root.findViewById(R.id.editotp);
        Button verifubtn = root.findViewById(R.id.votpbtn);

        startSmsUserConsent();


        wrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_verifying2_to_phoneNumber);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String mobile = sharedPreferences.getString("phonenumber","");

        sendVerificationCode(mobile);

        verifubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the OTP text field is empty or not.
                String code = edotp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edotp.setError("Enter valid code");
                    edotp.requestFocus();
                    return;
                }
                else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(edotp.getText().toString());
                }
            }
        });
        smsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                         resendVerificationCode(mobile);
            }
        });


        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }




    private void startSmsUserConsent() {
        SmsRetrieverClient client = SmsRetriever.getClient(getActivity().getApplicationContext());
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity().getApplicationContext(), "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Verifying instance() {
        return inst;
    }





    void getOtpFromMessage(String message1) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message1);
        if (matcher.find()) {
            edotp.setText(matcher.group(0));

        }
//            }
//        }
//    }




    } @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

    private void unregisterReceiver(SmsBroadcastReceiver smsBroadcastReceiver) {
    }





    private void resendVerificationCode(String mobile) {

            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91"+ mobile)            // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS)// Timeout and unit
                            .setActivity(getActivity())
//                        .setActivity((Activity) getActivity().getApplicationContext())// Activity (for callback binding)
                            .setCallbacks(mCallBack)// OnVerificationStateChangedCallbacks
//                            .setForceResendingToken(token)
                            .build();

            PhoneAuthProvider.verifyPhoneNumber(options);
            // ForceResendingToken from callbacks
        }





    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                            Toast.makeText(getActivity().getApplicationContext(), "VERIFIED", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(getView()).navigate(R.id.action_verifying2_to_profileInfo);

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(getActivity().getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
//    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
//   String mobile = sharedPreferences.getString("phonenumber","");

   



    private void sendVerificationCode(String mobile) {
        // this method is used for getting
        // OTP on user phone number.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+ mobile)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)// Timeout and unit
                        .setActivity(getActivity())
//                        .setActivity((Activity) getActivity().getApplicationContext())// Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }



    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
               edotp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }




    @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

}




