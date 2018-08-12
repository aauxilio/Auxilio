package com.auxilio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auxilio.auxilio.TwilioAPI;
import com.auxilio.data.AffidivitApplication;

public class MainLobbyActivity extends AppCompatActivity {

    Button notifyRelatives;
    Button viewAffidivit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);

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
                new TwilioAPI().execute(application);
                Toast.makeText(MainLobbyActivity.this, "Parientes notificados", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
