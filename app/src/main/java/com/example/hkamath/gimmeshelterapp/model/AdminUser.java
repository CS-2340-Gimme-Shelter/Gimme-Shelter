package com.example.hkamath.gimmeshelterapp.model;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

/**
 * Created by crsch on 2/25/2018.
 */

public class AdminUser extends User {
    public AdminUser(String firstName, String lastName, String email, String password, Gender gender, Date birthDate, FirebaseUser fuser) {
        super(firstName, lastName, email, password, gender, birthDate, true, fuser);
    }
}
