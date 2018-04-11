package com.example.hkamath.gimmeshelterapp;

import com.example.hkamath.gimmeshelterapp.model.Gender;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by crsch on 4/11/2018.
 */
public class CarlUnitTest {

    @Test
    public void shelterEqualsTest() {
        Gender g1 = Gender.MALE;
        Gender g2 = Gender.FEMALE;
        Gender g3 = Gender.MALE;

        assertTrue(g1.equals(g1));
        assertFalse(g1.equals(null));
        assertFalse(g1.equals(g2));
        assertTrue(g1.equals(g3));
    }
}
