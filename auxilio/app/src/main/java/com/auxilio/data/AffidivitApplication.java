package com.auxilio.data;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Model to whold values for Application.
 */
public class AffidivitApplication {

    @Nullable private ChildInformation childInformation;
    @Nullable private Person parent;
    @Nullable private List<Person> relatives;

    public AffidivitApplication(@Nullable Builder builder) {
        if (builder == null) {
            return;
        }
        this.childInformation = builder.childInformation;
        this.parent = builder.parent;
        this.relatives = builder.relatives;
    }

    @Nullable
    public ChildInformation getChildInformation() {
        return childInformation;
    }

    @Nullable
    public Person getParent() {
        return parent;
    }

    @Nullable
    public List<Person> getRelatives() {
        return relatives;
    }

    public static class Builder {

        private ChildInformation childInformation;
        private Person parent;
        private List<Person> relatives;

        public Builder parent(@Nullable Person parent) {
            this.parent = parent;
            return this;
        }

        public Builder relatives(@Nullable List<Person> relatives) {
            this.relatives = relatives;
            return this;
        }

        public Builder childInformation(@Nullable ChildInformation childInformation) {
            this.childInformation = childInformation;
            return this;
        }


        public AffidivitApplication build() {
            return new AffidivitApplication(this);
        }
    }
}


