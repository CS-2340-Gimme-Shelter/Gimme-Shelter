/*
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
*/

package com.example.hkamath.gimmeshelterapp;
import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.User;
import com.example.hkamath.gimmeshelterapp.model.APIUtil;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
/**
 * Created by uday on 4/9/18.
 */

public class HarishUnitTest {

    /**
     * Tests the giveShelterGuest function described above, in the APIUtil.java class
     */
    @Test
    public void giveShelterGuestTest() throws Exception {
        Shelter s1 = new Shelter("address",2,10,10,"number",null,"s1","None",1,null);
        User u1 = new User("u1","last","user1","pass1",null,null,false,null);
        User u2 = new User("u2","last","user1","pass1",null,null,false,null);
        User u3 = new User("u3","last","user1","pass1",null,null,false,null);

        assertEquals(true,APIUtil.giveShelterGuest(s1,u1,1));
        assertEquals(true,APIUtil.giveShelterGuest(s1,u2,1));
        assertEquals(false,APIUtil.giveShelterGuest(s1,u3,1));
        s1.setCapacity(3);
        assertEquals(true,APIUtil.giveShelterGuest(s1,u3,1));
    }
}
