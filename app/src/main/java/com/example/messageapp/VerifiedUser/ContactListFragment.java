package com.example.messageapp.VerifiedUser;

import static androidx.core.content.ContentResolverCompat.query;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.messageapp.ContactsModal;
import com.example.messageapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;


public class ContactListFragment extends Fragment {

    // creating variables for our array list, recycler view progress bar and adapter.
    private ArrayList<ContactsModal> contactsModalArrayList;
    private RecyclerView contactRV;
    private ContactRVAdapter contactRVAdapter;
    private ProgressBar loadingPB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contact_list, container, false);
        // on below line we are initializing our variables.
        contactsModalArrayList = new ArrayList<>();
        contactRV = root.findViewById(R.id.idRVContacts);
        FloatingActionButton addNewContactFAB = root.findViewById(R.id.idFABadd);
        loadingPB = root.findViewById(R.id.idPBLoading);

        // calling method to prepare our recycler view.
        prepareContactRV();

        // calling a method to request permissions.
        requestPermissions();
        return root;
    }

    private void prepareContactRV() {
        // in this method we are preparing our recycler view with adapter.
        contactRVAdapter = new ContactRVAdapter(getActivity().getApplicationContext(), contactsModalArrayList);
        // on below line we are setting layout manager.
        contactRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        // on below line we are setting adapter to our recycler view.
        contactRV.setAdapter(contactRVAdapter);
    }

    private void requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(getContext())
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(Manifest.permission.READ_CONTACTS,
                        // below is the list of permissions
                        Manifest.permission.WRITE_CONTACTS)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    public void setContentResolver(ContentProvider contentResolver) {

                    }

                    public ContentProvider getContentResolver() {

                        return null;
                    }

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            // do you work now
                            getContacts();
                            Toast.makeText(getActivity().getApplicationContext(), "All the permissions are granted..", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permanently,
                            // we will show user a dialog message.
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }


                                  @SuppressLint("Range")
                                  private void getContacts() {
                                      // this method is use to read contact from users device.
                                      // on below line we are creating a string variables for
                                      // our contact id and display name.
                                      String contactId = "";
                                      String displayName = "";
                                      // on below line we are calling our content resolver for getting contacts
                                      Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                                      // on blow line we are checking the count for our cursor.
                                      if (cursor.getCount() > 0) {
                                          // if the count is greater than 0 then we are running a loop to move our cursor to next.
                                          while (cursor.moveToNext()) {
                                              // on below line we are getting the phone number.
                                              @SuppressLint("Range") int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                                              if (hasPhoneNumber > 0) {
                                                  // we are checking if the has phone number is > 0
                                                  // on below line we are getting our contact id and user name for that contact
                                                  contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                                                  displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                                  // on below line we are calling a content resolver and making a query
                                                  Cursor phoneCursor = getContentResolver().query(
                                                          ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                                          null,
                                                          ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                                          new String[]{contactId},
                                                          null);
                                                  // on below line we are moving our cursor to next position.
                                                  if (phoneCursor.moveToNext()) {
                                                      // on below line we are getting the phone number for our users and then adding the name along with phone number in array list.
                                                      String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                                      contactsModalArrayList.add(new ContactsModal(displayName, phoneNumber));
                                                  }
                                                  // on below line we are closing our phone cursor.
                                                  phoneCursor.close();
                                              }
                                          }
                                      }
                                      // on below line we are closing our cursor.
                                      cursor.close();
                                      // on below line we are hiding our progress bar and notifying our adapter class.
                                      loadingPB.setVisibility(View.GONE);
                                      contactRVAdapter.notifyDataSetChanged();
                                  }



                    private void showSettingsDialog() {
                        // we are displaying an alert dialog for permissions
                        AlertDialog.Builder builder = new AlertDialog.Builder(getView().getContext());

                        // below line is the title
                        // for our alert dialog.
                        builder.setTitle("Need Permissions");

                        // below line is our message for our dialog
                        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
                        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // this method is called on click on positive
                                // button and on clicking shit button we
                                // are redirecting our user from our app to the
                                // settings page of our app.
                                dialog.cancel();
                                // below is the intent from which we
                                // are redirecting our user.
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivityForResult(intent, 101);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // this method is called when
                                // user click on negative button.
                                dialog.cancel();
                            }
                        });
                        // below line is used
                        // to display our dialog
                        builder.show();
                    }
                });
    }
}