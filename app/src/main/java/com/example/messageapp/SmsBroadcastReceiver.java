package com.example.messageapp;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class SmsBroadcastReceiver extends BroadcastReceiver {

//    SmsBroadcastReceiverListener smsBroadcastReceiverListener;
public static final String SMS_BUNDLE = "pdus";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            Verifying inst = Verifying.instance();
        }
//        if (intent.getAction() == SmsRetriever.SMS_RETRIEVED_ACTION) {
//            Bundle extras = intent.getExtras();
//            Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
//            switch (smsRetrieverStatus.getStatusCode()) {
//                case CommonStatusCodes.SUCCESS:
//                    Intent messageIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
//                    smsBroadcastReceiverListener.onSuccess(messageIntent);
//                    break;
//                case CommonStatusCodes.TIMEOUT:
//                    smsBroadcastReceiverListener.onFailure();
//                    break;
//            }
//        }
//    }

//    public void onReceive(Context context, Intent intent) {
//        Bundle intentExtras = intent.getExtras();
//        if (intentExtras != null) {
//            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
//            String smsMessageStr = "";
//            for (int i = 0; i < sms.length; ++i) {
//                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
//
//                String smsBody = smsMessage.getMessageBody().toString();
//                String address = smsMessage.getOriginatingAddress();
//
//                smsMessageStr += "SMS From: " + address + "\n";
//                smsMessageStr += smsBody + "\n";
//            }
//            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
//
//            //this will update the UI with message
//            SmsActivity inst = SmsActivity.instance();
//            inst.updateList(smsMessageStr);
//        }
//    }
//}

//       interface SmsBroadcastReceiverListener {
//            void onSuccess(Intent intent);
//
//            void onFailure();
//        }
    }
}
