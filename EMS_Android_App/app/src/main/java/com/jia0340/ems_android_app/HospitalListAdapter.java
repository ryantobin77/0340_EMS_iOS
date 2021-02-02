package com.jia0340.ems_android_app;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jia0340.ems_android_app.models.Hospital;
import com.jia0340.ems_android_app.models.NedocsScore;

import java.util.List;

/**
 * Custom adapter used to bind individual items in the recyclerView
 *
 * @author Anna Dingler
 * Created on 1/27/21
 */
class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.ViewHolder> {

    private List<Hospital> mHospitalList;
    private Context mContext;

    /**
     * Constructor of the custom adapter
     *
     * @param hospitalList The dataset that the recyclerView to be populated with
     */
    public HospitalListAdapter(List<Hospital> hospitalList, Context context) {
        mHospitalList = hospitalList;
        mContext = context;
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

        View hospitalView = layoutInflater.inflate(R.layout.hospital_card, parent, false);

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
        holder._hospitalName.setText(hospital.getName());

        //TODO: reformat the string here
        holder._distanceLabel.setText(hospital.getDistance() + "mi");

        initializeNedocsValues(holder, hospital.getNedocsScore());

        //TODO: need the view to have some elements visible and some not (only make one item view)
        holder._expandedHospitalCard.setVisibility(hospital.isExpanded() ? View.VISIBLE : View.GONE);

        if (hospital.isFavorite())
            holder._favoriteView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.filled_favorite_pin, null));
        else
            holder._favoriteView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.outlined_favorite_pin, null));

        holder._favoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean favorite = hospital.isFavorite();

                hospital.setFavorite(!favorite);

                notifyItemChanged(position);
            }
        });

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

    //TODO: add javadocs and use string resources
    public void initializeNedocsValues(ViewHolder holder, NedocsScore score) {
        switch (score) {
            case NORMAL:
                holder._nedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.normal, null));
                holder._nedocsLabel.setText("Normal");
                break;
            case BUSY:
                holder._nedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.busy, null));
                holder._nedocsLabel.setText("Busy");
                break;
            case OVERCROWDED:
                holder._nedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.overcrowded, null));
                holder._nedocsLabel.setText("Overcorwded");
                break;
            case SEVERE:
                holder._nedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.severe, null));
                holder._nedocsLabel.setText("Severe");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + score);
        }
    }

    /**
     * Custom ViewHolder that contains the individual views found in the item xml
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        //TODO: declare all views here
        public ConstraintLayout _nedocsView;
        public TextView _nedocsLabel;
        public TextView _hospitalName;
        public ImageButton _favoriteView;
        public TextView _distanceLabel;
        public LinearLayout _expandedHospitalCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //TODO: assign all views here
            _nedocsView = itemView.findViewById(R.id.nedocsView);
            _nedocsLabel = itemView.findViewById(R.id.nedocsLabel);
            _hospitalName = itemView.findViewById(R.id.hospitalName);
            _favoriteView = itemView.findViewById(R.id.favoriteView);
            _distanceLabel = itemView.findViewById(R.id.distanceLabel);
            _expandedHospitalCard = itemView.findViewById(R.id.expandedHospitalCard);
        }
    }

}
