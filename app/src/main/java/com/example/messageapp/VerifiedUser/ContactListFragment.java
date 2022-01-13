package com.example.messageapp.VerifiedUser;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.messageapp.R;
import com.google.firebase.database.Query;

import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;


public class ContactListFragment extends Fragment  {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private static final String TAG = "ContactList";
    MyCustomAdapter dataAdapter = null;
    ListView listView;
    Button btnGetContacts;
    List<ContactsInfo> contactsInfoList;
    String name;


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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                name = contactsInfoList.get(i).getDisplayName();
                Log.d(TAG, "onItemClick: "+name);

                detailsPhone();

                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_personFragment);

            }
        });


                requestContactPermission();


    return root;}

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
                        else if (id==2){
                            //Save clicked
                            //set text
                         return true;
                        }
                        else if (id==3){
                            //Delete clicked
                            //set text
                        return true;
                        }
                        return false;
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






