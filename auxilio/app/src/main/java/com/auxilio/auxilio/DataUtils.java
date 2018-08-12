package com.auxilio.auxilio;

import com.auxilio.auxilio.data.AffidivitApplication;
import com.auxilio.auxilio.data.ChildInformation;
import com.auxilio.auxilio.data.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 8/11/18.
 */

public class DataUtils {

    private static AffidivitApplication affidivitApplication;

    private DataUtils() { }

    public static AffidivitApplication getAffidivitApplication() {
        if (affidivitApplication == null) {
            affidivitApplication = new AffidivitApplication(null);
        }
        return affidivitApplication;
    }

    public void initDefaults() {

        ChildInformation childInformation = new ChildInformation.Builder()
                .dob("2/15/1990")
                .firstName("Juan")
                .lastName("Sanchez")
                .build();
        Person person = new Person.Builder()
                .address("123 El Camino Real, Los Gatos 90901")
                .firstName("Carlos")
                .lastName("Sanchez")
                .phoneNumber("(831) 898 4932")
                .build();

        List<Person> relatives = new ArrayList<>();
        Person person1 = new Person.Builder()
                .address("1455 Market Street, San Francisco CA")
                .firstName("Rogelio Sanchez")
                .lastName("Sanchez")
                .phoneNumber("(831) 999-1930")
                .build();

        Person person2 = new Person.Builder()
                .address("190 Maple Ave, Greenfield CA 93927")
                .firstName("Guillermo")
                .lastName("Sanchez")
                .phoneNumber("(831) 999-1930")
                .build();
        relatives.add(person1);
        relatives.add(person2);

        AffidivitApplication builder = new AffidivitApplication.Builder()
                .childInformation(childInformation)
                .parent(person)
                .relatives(relatives)
                .build();
    }
}
