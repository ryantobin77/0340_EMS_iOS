package com.jia0340.ems_android_app.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum for Hospital Type values.
 *
 * @author Allison Nakazawa & Anna Dingler
 * Created on 1/24/21
 */
public enum HospitalType {
    @JsonProperty("Specialty Center 1")
    ADULT_TRAUMA_CENTER,
    @JsonProperty("heart")
    HEART,
    @JsonProperty("brain")
    BRAIN
}
