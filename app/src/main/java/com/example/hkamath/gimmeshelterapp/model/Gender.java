package com.example.hkamath.gimmeshelterapp.model;

/**
 * Created by crsch on 2/25/2018.
 */

public enum Gender {
    MALE("male"), FEMALE("female"), OTHER("other");

    private final String name;

    Gender(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String toString() {
        return getName();
    }
}
