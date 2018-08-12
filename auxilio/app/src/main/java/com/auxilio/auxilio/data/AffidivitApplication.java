package com.auxilio.auxilio.data;

import java.util.List;

/**
 * Model to whold values for Application.
 */
public class AffidivitApplication {

    private final ChildInformation childInformation;
    private final Person parent;
    private final List<Person> relatives;

    public AffidivitApplication(Builder builder) {
        this.childInformation = builder.childInformation;
        this.parent = builder.parent;
        this.relatives = builder.relatives;
    }

    public static class Builder {

        private ChildInformation childInformation;
        private Person parent;
        private List<Person> relatives;

        public Builder parent(Person parent) {
            this.parent = parent;
            return this;
        }

        public Builder relatives(List<Person> relatives) {
            this.relatives = relatives;
            return this;
        }

        public Builder childInformation(ChildInformation childInformation) {
            this.childInformation = childInformation;
            return this;
        }


        public AffidivitApplication build() {
            return new AffidivitApplication(this);
        }
    }
}


