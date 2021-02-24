package com.jia0340.ems_android_app.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import com.jia0340.ems_android_app.R;

/**
 * Enum for Hospital Type values.
 *
 * @author Allison Nakazawa & Anna Dingler
 * Created on 1/24/21
 */
public enum HospitalType {

    ADULT_TRAUMA_CENTER_LEVEL_I(R.drawable.person_1, R.string.adult_trauma_center_1),
    ADULT_TRAUMA_CENTER_LEVEL_II(R.drawable.person_2, R.string.adult_trauma_center_2),
    ADULT_TRAUMA_CENTER_LEVEL_III(R.drawable.person_3, R.string.adult_trauma_center_3),
    ADULT_TRAUMA_CENTER_LEVEL_IV(R.drawable.person_4, R.string.adult_trauma_center_4),
    PEDIATRIC_TRAUMA_CENTER_LEVEL_I(R.drawable.child_1, R.string.pediatric_trauma_center_1),
    PEDIATRIC_TRAUMA_CENTER_LEVEL_II(R.drawable.child_2, R.string.pediatric_trauma_center_2),
    COMPREHENSIVE_STROKE_CENTER(R.drawable.brain_comprehensive, R.string.comprehensive_stroke_center),
    THROMBECTOMY_CAPABLE_STROKE_CENTER(R.drawable.brain_thrombectomy, R.string.thrombectomy_stroke_center),
    PRIMARY_STROKE_CENTER(R.drawable.brain, R.string.primary_stroke_center),
    REMOTE_TREATMENT_STROKE_CENTER(R.drawable.brain_remote, R.string.remote_treatment_stroke_center),
    EMERGENCY_CARDIAC_CARE_CENTER_LEVEL_I(R.drawable.heart_1, R.string.eccc_1),
    EMERGENCY_CARDIAC_CARE_CENTER_LEVEL_II(R.drawable.heart_2, R.string.eccc_2),
    EMERGENCY_CARDIAC_CARE_CENTER_LEVEL_III(R.drawable.heart_3, R.string.eccc_3),
    NEONATAL_CENTER_LEVEL_I(R.drawable.baby_1, R.string.neonatal_center_1),
    NEONATAL_CENTER_LEVEL_II(R.drawable.baby_2, R.string.neonatal_center_2),
    NEONATAL_CENTER_LEVEL_III(R.drawable.baby_3, R.string.neonatal_center_3),
    MATERNAL_CENTER_LEVEL_I(R.drawable.pregnant_1, R.string.maternal_center_1),
    MATERNAL_CENTER_LEVEL_II(R.drawable.pregnant_2, R.string.maternal_center_2),
    MATERNAL_CENTER_LEVEL_III(R.drawable.pregnant_3, R.string.maternal_center_3);

    private int imageId;
    private int stringId;
    HospitalType(int imageId, int stringId) {
        this.imageId = imageId;
        this.stringId = stringId;
    }

    public int getImageId() {
        return imageId;
    }

    public int getStringId() {
        return stringId;
    }
}
