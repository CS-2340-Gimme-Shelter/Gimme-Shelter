package com.example.hkamath.gimmeshelterapp.model;

/**
 * Created by crsch on 3/6/2018.
 */

public enum ShelterGender {
    UNRESTRICTED("Any"), MEN("Men"), WOMEN("Women"), OTHER("Other");

    private String name;

    ShelterGender(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
