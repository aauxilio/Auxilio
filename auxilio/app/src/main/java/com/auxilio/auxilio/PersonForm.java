package com.auxilio.auxilio;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;

import com.auxilio.auxilio.data.AffidivitApplication;
import com.auxilio.auxilio.data.Person;

import static android.view.View.GONE;

public class PersonForm extends AppCompatActivity {

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText phoneNumber;
    private TextInputEditText address;
    private Button nextButton;
    private Button done;


    private AffidivitApplication.Builder affidivitApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_form);
        affidivitApplication = DataUtils.getAffidivitApplication();
        firstName = findViewById(R.id.parent_first_name);
        lastName = findViewById(R.id.parents_last_name);
        phoneNumber = findViewById(R.id.parent_phone);
        address = findViewById(R.id.parent_address);
        done = findViewById(R.id.parent_done_button);

        nextButton = findViewById(R.id.parent_next_button);
        setButtonText();

        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addPerson(buildPerson());
                        Intent intent = new Intent(PersonForm.this, PersonForm.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }

    private Person buildPerson() {
        return new Person.Builder()
                .firstName(firstName.getText().toString())
                .lastName(lastName.getText().toString())
                .phoneNumber(phoneNumber.getText().toString())
                .address(address.getText().toString())
                .build();
    }

    private void addPerson(Person person) {
        if (DataUtils.getNumOfRelatives() > 0) {
            DataUtils.addRelative(person);
        } else {
            DataUtils.addParent(person);
        }
    }

    private void setButtonText() {
        if (DataUtils.getNumOfRelatives() == 0) {
            nextButton.setText("Add Relatives");
            done.setVisibility(GONE);
        } else {
            nextButton.setText("Next Relative");
            done.setVisibility(View.VISIBLE);
        }
    }
}
