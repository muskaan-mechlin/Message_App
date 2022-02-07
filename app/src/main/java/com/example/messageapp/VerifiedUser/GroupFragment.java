package com.example.messageapp.VerifiedUser;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class GroupFragment extends Fragment {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String TAG = "GroupFragment";
    FloatingActionButton fab;
    MyCustomAdapter dataAdapter = null;
    ListView listView;
     ChipGroup chipGroup ;
    List<ContactsInfo> contactsInfoList;
    TextView mTextView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root = inflater.inflate(R.layout.fragment_group, container, false);
//        btnGetContacts = (Button) root.findViewById(R.id.btnGetContacts);
        listView = root.findViewById(R.id.list);
        fab = root.findViewById(R.id.floating_action_button);
        mTextView = root.findViewById(R.id.textview);
        listView.setAdapter(dataAdapter);
//
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        listView.setItemChecked(contactsInfoList.size(), true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SparseBooleanArray clickedItemPositions = listView.getCheckedItemPositions();
                listView.getSelectedItem();
                Log.d(TAG, "onItemClick: "+listView.getSelectedItem());
//                mTextView.setText("Checked items - ");

                for(int index=0;index<clickedItemPositions.size();index++){
                    // Get the checked status of the current item
                   String name = (String) contactsInfoList.get(i).getDisplayName();
                    Log.d(TAG, "onItemClick: "+name);
                    mTextView.setText("Checked items - " + name);


                    boolean checked = clickedItemPositions.valueAt(index);

                    if(checked){
                        // If the current item is checked
                        int key = clickedItemPositions.keyAt(index);
                        Log.d(TAG, "onItemClick: "+clickedItemPositions.keyAt(index));
//                        String item = (String) listView.getItemAtPosition(key);

                        // Display the checked items on TextView
                        mTextView.setText(mTextView.getText() + " \n");
                    }
                }
            }
        });









        requestContactPermission();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),"At least 1 contact must be selected",Toast.LENGTH_SHORT).show();

            }
        });

        return root;
    }
    @SuppressLint("Range")
    private void getContacts(){
        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        String contactId = null;
        String displayName = null;
        contactsInfoList = new ArrayList<ContactsInfo>();
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {

                    ContactsInfo contactsInfo = new ContactsInfo();
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactsInfo.setContactId(contactId);
                    contactsInfo.setDisplayName(displayName);

                    Cursor phoneCursor =getActivity().getApplicationContext().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        contactsInfo.setPhoneNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    contactsInfoList.add(contactsInfo);
                }
            }
        }
        cursor.close();

        dataAdapter = new MyCustomAdapter(getActivity().getApplicationContext(), R.layout.contact_info, contactsInfoList);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemChecked(contactsInfoList.size(), true);
        listView.setAdapter(dataAdapter);

//        setTag(contactsInfoList);
//

    }


    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());
                    builder.setTitle("Read contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
//    private void setTag(final List<ContactsInfo> contactsInfoList) {
//
//        for (int index = 0; index < contactsInfoList.size(); index++) {
//            final ContactsInfo tagName = contactsInfoList.get(index);
//            final Chip chip = new Chip(getActivity().getApplicationContext());
//            int paddingDp = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, 10,
//                    getResources().getDisplayMetrics()
//            );
//            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
//            chip.setText((CharSequence) tagName);
//            chip.setCloseIconResource(R.drawable.ic_baseline_arrow_forward_24);
//            chip.setCloseIconEnabled(true);
//            //Added click listener on close icon to remove tag from ChipGroup
//            chip.setOnCloseIconClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    contactsInfoList.remove(tagName);
//                    chipGroup.removeView(chip);
//                }
//            });
//
//            chipGroup.addView(chip);
//        }
//




}