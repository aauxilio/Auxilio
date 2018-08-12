package com.auxilio.auxilio;

import com.auxilio.auxilio.data.AffidivitApplication;
import com.auxilio.auxilio.data.ChildInformation;
import com.auxilio.auxilio.data.Person;

import java.util.ArrayList;
import java.util.List;


public class DataUtils {

    private static int numOfChildren;
    private static int numOfRelatives;
    private static List<ChildInformation> childrenInformation;
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

    public static int getNumOfChildren() {
        return numOfChildren;
    }

    public static int getNumOfRelatives() {
        return numOfRelatives;
    }

    public static Person getParent() {
        return parent;
    }

    public static void addRelative(Person person) {
        if (relatives == null) {
            relatives = new ArrayList<>();
        }

        relatives.add(person);
        numOfRelatives++;
    }

    public static void addParent(Person person) {
        parent = person;
    }

    public static void addChild(ChildInformation childInformation) {
        if (childrenInformation == null) {
            childrenInformation = new ArrayList<>();
        }
        childrenInformation.add(childInformation);
        numOfChildren++;
    }

    public static AffidivitApplication.Builder initDefaults() {

        ChildInformation childInformation = new ChildInformation.Builder()
                .dob("2/15/1990")
                .firstName("Juan")
                .lastName("Sanchez")
                .build();
        Person person = new Person.Builder()
                .address("123 El Camino Real, Los Gatos 90901")
                .firstName("Benito")
                .lastName("Sanchez")
                .phoneNumber("(831) 898 4932")
                .build();

        List<Person> relatives = new ArrayList<>();
        Person person1 = new Person.Builder()
                .address("1455 Market Street, San Francisco CA")
                .firstName("Jorge")
                .lastName("Orrantia")
                .phoneNumber("+15106480370")
                .build();

        Person person2 = new Person.Builder()
                .address("190 Maple Ave, Greenfield CA 93927")
                .firstName("Jose")
                .lastName("Sanchez")
                .phoneNumber("+18317768550")
                .build();
        relatives.add(person1);
        relatives.add(person2);

        affidivitApplication = new AffidivitApplication.Builder()
                .childInformation(childInformation)
                .parent(person)
                .relatives(relatives);

        return affidivitApplication;
    }
}
