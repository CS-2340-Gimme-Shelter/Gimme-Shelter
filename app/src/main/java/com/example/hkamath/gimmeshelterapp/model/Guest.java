package com.example.hkamath.gimmeshelterapp.model;

import com.google.firebase.auth.FirebaseUser;


/**
 * Created by uday on 4/26/18.
 */

public class Guest extends User {
    public Guest(FirebaseUser fuser) {
        super("guest", "guest", "guest", null, Gender.MALE, null, false, fuser);
    }
}
