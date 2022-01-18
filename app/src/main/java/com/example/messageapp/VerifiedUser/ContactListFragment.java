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
import com.example.messageapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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
        listView.setAdapter(dataAdapter);
        db = FirebaseFirestore.getInstance();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                name = contactsInfoList.get(i).getDisplayName();
                 phoneno = contactsInfoList.get(i).getPhoneNumber();
                Log.d(TAG, "onItemClick: "+name);
                Log.d(TAG, "onItemClick: "+phoneno);



                String recieverId = FirebaseAuth.getInstance().getUid();
                Log.d(TAG, "onItemClick: "+recieverId);





            checkExistingConversation(view);

                detailsPhone();



            }
        });


                requestContactPermission();


    return root;}

    public void checkExistingConversation(View v){

        CollectionReference dbConversation = db.collection("Conversations");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String currentUserPhoneNo = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d(TAG, "checkExistingConversation: "+currentUserPhoneNo);
        String otherUserPhoneNo = phoneno;
        Set <String> setOfExistingPhoneNumber = new HashSet<>();
        setOfExistingPhoneNumber.add(currentUserPhoneNo);
        setOfExistingPhoneNumber.add(otherUserPhoneNo);
        Log.d(TAG, "checkExistingConversation: "+phoneno);
        Query query = dbConversation.whereArrayContainsAny("ParticipantsPhoneNo", Arrays.asList(currentUserPhoneNo));
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean isChatExistAlready = false;
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Log.d(TAG, "onComplete: " + queryDocumentSnapshot.getId());
                        Log.d(TAG, "onComplete: " + queryDocumentSnapshot.getData());
                        Log.d(TAG, "onComplete: " + queryDocumentSnapshot.getData().get("ParticipantsPhoneNo"));

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

    public void createConversation(String user1UID, String user2UID, String user1PhoneNumber , String user2PhoneNumber,View v){
        Map<String, Object> conversationDocument = new HashMap<>();
        conversationDocument.put("Participants",Arrays.asList(user1UID, user2UID));
        conversationDocument.put("ParticipantsPhoneNo",Arrays.asList(user1PhoneNumber,user2PhoneNumber));
        db.collection("Conversations")
                .add(conversationDocument)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
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






