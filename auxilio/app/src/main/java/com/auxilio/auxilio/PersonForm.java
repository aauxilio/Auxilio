package com.auxilio.auxilio;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import com.auxilio.auxilio.data.AffidivitApplication;
import com.auxilio.auxilio.data.Person;

import static android.view.View.GONE;

public class PersonForm extends AppCompatActivity {

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText phoneNumber;
    private TextInputEditText address;
    private TextView addParent;
    private TextView addRelative;
    private Button nextButton;
    private Button done;


    private AffidivitApplication.Builder affidivitApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_form);
        affidivitApplication = DataUtils.getAffidivitApplication();
        addParent = findViewById(R.id.parent_add_parent);
        addRelative = findViewById(R.id.parent_add_relative);
        firstName = findViewById(R.id.parent_first_name);
        lastName = findViewById(R.id.parents_last_name);
        phoneNumber = findViewById(R.id.parent_phone);
        address = findViewById(R.id.parent_address);
        done = findViewById(R.id.parent_done_button);

        nextButton = findViewById(R.id.parent_next_button);
        setButtonText();

        done.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PersonForm.this, ChildInformationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );

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
        if (DataUtils.getParent() == null) {
            DataUtils.addParent(person);
            return;
        } else {
            DataUtils.addRelative(person);
        }
    }

    private void setButtonText() {
        if (DataUtils.getParent() == null) {
            addParent.setVisibility(View.VISIBLE);
            addRelative.setVisibility(GONE);
            done.setVisibility(View.GONE);
        } else {
            addRelative.setVisibility(View.VISIBLE);
            addParent.setVisibility(GONE);
            if (DataUtils.getNumOfRelatives() == 0) {
                done.setVisibility(View.GONE);
            } else {
                done.setVisibility(View.VISIBLE);
            }
        }
    }
}
