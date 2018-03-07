package com.example.hkamath.gimmeshelterapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.drive.metadata.internal.ParentDriveIdSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crsch on 3/5/2018.
 */

public class Shelter implements Parcelable{

    private String address;
    private long capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String shelterName;
    private String specialNotes;
    private long uniqueKey;
    private ShelterGender gender;
    private List<ShelterRestriction> restrictions = new ArrayList<>();

    public Shelter(Parcel parcel) {
        address = parcel.readString();
        capacity = parcel.readLong();
        latitude = parcel.readDouble();
        longitude = parcel.readDouble();
        phoneNumber = parcel.readString();
        shelterName = parcel.readString();
        specialNotes = parcel.readString();
        uniqueKey = parcel.readLong();
        gender = (ShelterGender) parcel.readSerializable();
        parcel.readList(restrictions, null);
    }

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
        ShelterHandler.addShelter(this);
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
                "gender='" + gender + '\'' +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        /*
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
         */
        parcel.writeString(address);
        parcel.writeLong(capacity);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeString(phoneNumber);
        parcel.writeString(shelterName);
        parcel.writeString(specialNotes);
        parcel.writeLong(uniqueKey);
        parcel.writeSerializable(gender);
        parcel.writeList(restrictions);
    }

    public static final Parcelable.Creator<Shelter> CREATOR = new Parcelable.Creator<Shelter>() {

        @Override
        public Shelter createFromParcel(Parcel parcel) {
            return new Shelter(parcel);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };
}
