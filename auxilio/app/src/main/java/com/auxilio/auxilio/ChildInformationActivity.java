package com.auxilio.auxilio;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.auxilio.auxilio.data.ChildInformation;

public class ChildInformationActivity extends AppCompatActivity {

    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private TextInputEditText dob;

    Button childNextButton;
    Button childDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_information);
        firstName = findViewById(R.id.child_first_name);
        lastName = findViewById(R.id.child_last_name);
        childDoneButton = findViewById(R.id.child_done_button);
        childNextButton = findViewById(R.id.child_next_button);
        dob = findViewById(R.id.child_dob);

        updateButtons();
        
        childNextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addChild(buildChildInformation());
                    }
                }
        );
    }

    private void addChild(ChildInformation childInformation) {
        DataUtils.addChild(childInformation);
    }

    private ChildInformation buildChildInformation() {
        return new ChildInformation.Builder()
                .firstName(firstName.getText().toString())
                .lastName(lastName.getText().toString())
                .dob(dob.getText().toString())
                .build();
    }


    private void updateButtons() {
        if (DataUtils.getNumOfChildren() == 0) {
            childDoneButton.setVisibility(View.GONE);
        } else {
            childDoneButton.setVisibility(View.VISIBLE);
        }
    }
}
