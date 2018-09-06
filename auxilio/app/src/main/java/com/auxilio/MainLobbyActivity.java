package com.auxilio;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auxilio.auxilio.SMS;
import com.auxilio.data.AffidivitApplication;

public class MainLobbyActivity extends AppCompatActivity {

    Button notifyRelatives;
    Button viewAffidivit;
    private static final int PERMISSION_SMS_CODE = 1;
    SMS message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);
        checkForPermissions();
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
                AffidivitApplication application= DataUtils.getAffidivitApplication().build();
                String sms = "We can now send messages that are > 160 chars! This gives us greater flexibility in the" +
                        " content of the message and not be restricted anymore by the previous max limit of 160 chars" +
                        " per message. The max number of chars that is allowed to send in an sms is 918." +
                        "  If a message is > 160 chars it will break down the message into batches of 158 chars, and the last" +
                        " batch being how ever many chars are left. If a message is <= 160, it will only send that one single batch.";
                message.sendSMS("4159881453",  sms);
            }
        });
    }

    private void checkForPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED)
                requestSmsPermission();
        }
    }

    private void requestSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed!")
                    .setMessage("This permission is needed to send a text message to family and friends, in case of an emergency.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainLobbyActivity.this,
                                    new String[] {Manifest.permission.SEND_SMS}, PERMISSION_SMS_CODE);
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS}, PERMISSION_SMS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Toast.makeText(this, "Inside onRequestPermissionResult", Toast.LENGTH_LONG).show();
        if (requestCode == PERMISSION_SMS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, PERMISSION_SMS_CODE);
                }
                else
                    requestSmsPermission();
            }

        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        checkForPermissions();
    }
    @Override
    protected void onDestroy()
    {
        message.unRegisterReceivers();
        super.onDestroy();
    }
}