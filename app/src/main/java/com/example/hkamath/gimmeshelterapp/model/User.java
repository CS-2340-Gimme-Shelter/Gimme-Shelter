package com.example.hkamath.gimmeshelterapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by crsch on 2/25/2018.
 */

public abstract class User {

    private static List<User> userList = new ArrayList<User>();
    private static User currentUser;

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Gender gender;
    private Date birthDate;
    private boolean admin;

    public User(String firstName, String lastName, String username, String password, Gender gender, Date birthDate, boolean admin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.admin = admin;
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

    public static boolean userPresent(User u) {
        return userList.contains(u);
    }

    public static User findUser(String username, String password) {
        for (User u: userList) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }

    public static void addRegisteredUser(User u) {
        userList.add(u);
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

    public static boolean isUsernameValid(String username) {
        return username.length() > 2;
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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
}
