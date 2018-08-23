package com.auxilio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auxilio.auxilio.SMS;
import com.auxilio.data.AffidivitApplication;

public class MainLobbyActivity extends AppCompatActivity {

    Button notifyRelatives;
    Button viewAffidivit;
    private static final int PERMISSION_REQUEST_CODE = 1;
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
//                new TwilioAPI().execute(application);
//                Toast.makeText(MainLobbyActivity.this, "Parientes notificados", Toast.LENGTH_SHORT).show();

                String sms = "this is going to be around 160 chars. testing. this is going to be around 160 chars. testing.this is going to be around 160 chars. testing.this is going to be .";
                message.sendSMS("4159881453",  sms);
            }
        });
    }

    private void checkForPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
    }

    @Override
    protected void onDestroy()
    {
        message.unRegisterReceivers();
        super.onDestroy();
    }
}
