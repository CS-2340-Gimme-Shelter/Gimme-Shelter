package com.example.hkamath.gimmeshelterapp.model;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.hkamath.gimmeshelterapp.HomePage;
import com.example.hkamath.gimmeshelterapp.LoginScreen;
import com.example.hkamath.gimmeshelterapp.R;

import java.util.Date;

/**
 * Created by crsch on 2/25/2018.
 */

public class APIUtil {

    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public static class UserRegistratonTask extends AsyncTask<Void, Void, Boolean> {

        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private Gender gender;
        private Date birthDate;
        private boolean admin;

        private UserLoginCallback callback;

        public UserRegistratonTask(String firstName, String lastName, String username, String password, Gender gender, Date birthDate, boolean admin, UserLoginCallback callback) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.gender = gender;
            this.birthDate = birthDate;
            this.admin = admin;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            User regUser;

            if (admin) {
                 regUser = new AdminUser(firstName, lastName, username, password, gender, birthDate);
            } else {
                regUser = new HomelessUser(firstName, lastName, username, password, gender, birthDate);
            }

            if (User.userPresent(regUser)) {
                return false;
            } else {
                User.addRegisteredUser(regUser);
                User.loginAs(regUser);
                return true;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            callback.onPostExecute(success);
        }

        @Override
        protected void onCancelled() {
            callback.onCancelled();
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public static class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        private UserLoginCallback callback;

        public UserLoginTask(String username, String password, UserLoginCallback callback) {
            mUsername = username;
            mPassword = password;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            User user = User.findUser(mUsername, mPassword);
            if (user == null) {
                return false;
            } else {
                User.loginAs(user);
                return true;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            callback.onPostExecute(success);

        }

        @Override
        protected void onCancelled() {
            callback.onCancelled();
        }
    }
}
