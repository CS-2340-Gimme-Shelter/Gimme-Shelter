/*
public void onPostExecute(Boolean success, String error) {
        mAuthTask = null;

        if (success.booleanValue()) {
            Intent myIntent = new Intent(LoginScreen.this,
                    HomePage.class);
            // Can't hit back button and come here
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
            finish();
        } else {
            mEmailView.setError(error);
            mEmailView.requestFocus();
            showProgress(false);
        }
    }
*/

package com.example.hkamath.gimmeshelterapp;
import com.example.hkamath.gimmeshelterapp.model.Gender;
import com.example.hkamath.gimmeshelterapp.model.User;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
/**
 * Created by uday on 4/9/18.
 */

public class HarishUnitTest {

    /**
     * Tests the PostExecute function described above.
     */
    @Test
    public void userEquals() throws Exception {
        User a = new User("Uday", "Patil", "udpatil", "password", Gender.MALE, new Date(1998, 5, 26), false, null);
        User b = new User("Uday", "Patil", "udpatil", "password", Gender.MALE, new Date(1998, 5, 26), false, null);
        String c = "udpatil";
        assertEquals(a, b);
        assertEquals(a, a);
        assertNotEquals(a, c);
    }
}
