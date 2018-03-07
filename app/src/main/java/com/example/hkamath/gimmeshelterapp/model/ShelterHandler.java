package com.example.hkamath.gimmeshelterapp.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by crsch on 3/7/2018.
 */

public class ShelterHandler {

    private static List<Shelter> shelters = new ArrayList<Shelter>();

    public static void addShelter(Shelter s) {
        shelters.add(s);
    }

    public static List<Shelter> getShelters() {
        return shelters;
    }

    public static Object[] getOrderedShelterList(ShelterGender gender, String name, ShelterRestriction...restrictions) {

        List<Shelter> nameList = getSheltersByNameMatch(name);
        List<Shelter> nameInList = getSheltersByNameIn(name);
        List<Shelter> genderNameList = getSheltersByGenderFit(gender, nameList);
        List<Shelter> genderNameInList = getSheltersByGenderFit(gender, nameInList);

        List<Shelter> restrictionEqualList = getSheltersRestrictionMatch(genderNameList, restrictions);
        List<Shelter> restrictionOutList = getSheltersRestrictionOutFit(genderNameList, restrictions);
        List<Shelter> restrictionInList = getSheltersRestrictionInFit(genderNameList, restrictions);
        List<Shelter> restrictionEqualListInName = getSheltersRestrictionMatch(genderNameInList, restrictions);
        List<Shelter> restrictionOutListInName = getSheltersRestrictionOutFit(genderNameInList, restrictions);
        List<Shelter> restrictionInListInName = getSheltersRestrictionInFit(genderNameInList, restrictions);

        List<Shelter> finalList = new ArrayList<>();

        // Add all exact matches
        finalList.addAll(restrictionEqualList);

        // Add name in and restriction match
        for (Shelter s: restrictionEqualListInName) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }

        // Add name match restriction out
        for (Shelter s: restrictionOutList) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }

        // Add name in restriction out
        for (Shelter s: restrictionOutListInName) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }

        // Add name match restriction in
        for (Shelter s: restrictionInList) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }

        // Add name in restriction in
        for (Shelter s: restrictionInListInName) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }

        return finalList.toArray();
    }

    public static List<Shelter> getSheltersByNameMatch(String name) {
        return getSheltersByNameMatch(name, null);
    }

    public static List<Shelter> getSheltersByNameMatch(String name, List<Shelter> preList) {
        return getSheltersByPredicate(shelter -> {
            return shelter.getShelterName().equalsIgnoreCase(name);
        }, preList);
    }

    public static List<Shelter> getSheltersByNameIn(String name) {
        return getSheltersByNameIn(name, null);
    }

    public static List<Shelter> getSheltersByNameIn(String name, List<Shelter> preList) {
        return getSheltersByPredicate(shelter -> {
            return shelter.getShelterName().toLowerCase().contains(name.toLowerCase());
        }, preList);
    }

    public static List<Shelter> getSheltersByGenderMatch(ShelterGender gender) {
        return getSheltersByGenderMatch(gender, null);
    }

    public static List<Shelter> getSheltersByGenderMatch(ShelterGender gender, List<Shelter> preList) {
        return getSheltersByPredicate(shelter -> {
            return shelter.getGender() == gender;
        }, preList);
    }

    public static List<Shelter> getSheltersByGenderFit(ShelterGender gender) {
        return getSheltersByGenderFit(gender, null);
    }

    public static List<Shelter> getSheltersByGenderFit(ShelterGender gender, List<Shelter> preList) {
        return getSheltersByPredicate(shelter -> {
            if (gender == ShelterGender.UNRESTRICTED) {
                return true;
            }
            return shelter.getGender() == gender || shelter.getGender() == ShelterGender.UNRESTRICTED;
        }, preList);
    }

    public static List<Shelter> getSheltersRestrictionMatch(ShelterRestriction ... restrictions) {
        return getSheltersRestrictionMatch(null, restrictions);
    }

    public static List<Shelter> getSheltersRestrictionMatch(List<Shelter> preList, ShelterRestriction ... restrictions) {
        return getSheltersByPredicate(shelter -> {
            return shelter.getRestrictions().containsAll(Arrays.asList(restrictions));
        }, preList);
    }

    public static List<Shelter> getSheltersRestrictionOutFit(ShelterRestriction ... restrictions) {
        return getSheltersRestrictionOutFit(null, restrictions);
    }

    public static List<Shelter> getSheltersRestrictionOutFit(List<Shelter> preList, ShelterRestriction ... restrictions) {
        return getSheltersByPredicate(shelter -> {
            return Arrays.asList(restrictions).containsAll(shelter.getRestrictions());
        }, preList);
    }

    public static List<Shelter> getSheltersRestrictionInFit(ShelterRestriction ... restrictions) {
        return getSheltersRestrictionInFit(null, restrictions);
    }

    public static List<Shelter> getSheltersRestrictionInFit(List<Shelter> preList, ShelterRestriction ... restrictions) {
        return getSheltersByPredicate(shelter -> {
            if (shelter.getRestrictions().contains(ShelterRestriction.NONE)) {
                return true;
            }
            return shelter.getRestrictions().containsAll(Arrays.asList(restrictions));
        }, preList);
    }

    public static List<Shelter> getSheltersByPredicate(Predicate<Shelter> pred, List<Shelter> preList) {
        if (preList == null) {
            preList = shelters;
        }
        List<Shelter> ret = new ArrayList<Shelter>();
        for (Shelter s: preList) {
            if (pred.test(s)) {
                ret.add(s);
            }
        }

        return ret;
    }
}
