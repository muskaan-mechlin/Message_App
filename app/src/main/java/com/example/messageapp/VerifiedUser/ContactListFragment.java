package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import android.widget.Toast;

import com.example.messageapp.Firebase.ChatMessage;
import com.example.messageapp.Firebase.Reciever;
import com.example.messageapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class ContactListFragment extends Fragment  {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String TAG = "ContactList";
    MyCustomAdapter dataAdapter = null;
    private FirebaseFirestore db;
    ListView listView;
    Button btnGetContacts;
    List<ContactsInfo> contactsInfoList;
    String name,phoneno;
    List<ChatMessage> listOfChatMessages;
    String ConversationID,UserID;
     FloatingActionButton floatingActionButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contact_list2, container, false);

        listView = root.findViewById(R.id.lstContacts);
        floatingActionButton = root.findViewById(R.id.floating_action_button);
        listView.setAdapter(dataAdapter);
        db = FirebaseFirestore.getInstance();
        checkUserExists();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_groupFragment);
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                name = contactsInfoList.get(i).getDisplayName();
                 phoneno = contactsInfoList.get(i).getPhoneNumber();
                Log.d(TAG, "onItemClick: "+name);
                Log.d(TAG, "onItemClick: "+phoneno);
                String phoneNumberUtils = PhoneNumberUtils.stripSeparators(phoneno);
                Log.d(TAG, "onItemClick: 1 "+phoneNumberUtils);
//                Log.d(TAG, "onItemClick:2 "+PhoneNumberUtils.formatNumber(phoneno,Locale.getDefault().getCountry()));
//                splitMobilenumberMethod();


                checkRecieverExists();
//                createContacts(name,phoneno,"");
                   checkContactExist();


                String recieverId = FirebaseAuth.getInstance().getUid();
                Log.d(TAG, "onItemClick: "+recieverId);


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
                String username = sharedPreferences.getString("name", "");
                Log.d(TAG, "onItemClick: Current user "+FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());





            checkExistingConversation(view);

                detailsPhone();



            }
        });


                requestContactPermission();


    return root;}
    public void detailsConversationId() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ConversationId",ConversationID);
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: "+ConversationID);

        Log.d(TAG, "details: "+preferences.getString("ConversationId",""));
    }
    public void checkContactExist() {
        CollectionReference dbContact = db.collection("Users").document(UserID).collection("Contacts");
        String phoneNumber = phoneno;
        Query query = dbContact.whereEqualTo("PhoneNumber", phoneNumber);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean isContactExistAlready = false;


                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: Contact " + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: Contact " + queryDocumentSnapshot.getData());
                        isContactExistAlready = true;
                    }
                    if(!isContactExistAlready) {
                        db.collection("Users").whereEqualTo("FullPhoneNumber",phoneNumber).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot querySnapshots) {
                                for(DocumentSnapshot documentSnapshot : querySnapshots.getDocuments()) {
                                    Log.d(TAG, "onSuccess: FullId " + documentSnapshot.getId());
                                    String uId = documentSnapshot.getId();
                                    Log.d(TAG, "onSuccess:LiD "+uId);
                                    createContacts(name,phoneNumber,uId);
                                }

                            }
                        });
//                        createContacts(name,phoneNumber,"");
                    }
                }
            }

        });

    }

    public void checkUserExists(){
        CollectionReference dbUser = db.collection("Users");
        String currentUserPhoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d(TAG, "checkExistingUser:PhoneNumber "+currentUserPhoneNo);
        Log.d(TAG, "checkUserExists: Number "+currentUserPhoneNo);
        Query query1 = dbUser.whereEqualTo("FullPhoneNumber", currentUserPhoneNo);
        query1.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                  boolean  isUserExistAlready = false;
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: result ");
                        Log.d(TAG, "onComplete:usersId  " + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: user " + queryDocumentSnapshot.getData());
                        Log.d(TAG, "onComplete:user " + queryDocumentSnapshot.getData().get("FullPhoneNumber"));

                        UserID = queryDocumentSnapshot.getId();
                        detailsContacts();


                        isUserExistAlready = true;



                    }
                    if(!isUserExistAlready) {
                        Log.d(TAG, "onComplete: UsernotExist");
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
                        String username = sharedPreferences.getString("name", "");
                        String phonenumber = sharedPreferences.getString("phonenumber", "");
                        createUserDetails(currentUserPhoneNo,"+91",username,phonenumber);


                    }
                }

              else {

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
                    String username = sharedPreferences.getString("name", "");
                    String phonenumber = sharedPreferences.getString("phonenumber", "");
                    createUserDetails(currentUserPhoneNo,"+91",username,phonenumber);
                }

            }

        });
    }
