package com.auxilio;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.auxilio.auxilio.SMS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainLobbyActivity extends AppCompatActivity {

    Button notifyRelatives;
    Button viewAffidivit;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    SMS message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);
        checkAndRequestPermissions();
        message = new SMS(getBaseContext());
        message.registerReceivers();

        notifyRelatives = findViewById(R.id.notifi_relatives);
        viewAffidivit = findViewById(R.id.view_doc);

        viewAffidivit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainLobbyActivity.this, DocuViewActivity.class);
                        startActivity(intent);
                    }
                }
        );

        notifyRelatives.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String sms = "We can now send messages that are > 160 chars! This gives us greater flexibility in the" +
                        " content of the message and not be restricted anymore by the previous max limit of 160 chars" +
                        " per message. The max number of chars that is allowed to send in an sms is 918." +
                        "  If a message is > 160 chars it will break down the message into batches of 158 chars, and the last" +
                        " batch being how ever many chars are left. If a message is <= 160, it will only send that one single batch.";
                message.sendSMS("4159881453",  sms);
            }
        });
    }

    private  void checkAndRequestPermissions() {
        int smsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int contactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        List<String> permissionsNeeded = new ArrayList<>();
        if (contactsPermission != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.READ_CONTACTS);

        if (smsPermission != PackageManager.PERMISSION_GRANTED)
            permissionsNeeded.add(Manifest.permission.SEND_SMS);

        if (!permissionsNeeded.isEmpty())
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[permissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("ONREQUESTPERMISSION", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);

                    if (perms.get(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                        Log.d("ONREQUESTPERMISSION", "Some permissions are not granted ask again ");
                        Toast.makeText(this, "One of the permissions wasn't granted!", Toast.LENGTH_LONG).show();

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                            showDialogOK("SMS and Contact permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_ID_MULTIPLE_PERMISSIONS);
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }

    @Override
    protected void onStart() {

        super.onStart();
        checkAndRequestPermissions();
    }
    @Override
    protected void onDestroy()
    {
        message.unRegisterReceivers();
        super.onDestroy();
    }
}