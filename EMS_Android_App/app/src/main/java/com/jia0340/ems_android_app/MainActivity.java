package com.jia0340.ems_android_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jia0340.ems_android_app.models.Hospital;
import com.jia0340.ems_android_app.network.DataService;
import com.jia0340.ems_android_app.network.DistanceService;
import com.jia0340.ems_android_app.network.RetrofitClientInstance;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity that corresponds to the activity_main.xml file
 * This is the first activity displayed when the app is launched
 *
 * @author Anna Dingler
 * Created on 1/24/21
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Hospital> mHospitalList;
    private HospitalListAdapter mHospitalAdapter;
    private Toolbar mToolbar;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private boolean mGettingDistance;

    /**
     * Create method for application
     * Called when the app is started
     * Used to set the content view, get initial hospital data, and setup recyclerView
     * @param savedInstanceState saved instance of app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attaching the layout to the toolbar object
        mToolbar = findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(mToolbar);

        getHospitalData();

        //TODO: need to prompt user to grant location access
    }

//    @Override
//    protected void onDestroy() {
//
//        super.onDestroy();
//
//        // stopping the service
//        stopService(new Intent( this, DistanceService.class ) );
//    }

    /**
     * Instantiates the menu at the top of the screen
     * Includes call, sort, filter and search buttons
     * @param menu Menu that we want our layout set to
     * @return Return true if menu was created successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Called when one of the buttons in the menu is called
     * @param item The item that has been selected
     * @return Return true is logic is completed successfully
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO: logic for menu

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets the hospital data from the database and assigns it to mHospitalList
     * If response was retrieved correctly, set up the recyclerView and populate with data
     */
    public void getHospitalData() {

        /*Create handle for the RetrofitInstance interface*/
        DataService service = RetrofitClientInstance.getRetrofitInstance().create(DataService.class);
        Call<List<Hospital>> call = service.getHospitals();
        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                // Save the returned list
                mHospitalList = (ArrayList<Hospital>) response.body();
                // Now we can setup the recyclerView
                instantiateRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                // Failed to collect hospital data
                // TODO: what do we want to happen when it fails?
                Log.d("MainActiity", t.getMessage());
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Set up the recyclerView and adapter
     */
    private void instantiateRecyclerView() {

        RecyclerView hospitalRecyclerView = findViewById(R.id.hospital_list);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        hospitalRecyclerView.addItemDecoration(itemDecoration);

        mHospitalAdapter = new HospitalListAdapter(mHospitalList, this);
        hospitalRecyclerView.setAdapter(mHospitalAdapter);
        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread(new Runnable() {
            public void run() {
                getCurrentLocation();
            }
        }).start();

//        // starting the service
//        startService(new Intent( this, DistanceService.class ) );
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
                                mGettingDistance = true;
                                calculateDistances();
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

        mGettingDistance = false;
        mHospitalAdapter.notifyDataSetChanged();
    }

    private String convertToMiles(float distanceInMeters) {

        DecimalFormat df = new DecimalFormat("0.00");

        return df.format(distanceInMeters * 0.000621371);

    }


//    // starting the service
//    List<Double> latitudes = null;
//    List<Double> longitudes = null;
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//        latitudes = mHospitalList.stream().map(Hospital::getLatitude).collect(Collectors.toList());
//        longitudes = mHospitalList.stream().map(Hospital::getLongitude).collect(Collectors.toList());
//    } else {
//        for (Hospital hospital : mHospitalList) {
//            latitudes.add(hospital.getLatitude());
//            longitudes.add(hospital.getLongitude());
//        }
//    }
//    Intent intent = new Intent(getBaseContext(), DistanceService.class);
//        intent.putExtra("HOSPITAL_LAT", (Parcelable) latitudes);
//        intent.putExtra("HOSPITAL_LONG", (Parcelable) longitudes);
//    startService(new Intent( this, DistanceService.class ) );
}