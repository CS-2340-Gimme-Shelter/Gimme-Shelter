package com.example.hkamath.gimmeshelterapp.model;

/**
 * Created by crsch on 3/6/2018.
 */

public enum ShelterRestriction {
    CHILDREN("Children"), FAMILIES("Families"),
    FAMILIES_YOUNG_CHILDREN("Families with young children"),
    FAMILIES_NEWBORNS("Families with newborns"), YOUNG_ADULTS("Young adults"), ADULTS("Adults"),
    SENIORS("Seniors"), VETERANS("Veterans"), NONE("None");

    private String name;

    ShelterRestriction(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
