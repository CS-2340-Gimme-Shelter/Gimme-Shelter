package com.example.hkamath.gimmeshelterapp.model;

import android.util.Patterns;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by crsch on 2/25/2018.
 */

public class User {

    private static User currentUser;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Gender gender;
    private Date birthDate;
    private boolean admin;
    private long bedRequestedShelter = -1;

    private FirebaseUser fuser;

    public User() {

    }

    public User(String firstName, String lastName, String username, String password, Gender gender, Date birthDate, boolean admin, FirebaseUser fuser) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.admin = admin;
        this.fuser = fuser;
    }

    public FirebaseUser getFirebaseUser() {
        return fuser;
    }

    public void setFuser(FirebaseUser fuser) {
        this.fuser = fuser;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public static void loginAs(User u) {
        currentUser = u;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public long getBedRequestedShelter() {
        return bedRequestedShelter;
    }

    public void setBedRequestedShelter(long bedRequestedShelter) {
        this.bedRequestedShelter = bedRequestedShelter;
    }

    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                ", admin=" + admin +
                '}';
    }
}
