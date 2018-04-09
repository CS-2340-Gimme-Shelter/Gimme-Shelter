package com.example.hkamath.gimmeshelterapp;
import com.example.hkamath.gimmeshelterapp.model.Gender;
import com.example.hkamath.gimmeshelterapp.model.User;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
/**
 * Created by uday on 4/9/18.
 */

public class UdayUnitTest {

    /**
     * Tests the equals method in the User class to test for same object
     * and different objects with same fields
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
