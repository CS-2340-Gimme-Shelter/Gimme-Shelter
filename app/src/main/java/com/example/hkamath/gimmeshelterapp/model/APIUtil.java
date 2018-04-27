package com.example.hkamath.gimmeshelterapp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hkamath.gimmeshelterapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by crsch on 2/25/2018.
 */

public class APIUtil {

    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static boolean giveShelterGuest(Shelter shelter, User user, int num) {
        long capacity = shelter.getCapacity();
        int numReserved = (int) shelter.getVisitors().values().stream().mapToInt(Number::intValue).sum();
        if (capacity < numReserved + num) {
            return false;
        }
        database.getReference("Shelter").child(shelter.getUniqueKey() + "").child("visitors")
                .child(user.getFirebaseUser().getUid()).setValue(num);
        database.getReference("User").child(user.getFirebaseUser().getUid()).child("bedRequestedShelter").setValue(shelter.getUniqueKey());
        user.setBedRequestedShelter(shelter.getUniqueKey());
        shelter.getVisitors().put(user.getFirebaseUser().getUid(), num);

        return true;
    }

    public static void removeShelterGuest(Shelter shelter, User user) {
        database.getReference("Shelter").child(shelter.getUniqueKey() + "").child("visitors")
                .child(user.getFirebaseUser().getUid()).setValue(0);
        database.getReference("User").child(user.getFirebaseUser().getUid()).child("bedRequestedShelter").setValue(null);
        user.setBedRequestedShelter(-1);
        shelter.getVisitors().put(user.getFirebaseUser().getUid(), 0);
    }

    public static class GrabSheltersTask {
        public static void fetch(final ShelterFetchCallback callback) {
            DatabaseReference myRef = database.getReference("Shelter");
            Log.d("Shelters", "Fetching shelters");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<Shelter> shelters = new ArrayList<Shelter>();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Shelter shelter = snapshot.getValue(Shelter.class);
                        ShelterHandler.addShelter(shelter);
                        shelters.add(shelter);
                        Log.d("Shelter", shelter.toString());
                    }

                    callback.sheltersFetched(shelters);
                    watchShelters();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onFail();
                }
            });
        }
    }

    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public static class UserRegistratonTask {

        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Gender gender;
        private Date birthDate;
        private boolean admin;

        private UserLoginCallback callback;

        public UserRegistratonTask(String firstName, String lastName, String email, String password, Gender gender, Date birthDate, boolean admin, UserLoginCallback callback) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.gender = gender;
            this.birthDate = birthDate;
            this.admin = admin;
            this.callback = callback;
        }

        public void register() {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("Auth", (task.getResult().getUser() == null) + "");
                        FirebaseUser fuser = task.getResult().getUser();
                        DatabaseReference myRef = database.getReference("User").child(fuser.getUid());
                        DatabaseReference myRef2 = database.getReference("BannedUsers").child(email.replace(".", ""));

                        User regUser;
                        if (admin) {
                            regUser = new AdminUser(firstName, lastName, email, password, gender, birthDate, fuser);
                        } else {
                            regUser = new HomelessUser(firstName, lastName, email, password, gender, birthDate, fuser);
                        }
                        myRef.setValue(regUser);
                        myRef2.setValue(false);
                        User.loginAs(regUser);
                        callback.onPostExecute(true, null);
                    } else {
                        callback.onPostExecute(false, R.string.email_in_use);
                    }

                }
            });
        }
    }

    /**
     *  Represents an asynchronous task to reset the user's password
     */
    public static class ResetPasswordTask {
        private String mEmail;
        private ResetPasswordCallback mCallback;

        public ResetPasswordTask(String email, ResetPasswordCallback callback) {
            mEmail = email;
            mCallback = callback;
        }


        public void resetPassword() {
            auth.sendPasswordResetEmail(mEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("APIUtil", "password reset email sent");
                                mCallback.onResetPassword(true, R.string.password_reset_email_sent);
                            }
                        }
                    });
        }

    }

    abstract public static class LoginTask {
        protected UserLoginCallback callback;

        public LoginTask(UserLoginCallback callback) {
         this.callback = callback;
        }

        abstract public void signIn();
    }

    public static class AnonLoginTask extends LoginTask{

        public AnonLoginTask(UserLoginCallback callback) {
            super(callback);
        }

        @Override
        public void signIn() {
            auth.signInAnonymously()
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fuser = task.getResult().getUser();
                                User user = new Guest(fuser);
                                User.loginAs(user);
                                callback.onPostExecute(true, null);
                            } else {
                                callback.onPostExecute(false, "Guest Login Failed");
                            }
                        }
                    });
        }
    }

    public static class BanUserTask {
        private String mEmail;

        public BanUserTask(String email) {
            mEmail = email.replace(".", "");
        }

        public void banUser(boolean disable) {
            DatabaseReference myDB = database.getReference("BannedUsers").child(mEmail);
            myDB.setValue(disable);
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public static class UserLoginTask extends LoginTask{

        private final String mEmail;
        private final String mPassword;

        public UserLoginTask(String email, String password, UserLoginCallback callback) {
            super(callback);
            this.mEmail = email;
            mPassword = password;

        }

        @Override
        public void signIn() {

            DatabaseReference myDB = database.getReference("BannedUsers").child(mEmail.replace(".", ""));

            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null && (boolean) dataSnapshot.getValue()) {
                        callback.onPostExecute(false, "User is banned");
                    } else {
                        auth.signInWithEmailAndPassword(mEmail, mPassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser fuser = task.getResult().getUser();
                                            DatabaseReference myRef = database.getReference("User").child(fuser.getUid());
                                            myRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // This method is called once with the initial value and again
                                                    // whenever data at this location is updated.

                                                    User user = dataSnapshot.getValue(User.class);
                                                    user.setFuser(fuser);
                                                    User.loginAs(user);
                                                    // Make sure we continue to update User but don't
                                                    if (callback != null) {
                                                        callback.onPostExecute(true, null);
                                                    }
                                                    callback = null;
                                                    Log.d("Auth", user.toString());
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError error) {
                                                    // Failed to read value
                                                    auth.signOut();
                                                    if (callback != null) {
                                                        callback.onPostExecute(false, error.getMessage());
                                                    }
                                                    callback = null;
                                                }
                                            });

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            callback.onPostExecute(false, R.string.error_email_password_not_found);
                                        }

                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("ban error", databaseError.getMessage());
                    callback.onPostExecute(false, "Error parsing ban status");
                }
            };
            myDB.addListenerForSingleValueEvent(listener);


        }

    }

    public static void watchShelters() {
        database.getReference("Shelter").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Shelter> shelters = new ArrayList<Shelter>();
                ShelterHandler.clearShelters();
                Log.d("Shelter", "Shelters updating");
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Shelter shelter = snapshot.getValue(Shelter.class);
                    ShelterHandler.addShelter(shelter);
                    shelters.add(shelter);
                    Log.d("Shelter", shelter.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Shelter", "Error: " + databaseError);
            }
        });
    }
}
