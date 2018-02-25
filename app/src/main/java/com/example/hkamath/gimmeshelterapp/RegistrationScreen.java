package com.example.hkamath.gimmeshelterapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hkamath.gimmeshelterapp.model.APIUtil;
import com.example.hkamath.gimmeshelterapp.model.Gender;
import com.example.hkamath.gimmeshelterapp.model.User;
import com.example.hkamath.gimmeshelterapp.model.UserLoginCallback;

import java.util.Calendar;

public class RegistrationScreen extends AppCompatActivity implements UserLoginCallback {

    private View mRegistrationFormView;
    private APIUtil.UserRegistratonTask mAuthTask;

    private AutoCompleteTextView mFirstNameView;
    private AutoCompleteTextView mLastNameView;
    private AutoCompleteTextView mUserNameView;
    private EditText mPasswordView;
    private Spinner mGenderSpinner;
    private TextView mBDateView;
    private int selectedBirthYear;
    private int selectedBirthMonth;
    private int selectedBirthDay;
    boolean dateSelected = false;
    private CheckBox mAdminView;

    private Button mSubmitButton;
    private Button mCancelButton;



    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        dateSelected = false;

        mRegistrationFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.reg_progress);

        mFirstNameView = findViewById(R.id.reg_firstName);
        mLastNameView = findViewById(R.id.reg_lastName);
        mUserNameView = findViewById(R.id.reg_username);
        mPasswordView = findViewById(R.id.reg_password);
        mBDateView = findViewById(R.id.reg_bdate);
        mAdminView = findViewById(R.id.reg_admin);
        mSubmitButton = findViewById(R.id.register);
        mCancelButton = findViewById(R.id.reg_cancel_button);

        // Set up the gender drop down
        mGenderSpinner = findViewById(R.id.reg_gender);
        mGenderSpinner.setAdapter(new ArrayAdapter<Gender>(this, android.R.layout.simple_spinner_item, Gender.values()));

        // Create DateSetListener for DatePicker
        final DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedBirthYear = year;
                selectedBirthMonth = month;
                selectedBirthDay = day;
                dateSelected = true;
                mBDateView.setText(String.format("%2d/%2d/%4d", selectedBirthMonth + 1, selectedBirthDay, selectedBirthYear));
            }
        };

        // Create Date picker when mDBate is clicked
        mBDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = 1995;
                int month = 5;
                int day = 15;

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistrationScreen.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener, year, month, day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void attemptLogin() {

        // Reset errors.
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mUserNameView.setError(null);
        mPasswordView.setError(null);
        mBDateView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        Gender gender = (Gender)mGenderSpinner.getSelectedItem();

        boolean admin = mAdminView.isChecked();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid first name.
        if (!dateSelected) {
            mBDateView.setError(getString(R.string.error_field_required));
            focusView = mBDateView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if(!User.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (!User.isUsernameValid(username)) {
            mUserNameView.setError(getString(R.string.error_invalid_username));
            focusView = mUserNameView;
            cancel = true;
        }

        // Check for a valid last name.
        if (TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            cancel = true;
        }

        // Check for a valid first name.
        if (TextUtils.isEmpty(firstName)) {
            mFirstNameView.setError(getString(R.string.error_field_required));
            focusView = mFirstNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            Calendar c = Calendar.getInstance();
            c.set(selectedBirthYear, selectedBirthMonth, selectedBirthDay, 0, 0);

            mAuthTask = new APIUtil.UserRegistratonTask(firstName, lastName, username, password,
                    gender, c.getTime(), admin, this);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onPostExecute(Boolean success) {
        mAuthTask = null;

        if (success.booleanValue()) {
            Intent myIntent = new Intent(this,
                    HomePage.class);
            startActivity(myIntent);
        } else {
            mUserNameView.setError("Username already in use!");
            mUserNameView.requestFocus();
            showProgress(false);
        }
    }

    @Override
    public void onCancelled() {
        mAuthTask = null;
        showProgress(false);
    }
}