//


    public void checkExistingConversation(View v){

        CollectionReference dbConversation = db.collection("Conversations");
//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String currentUserPhoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d(TAG, "checkExistingConversation: "+currentUserPhoneNo);
        String otherUserPhoneNo = phoneno;
        Set <String> setOfExistingPhoneNumber = new HashSet<>();
        setOfExistingPhoneNumber.add(currentUserPhoneNo);
        setOfExistingPhoneNumber.add(otherUserPhoneNo);
        Log.d(TAG, "checkExistingConversation: "+phoneno);
        Query query = dbConversation.whereArrayContainsAny("ParticipantsPhoneNo", Arrays.asList(currentUserPhoneNo,phoneno));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean isChatExistAlready = false;
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: conversation  " + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: " + queryDocumentSnapshot.getData());
                        Log.d(TAG, "onComplete: " + queryDocumentSnapshot.getData().get("ParticipantsPhoneNo"));

                        ConversationID = queryDocumentSnapshot.getId();
                        detailsConversationId();

                        Set<String> setOfDatabasePhoneNumber = new HashSet<>();
                        setOfDatabasePhoneNumber.addAll((Collection<? extends String>) queryDocumentSnapshot.getData().get("ParticipantsPhoneNo"));
                        Log.d(TAG, "onComplete: SetMatches  "+setOfDatabasePhoneNumber.containsAll(setOfExistingPhoneNumber));
                        if (setOfDatabasePhoneNumber.containsAll(setOfExistingPhoneNumber)){
                            navigateWithConversationId(queryDocumentSnapshot.getId(),v);
                            isChatExistAlready = true;
                            break;
                        }

                    }
                    if (!isChatExistAlready){
                        createConversation("","",currentUserPhoneNo,otherUserPhoneNo,v);
                    }

                }

            }

        });

    }

    public void navigateWithConversationId(String conversationId,View v){
        Log.d(TAG, "navigateWithConversationId: "+conversationId);
        Bundle bundle = new Bundle();
        bundle.putString("ConersationID", conversationId);
        Log.d(TAG, "navigateWithConversationId: "+bundle);
        Navigation.findNavController(v).navigate(R.id.action_contactListFragment_to_personFragment,bundle);

    }

    public void createUserDetails(String fullphoneno,String countryCode,String name,String phoneNumber) {
        Map<String, Object> userDocument = new HashMap<>();
        userDocument.put("FullPhoneNumber",fullphoneno);
        userDocument.put("CountryCode",countryCode);
        userDocument.put("Name",name);
        userDocument.put("PhoneNumber",phoneNumber);
        db.collection("Users")
                .add(userDocument)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: user " + documentReference.getId());
                        Log.d(TAG, "onSuccess: ID  "+documentReference.getId());
                        UserID = documentReference.getId();
                        detailsContacts();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void createContacts(String name,String phoneNumber,String userId) {
        Map<String, Object> userDocument = new HashMap<>();
        userDocument.put("Name",name);
        userDocument.put("PhoneNumber",phoneNumber);
        userDocument.put("UserId",userId);
        db.collection("Users").document(UserID).collection("Contacts").add(userDocument).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(@NonNull DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: userCC " + documentReference.getId());
                Log.d(TAG, "onSuccess: IDCC  "+documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding document", e);

                    }
                });
    }


    public void createConversation(String user1UID, String user2UID, String user1PhoneNumber , String user2PhoneNumber,View v){
        Map<String, Object> conversationDocument = new HashMap<>();
        conversationDocument.put("Participants",Arrays.asList(user1UID, user2UID));
        conversationDocument.put("ParticipantsPhoneNo",Arrays.asList(user1PhoneNumber,user2PhoneNumber));
        db.collection("Conversations")
                .add(conversationDocument)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID conversation: " + documentReference.getId());
                       String conversationID = documentReference.getId();
                       navigateWithConversationId(conversationID,v);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_app_bar3, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem searchItem = menu.findItem(R.id.btnsett2);

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {


        SearchView searchView = (SearchView) menu.findItem(R.id.btnsett2).getActionView();
        searchView.setQueryHint("Search People");
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (contactsInfoList.contains(s)) {
                    dataAdapter.getFilter().filter(s);
                }
                else {
                    // Search query not found in List View
                    Toast
                            .makeText(getActivity().getApplicationContext(),
                                    "Not found",
                                    Toast.LENGTH_LONG)
                            .show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                dataAdapter.getFilter().filter(s);
                return false;
            }
        });
                return false;
            }
        });


        MenuItem setting = menu.findItem(R.id.btnsett1);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(),getView());

                //add menu items in popup menu
                popupMenu.getMenu().add(Menu.NONE, 0, 0, "Invite a friend"); //parm 2 is menu id, param 3 is position of this menu item in menu items list, param 4 is title of the menu
                popupMenu.getMenu().add(Menu.NONE, 1, 1, "Contacts");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "Refresh");
                popupMenu.getMenu().add(Menu.NONE, 3, 3, "Help");

                //handle menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //get id of the clicked item
                        int id = menuItem.getItemId();
                        //handle clicks
                        if (id==0){
                            //Copy clicked
                            //set text
                           return true;
                        }
                        else if (id==1){
                            //Share clicked
                            //set text
                         return true;
                        }
                        else //Delete clicked
                            //set text
                            if (id==2){
                            //Save clicked
                            //set text
                         return true;
                        }
                        else return id == 3;
                    }
                });


                popupMenu.show();
                return true;
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnsett1) {
            return true;
        }
        if (id == R.id.btnsett2) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void detailsPhone() {
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("DisplayName", name);
        editor.putString("RecieverPhoneNo",phoneno);
        editor.apply();
        editor.commit();


        Log.d(TAG, "details: "+name);

        Log.d(TAG, "details: "+preferences.getString("DisplayName",""));
    }
    public void detailsContacts(){
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference),MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserId", UserID);

        Log.d(TAG, "detailsContacts:IIII "+UserID);
        editor.apply();
        editor.commit();

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
                    String finalDisplayName = displayName;
                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Contacts").whereEqualTo("Name",displayName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                           boolean isNameExist = false;
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                Log.d(TAG, "onSuccess: 11 "+documentSnapshot.getId());
                                isNameExist = true;


                            }
                            if(isNameExist) {
                                contactsInfo.setDisplayName(finalDisplayName);
                            }
                            else {
                                dataAdapter.remove(contactsInfo);
                            }

                        }
                    });
