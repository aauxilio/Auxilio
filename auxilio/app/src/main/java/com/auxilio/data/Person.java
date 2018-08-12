package com.auxilio.data;

/**
 * Model to hold values for Person on Application.
 */
public class Person {

    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String address;

    private Person(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastname;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public static class Builder {

        private String firstName;
        private String lastname;
        private String phoneNumber;
        private String address;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}

