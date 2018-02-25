package com.example.hkamath.gimmeshelterapp.model;

import java.util.Date;

/**
 * Created by crsch on 2/25/2018.
 */

public class AdminUser extends User {
    public AdminUser(String firstName, String lastName, String username, String password, Gender gender, Date birthDate) {
        super(firstName, lastName, username, password, gender, birthDate, true);
    }
}
