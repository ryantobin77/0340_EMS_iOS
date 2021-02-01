package com.jia0340.ems_android_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.jia0340.ems_android_app.models.Hospital;
import com.jia0340.ems_android_app.models.HospitalType;
import com.jia0340.ems_android_app.models.NedocsScore;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView hospitalRecyclerView = (RecyclerView) findViewById(R.id.hospital_list);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        hospitalRecyclerView.addItemDecoration(itemDecoration);

        mHospitalList = new ArrayList<>();

        mHospitalList.add(new Hospital("A", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("B", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("C", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("D", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("E", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("F", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("G", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("H", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("I", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));
        mHospitalList.add(new Hospital("J", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2f, false));

        mHospitalAdapter = new HospitalListAdapter(mHospitalList);
        hospitalRecyclerView.setAdapter(mHospitalAdapter);
        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}