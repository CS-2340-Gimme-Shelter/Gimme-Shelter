package com.example.hkamath.gimmeshelterapp.model;

/**
 * Created by crsch on 3/6/2018.
 */

public enum ShelterGender {
    MEN("Men"), WOMEN("Women"), OTHER("Other"), UNRESTRICTED("Any");

    private String name;

    ShelterGender(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