//                    contactsInfo.setDisplayName(displayName);

                    Cursor phoneCursor =getActivity().getApplicationContext().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String phoneNumb = phoneNumber;
                        String ext = "", phoneN = "";
                        if (phoneNumb.startsWith("+") || phoneNumb.length() > 10) {
                            phoneN =  phoneno;
//
                            Log.d(TAG, "splitMobilenumberMethod: "+phoneN);
                            Log.d(TAG, "splitMobilenumberMethod: "+phoneNumber);
                        } else {
                            if (phoneNumb.length()==10){
                                phoneN = "+91"+phoneno;
                                Log.d(TAG, "splitMobilenumberMethod: "+phoneN);
                            }
                            else {
                                ext=phoneNumb.substring(0,1);
                                phoneN ="+91"+phoneNumb.substring(1);
                                Log.d(TAG, "splitMobilenumberMethod: "+ext);
                                Log.d(TAG, "splitMobilenumberMethod: "+phoneN);
                            }

                        }

                        db.collection("Users").whereEqualTo("FullPhoneNumber",phoneNumber).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                              boolean  isPhoneExist = false;
                                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                                    Log.d(TAG, "onSuccess: 114 "+documentSnapshot.getId());
                                    contactsInfo.setPhoneNumber(phoneNumber);
                                    isPhoneExist = true;

                                }
                                if (isPhoneExist) {
                                    contactsInfo.setPhoneNumber(phoneNumber);
                                }
                                else  {
                                    dataAdapter.remove(contactsInfo);
                                }


                            }
                        });
