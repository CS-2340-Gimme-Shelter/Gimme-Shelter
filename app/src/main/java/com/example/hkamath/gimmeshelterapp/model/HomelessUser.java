package com.example.hkamath.gimmeshelterapp.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by crsch on 2/25/2018.
 */

public class HomelessUser extends User {
    public HomelessUser(String firstName, String lastName, String username, String password, Gender gender, Date birthDate, FirebaseUser fuser) {
        super(firstName, lastName, username, password, gender, birthDate, false, fuser);
    }
}
