package com.auxilio.auxilio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auxilio.auxilio.TwilioAPI;

public class MainLobbyActivity extends AppCompatActivity {

    Button notifyRelatives;
    Button temporaryPassword;
    Button viewAffidivit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lobby);

        notifyRelatives = findViewById(R.id.notifi_relatives);
        temporaryPassword = findViewById(R.id.temp_password);
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
                String message = "Hola, te quiero avisar que estoy en problemas al momento. Por favor cuida mis hijos! Muchas Gracias";

                new TwilioAPI().execute();
                Toast.makeText(MainLobbyActivity.this, "Successfully notified relatvies", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
