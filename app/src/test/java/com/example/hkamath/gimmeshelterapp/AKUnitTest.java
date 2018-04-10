
















/*
public static Shelter getShelterById(long id) {
        for (Shelter s: shelters) {
            if (s.getUniqueKey() == id) {
                return s;
            }
        }
        return null;
    }



*/

package com.example.hkamath.gimmeshelterapp;
import com.example.hkamath.gimmeshelterapp.model.Shelter;
import com.example.hkamath.gimmeshelterapp.model.ShelterHandler;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by uday on 4/9/18.
 */

public class AKUnitTest {

    /**
     * Tests the getShelterByID method by passing in an id (long) and checking
     * validity of the method return
     */
    @Test
    public void shelterFind() throws Exception {
	
	Shelter shelter1 = new Shelter("address", 5, 5, 5, "5", null, "shelter", "notes", 12345, null);
	Shelter shelter2 = new Shelter("address", 5, 5, 5, "5", null, "shelter", "notes", 12346, null);
	ShelterHandler handler = new ShelterHandler();
	handler.addShelter(shelter1);
	handler.addShelter(shelter2);
	assertEquals(shelter1, handler.getShelterById(1245));
	assertEquals(shelter2, handler.getShelterById(1246));
	assertEquals(null, handler.getShelterById(3));
	handler.clearShelters();
	assertEquals(null, handler.getShelterById(12345));
    }
}
