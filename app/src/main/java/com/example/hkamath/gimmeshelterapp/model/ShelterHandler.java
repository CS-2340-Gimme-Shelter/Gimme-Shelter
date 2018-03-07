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

    public static Object[] getOrderedShelterList(ShelterGender gender, ShelterRestriction...restrictions) {
        Log.d("shelters", gender + ", " + Arrays.toString(restrictions));
        List<Shelter> genderList = getSheltersByGenderFit(gender);
        Log.d("gender-list", genderList.toString());
        List<Shelter> restrictionEqualList = getSheltersRestrictionMatch(genderList, restrictions);
        List<Shelter> restrictionOutList = getSheltersRestrictionOutFit(genderList, restrictions);
        List<Shelter> restrictionInList = getSheltersRestrictionInFit(genderList, restrictions);
        List<Shelter> finalList = new ArrayList<>();
        finalList.addAll(restrictionEqualList);
        for (Shelter s: restrictionOutList) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }
        for (Shelter s: restrictionInList) {
            if (!finalList.contains(s)) {
                finalList.add(s);
            }
        }

        return finalList.toArray();
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