//                        contactsInfo.setPhoneNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    contactsInfoList.add(contactsInfo);
                }
            }
        }
        cursor.close();

        dataAdapter = new MyCustomAdapter(getActivity().getApplicationContext(), R.layout.message1, contactsInfoList);
//        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Contacts").whereEqualTo("Name",displayName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
//                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
//                    Log.d(TAG, "onSuccess: 11 "+documentSnapshot.getId());
//                    listView.setAdapter(dataAdapter);
//                }

//            }
//        });
        listView.setAdapter(dataAdapter);

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
    public void checkRecieverExists(){
        CollectionReference dbUser = db.collection("Reciever");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String username = sharedPreferences.getString("DisplayName", "");
        Query query2 = dbUser.whereEqualTo("user", name);
        Log.d(TAG, "checkRecieverExists: "+name);
        query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    boolean isRecieverExistAlready = false;
                    for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: result ");
                        Log.d(TAG, "onComplete: reciever "  + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: reciever " + queryDocumentSnapshot.getData());

                        isRecieverExistAlready = true;
                      
                    }

                
                if (!isRecieverExistAlready) {
                    Log.d(TAG, "onComplete: alreadyNotExist");
                    addDataToFirestore();
                }
                    
                }
                else {

                    addDataToFirestore();
                    Log.d(TAG, "onComplete: Firestore");

                }
            }


        });

    }
    private void addDataToFirestore() {

        // creating a collection reference
        // for our Firebase Firetore database.
        CollectionReference dbReciever = db.collection("Reciever");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.user_shared_preference), MODE_PRIVATE);
        String username = sharedPreferences.getString("DisplayName", "");

        // adding our data to our courses object class.
        Reciever userModel = new Reciever(name);
//        Map<String, Object> values = new HashMap<>();
//        values.put("user", username);
        Log.d(TAG, "addDataToFirestore: "+dbReciever);



        // below method is use to add data to Firebase Firestore.


        dbReciever.add(userModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
//                Toast.makeText(MainActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onSuccess:reciever  " +username);
                Log.d(TAG, "onSuccess:reciever " +userModel.getReceiver());
                Log.d(TAG, "onSuccess: reciever" +documentReference.get());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
//                Toast.makeText(MainActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onFailure: Failed");
            }
        });
    }




    void splitMobilenumberMethod(){
        String phoneNumb = phoneno;
        String ext = "", phoneN = "";
        if (phoneNumb.startsWith("+") || phoneNumb.length() > 10) {
            phoneN =  phoneno;
//
            Log.d(TAG, "splitMobilenumberMethod: "+phoneN);
            Log.d(TAG, "splitMobilenumberMethod: "+phoneno);
        } else {
            if (phoneNumb.length()==10){
                phoneN = "+91"+phoneno;
                Log.d(TAG, "splitMobilenumberMethod: "+phoneN);
            }
            else {
                ext=phoneNumb.substring(0,1);
                phoneN ="+91"+phoneNumb.substring(1);
                Log.d(TAG, "splitMobilenumberMethod: "+ext);
                Log.d(TAG, "splitMobilenumberMethod: "+phoneN);
            }

        }
//        showSelectedPhoneDialog(ext, phoneN);
    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }
}






