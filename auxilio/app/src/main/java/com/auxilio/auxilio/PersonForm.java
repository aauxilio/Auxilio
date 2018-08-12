package com.auxilio.auxilio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.auxilio.auxilio.data.AffidivitApplication;

public class PersonForm extends AppCompatActivity {

    private Button nextButton;
    private AffidivitApplication affidivitApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_form);
        affidivitApplication = DataUtils.getAffidivitApplication();

        nextButton = findViewById(R.id.parent_next_button);

        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PersonForm.this, PersonForm.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void setButtonText() {
        if (affidivitApplication.getRelatives() != null
                && affidivitApplication.getRelatives().size() > 1) {
            nextButton.setText("Next Relative");
        } else {
            nextButton.setText("Add Relatives");
        }
    }
}
