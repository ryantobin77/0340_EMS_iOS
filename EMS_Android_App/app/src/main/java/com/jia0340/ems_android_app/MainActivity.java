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

        //TODO: remove dummy data

        ArrayList<HospitalType> gradyTypes = new ArrayList<>();
        gradyTypes.add(HospitalType.ADULT_TRAUMA_CENTER);
        gradyTypes.add(HospitalType.BRAIN);

        ArrayList<String> gradyDiversions = new ArrayList<>();
        gradyDiversions.add("Medical Diversion");
        gradyDiversions.add("Psych Diversion");

        mHospitalList = new ArrayList<>();

        mHospitalList.add(new Hospital("Grady Health System", NedocsScore.SEVERE,
                gradyTypes, "(404) 616-6440", "80 Jesse Hill Jr Dr SE, Atlanta, GA 30303",
                "Fulton", "EMS Region 3", "Regional Coordinating Hospital D", 1.98,
                true, gradyDiversions));

        ArrayList<String> dodgeDiversions = new ArrayList<>();
        dodgeDiversions.add("ICU/CCU Diversion");

        mHospitalList.add(new Hospital("Dodge County Hospital", NedocsScore.NORMAL, null,
                "(478) 448-4000", "901 Griffin Ave, Eastman, GA 31023",
                "Dodge", "EMS Region 5", "Regional Coordinating Hospital H", 2.8, true, dodgeDiversions));

        ArrayList<HospitalType> redmondTypes = new ArrayList<>();
        redmondTypes.add(HospitalType.HEART);

        mHospitalList.add(new Hospital("Redmond Regional Medical Ctr",
                NedocsScore.SEVERE, redmondTypes, "(706) 291-0291",
                "501 Redmond Rd, Rome, GA 30165", "Floyd",
                "EMS Region 1", "Regional Coordinating Hospital C",
                21.76, false, null));

        ArrayList<HospitalType> upsonTypes = new ArrayList<>();
        upsonTypes.add(HospitalType.BRAIN);
        upsonTypes.add(HospitalType.HEART);

        mHospitalList.add(new Hospital("Upson Regional Medical Center", NedocsScore.BUSY, upsonTypes,
                "(706) 647-8111", "801 W Gordon St, Thomaston, GA 30286", "Upson", "EMS Region 4",
                "Regional Coordinating Hospital F",1.9, false, null));

        ArrayList<HospitalType> piedmontTypes = new ArrayList<>();
        piedmontTypes.add(HospitalType.BRAIN);
        piedmontTypes.add(HospitalType.HEART);
        piedmontTypes.add(HospitalType.ADULT_TRAUMA_CENTER);

        ArrayList<String> piedmontDiversions = new ArrayList<>();
        piedmontDiversions.add("ER Saturation");
        piedmontDiversions.add("ICU/CCU Diversion");
        piedmontDiversions.add("Psych Diversion");

        mHospitalList.add(new Hospital("Piedmont - Atlanta Hospital", NedocsScore.OVERCROWDED, piedmontTypes,
                "(404) 605-5000", "1968 Peachtree Rd NW, Atlanta, GA 30309", "Fulton",
                "EMS Region 3", "Regional Coordinating Hospital D",
                1.2, true, piedmontDiversions));

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

    //TODO: logic for menu
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