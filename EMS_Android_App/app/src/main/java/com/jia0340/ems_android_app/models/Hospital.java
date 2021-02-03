package com.jia0340.ems_android_app.models;

import java.util.ArrayList;

/**
 * Class representing a Hospital object.
 *
 * @author Allison Nakazawa & Anna Dingler
 * Created on 1/24/21
 */
public class Hospital {
    private String mName;
    private NedocsScore mNedocsScore;
    private ArrayList<HospitalType> mHospitalTypes;
    private boolean mHasDiversion;
    private ArrayList<String> mDiversions;
    private String mPhoneNumber;
    private String mAddress;
    private String mCounty;
    private String mRegion;
    private String mRegionalCoordinatingHospital;
    private double mDistance;
    private boolean mExpanded = false;
    private boolean mFavorite = false;

    //TODO: figure out naming conventions
    public Hospital(String name, NedocsScore nedocsScore, ArrayList<HospitalType> hospitalTypes, String phoneNumber,
                    String address, String county, String region, String regionalCoordinatingHospital,
                    double distance, boolean hasDiversion, ArrayList<String> diversions) {
        mName = name;
        mNedocsScore = nedocsScore;
        mHospitalTypes = hospitalTypes;
        mPhoneNumber = phoneNumber;
        mAddress = address;
        mCounty = county;
        mRegion = region;
        mRegionalCoordinatingHospital = regionalCoordinatingHospital;
        mDistance = distance;
        mHasDiversion = hasDiversion;
        mDiversions = diversions;
    }

    public String getName() {
        return mName;
    }

    public NedocsScore getmNedocsScore() {
        return mNedocsScore;
    }

    public double getmDistance() {
        return mDistance;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getCounty() {
        return mCounty;
    }

    public String getRegion() {
        return mRegion;
    }

    public String getRegionalCoordinatingHospital() {
        return mRegionalCoordinatingHospital;
    }

    public ArrayList<HospitalType> getHospitalTypes() {
        return mHospitalTypes;
    }

    public boolean hasDiversions() {
        return mHasDiversion;
    }

    public ArrayList<String> getDiversions() {
        return mDiversions;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }
}
