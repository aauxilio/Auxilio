package com.auxilio.auxilio;

import com.auxilio.auxilio.data.AffidivitApplication;
import com.auxilio.auxilio.data.ChildInformation;
import com.auxilio.auxilio.data.Person;

import java.util.ArrayList;
import java.util.List;


public class DataUtils {

    private static int numOfRelatives;
    private static Person parent;
    private static List<Person> relatives;

    private static AffidivitApplication.Builder affidivitApplication;

    private DataUtils() {
        numOfRelatives = 0;
    }

    public static AffidivitApplication.Builder getAffidivitApplication() {
        if (affidivitApplication == null) {
            affidivitApplication = new AffidivitApplication.Builder();
        }
        return affidivitApplication;
    }

    public static int getNumOfRelatives() {
        return numOfRelatives;
    }

    public static void addRelative(Person person) {
        relatives.add(person);
        numOfRelatives++;
    }

    public static void addParent(Person person) {
        parent = person;
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