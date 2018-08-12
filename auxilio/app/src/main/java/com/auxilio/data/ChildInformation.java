package com.auxilio.data;

/**
 * Created by ben on 8/11/18.
 */

public class ChildInformation {

    private final String firstName;
    private final String lastName;
    private final String dob;

    public ChildInformation(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dob = builder.dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDob() {
        return dob;
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private String dob;


        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastname) {
            this.lastName = lastname;
            return this;
        }

        public Builder dob(String dob) {
            this.dob = dob;
            return this;
        }

        public ChildInformation build() {
            return new ChildInformation(this);
        }
    }
}
