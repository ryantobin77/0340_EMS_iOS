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
    private double distance;
    private boolean hasDiversion;

    public Hospital(String name, NedocsScore nedocsScore, HospitalType hospitalType, double distance,
                    boolean hasDiversion) {
        this.name = name;
        this.nedocsScore = nedocsScore;
        this.hospitalType = hospitalType;
        this.distance = distance;
        this.hasDiversion = hasDiversion;
    }
}
