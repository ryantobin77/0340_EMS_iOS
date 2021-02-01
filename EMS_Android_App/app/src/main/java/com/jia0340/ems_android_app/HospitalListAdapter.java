package com.jia0340.ems_android_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jia0340.ems_android_app.models.Hospital;

import java.util.List;

/**
 * Custom adapter used to bind individual items in the recyclerView
 *
 * @author Anna Dingler
 * Created on 1/27/21
 */
class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.ViewHolder> {

    private List<Hospital> mHospitalList;

    /**
     * Constructor of the custom adapter
     *
     * @param hospitalList The dataset that the recyclerView to be populated with
     */
    public HospitalListAdapter(List<Hospital> hospitalList) {
        mHospitalList = hospitalList;
    }

    /**
     * Creates the view for a specific hospital and stores it within a viewHolder
     *
     * @param parent Parent view to the individual item
     * @param viewType
     * @return the viewholder that contains the item view for a specific hospital
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View hospitalView = layoutInflater.inflate(R.layout.temp_item, parent, false);

        ViewHolder holder = new ViewHolder(hospitalView);

        return holder;
    }

    /**
     * Binds each aspect of a hospital's data to its respective view in the viewHolder
     *
     * @param holder Holder that contains the elements for the speicifc hospital
     * @param position Position of the hospital in the list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Hospital hospital = mHospitalList.get(position);

        //TODO: assign the values for each view
        holder.name.setText(hospital.getName());

        //TODO: need the view to have some elements visible and some not (only make one item view)
        holder.test.setVisibility(hospital.isExpanded() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(view -> {

            boolean expanded = hospital.isExpanded();

            hospital.setExpanded(!expanded);

            notifyItemChanged(position);

        });
    }

    /**
     * Get the number of items in stored in recyclerView
     *
     * @return number of items in hospitalList
     */
    @Override
    public int getItemCount() {
        return mHospitalList.size();
    }

    /**
     * Custom ViewHolder that contains the individual views found in the item xml
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        //TODO: declare all views here
        public TextView name;
        public Button test;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //TODO: assign all views here
            name = itemView.findViewById(R.id.text_name);
            test = itemView.findViewById((R.id.button_test));
        }
    }

}
