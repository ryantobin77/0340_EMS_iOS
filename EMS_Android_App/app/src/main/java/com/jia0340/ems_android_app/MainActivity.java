package com.jia0340.ems_android_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView hospitalRecyclerView = (RecyclerView) findViewById(R.id.hospital_list);

        // Attaching the layout to the toolbar object
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(mToolbar);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        hospitalRecyclerView.addItemDecoration(itemDecoration);

        mHospitalList = new ArrayList<>();

        mHospitalList.add(new Hospital("this is a fun hospital", NedocsScore.NORMAL, HospitalType.ADULT_TRAUMA_CENTER, 1.98, false));
        mHospitalList.add(new Hospital("this is a lame hospital", NedocsScore.OVERCROWDED, HospitalType.ADULT_TRAUMA_CENTER, 2.8, false));
        mHospitalList.add(new Hospital("this hospital has a really freaking long hospital name for no apparent reason what the heck", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 21.76, false));
        mHospitalList.add(new Hospital("lol short", NedocsScore.SEVERE, HospitalType.ADULT_TRAUMA_CENTER, 1.9, false));
        mHospitalList.add(new Hospital("short", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 1.2, false));
        mHospitalList.add(new Hospital("hello world!!!!", NedocsScore.OVERCROWDED, HospitalType.ADULT_TRAUMA_CENTER, 4.5, false));
        mHospitalList.add(new Hospital("Grady hospital", NedocsScore.NORMAL, HospitalType.ADULT_TRAUMA_CENTER, 3.9, false));
        mHospitalList.add(new Hospital("Hospital", NedocsScore.SEVERE, HospitalType.ADULT_TRAUMA_CENTER, 28.4, false));
        mHospitalList.add(new Hospital("I need to go to THIS hospital", NedocsScore.BUSY, HospitalType.ADULT_TRAUMA_CENTER, 81.4, false));
        mHospitalList.add(new Hospital("Just kidding", NedocsScore.NORMAL, HospitalType.ADULT_TRAUMA_CENTER, 61, false));

        mHospitalAdapter = new HospitalListAdapter(mHospitalList, this);
        hospitalRecyclerView.setAdapter(mHospitalAdapter);
        hospitalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}