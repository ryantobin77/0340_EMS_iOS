package com.jia0340.ems_android_app.network;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jia0340.ems_android_app.models.Hospital;

import java.text.DecimalFormat;
import java.util.List;

public class DistanceService extends Service {

    Location mCurrentLocation = null;
    FusedLocationProviderClient mFusedLocationClient;
    boolean mCalculating = false;
    List<Hospital> mHospitalList;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getCurrentLocation();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getCurrentLocation() {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener((Activity) getApplicationContext(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d("DistanceService: ", "Successfully got location");
                            // Got last known location. In some rare situations this can be null.
                            if (location != null && !location.equals(mCurrentLocation)) {
                                mCurrentLocation = location;
                            }
                        }
                    });
        } else {
            Log.d("Main Activity", "Permissions were not granted!");
            //TODO: location permissions not granted, what do we want to do?
        }
    }

    //TODO: need to do this in another thread/service
    private void calculateDistances() {

        for (int i = 0; i < mHospitalList.size(); i++) {

            Hospital hospital = mHospitalList.get(i);

            float[] distance = new float[3];

            Location.distanceBetween(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), hospital.getLatitude(), hospital.getLongitude(), distance);

            //TODO: there might be an issue here with distance[0] not having a value??
            hospital.setDistance(convertToMiles(distance[0]));
        }
    }

    private String convertToMiles(float distanceInMeters) {

        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(distanceInMeters * 0.000621371);

    }
}
