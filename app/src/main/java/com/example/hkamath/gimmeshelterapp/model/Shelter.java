package com.example.hkamath.gimmeshelterapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crsch on 3/5/2018.
 */

public class Shelter {

    private static List<Shelter> shelters = new ArrayList<Shelter>();

    private String address;
    private long capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String shelterName;
    private String specialNotes;
    private long uniqueKey;
    private ShelterGender gender;
    private List<ShelterRestriction> restrictions;

    public Shelter(String address, long capacity, double latitude, double longitude, String phoneNumber, List<ShelterRestriction> restrictions, String shelterName, String specialNotes, long uniqueKey, ShelterGender gender) {
        this();
        this.address = address;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
        this.shelterName = shelterName;
        this.specialNotes = specialNotes;
        this.uniqueKey = uniqueKey;
        this.gender = gender;
    }

    public Shelter() {
        shelters.add(this);
    }

    public static List<Shelter> getShelters() {
        return shelters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ShelterRestriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<ShelterRestriction> restrictions) {
        this.restrictions = restrictions;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public long getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(long uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public ShelterGender getGender() {
        return gender;
    }

    public void setGender(ShelterGender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "address='" + address + '\'' +
                ", capacity=" + capacity +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", restrictions='" + restrictions + '\'' +
                ", shelterName='" + shelterName + '\'' +
                ", specialNotes='" + specialNotes + '\'' +
                ", uniqueKey=" + uniqueKey +
                '}';
    }

    public String getRestrictionsString() {
        return "Gender: " + gender.toString() + ", Other: " + restrictions.toString();
    }
}
