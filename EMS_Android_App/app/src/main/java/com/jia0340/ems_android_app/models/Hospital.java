package com.jia0340.ems_android_app.models;

/**
 * Class representing a Hospital object.
 *
 * @author Allison Nakazawa & Anna Dingler
 * Created on 1/24/21
 */
public class Hospital {
    private String name;
    private NedocsScore nedocsScore;
    private HospitalType hospitalType;
    private float distance;
    private boolean hasDiversion;
    private boolean mExpanded = false;

    public Hospital(String name, NedocsScore nedocsScore, HospitalType hospitalType, float distance,
                    boolean hasDiversion) {
        this.name = name;
        this.nedocsScore = nedocsScore;
        this.hospitalType = hospitalType;
        this.distance = distance;
        this.hasDiversion = hasDiversion;
    }

    public String getName() {
        return name;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }
}
