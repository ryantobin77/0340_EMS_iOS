package com.jia0340.ems_android_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jia0340.ems_android_app.models.Hospital;
import com.jia0340.ems_android_app.models.HospitalType;
import com.jia0340.ems_android_app.models.NedocsScore;

import java.util.ArrayList;
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

        holder.mHospitalName.setText(hospital.getName());
        holder.mDistanceLabel.setText(mContext.getString(R.string.distance, hospital.getmDistance()));
        holder.mPhoneNumber.setText(hospital.getPhoneNumber());
        //TODO: will need to format addresses for newlines etc.
        holder.mAddressView.setText(hospital.getAddress());
        holder.mCountyRegionText.setText(hospital.getCounty() + " - " +hospital.getRegion());
        holder.mRegionalCoordinatingText.setText(hospital.getRegionalCoordinatingHospital());

        handleNedocsValues(holder, hospital.getmNedocsScore());

        handleDiversions(holder, hospital.hasDiversions(), hospital.getDiversions());

        handleHospitalTypes(holder, hospital.getHospitalTypes());

        handleFavoritePin(holder, hospital);

        handleExpandCollapse(holder, hospital, position);
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

    public void handleNedocsValues(ViewHolder holder, NedocsScore score) {
        switch (score) {
            case NORMAL:
                holder.mNedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.normal, null));
                holder.mNedocsLabel.setText(mContext.getString(R.string.normal));
                break;
            case BUSY:
                holder.mNedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.busy, null));
                holder.mNedocsLabel.setText(mContext.getString(R.string.busy));
                break;
            case OVERCROWDED:
                holder.mNedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.overcrowded, null));
                holder.mNedocsLabel.setText(mContext.getString(R.string.overcrowded));
                holder.mNedocsLabel.setTextSize(12);
                break;
            case SEVERE:
                holder.mNedocsView.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.severe, null));
                holder.mNedocsLabel.setText(mContext.getString(R.string.severe));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + score);
        }
    }

    public void handleDiversions(ViewHolder holder, boolean hasDiversions, ArrayList<String> diversions) {

        if (hasDiversions) {
            if (diversions != null && diversions.size() > 0) {
                // set visibility of icon within collapsed/expanded views
                holder.mDiversionView.setVisibility(View.VISIBLE);
                holder.mExpandedDiversionView.setVisibility(View.VISIBLE);

                // assign text for expandedView
                String description = diversions.get(0);

                for (int i = 1; i < diversions.size(); i++) {
                    description += "\n";
                    description += diversions.get(i);
                }

                holder.mDiversionDescription.setText(description);
                holder.mDiversionDescription.setVisibility(View.VISIBLE);
            }
        } else {
            holder.mDiversionView.setVisibility(View.GONE);
            holder.mExpandedDiversionView.setVisibility(View.GONE);
            holder.mDiversionDescription.setVisibility(View.GONE);
        }

    }

    public void handleHospitalTypes(ViewHolder holder, ArrayList<HospitalType> hospitalTypes) {

        holder.mHospitalTypeOneImage.setVisibility(View.GONE);
        holder.mHospitalTypeTwoImage.setVisibility(View.GONE);
        holder.mHospitalTypeThreeImage.setVisibility(View.GONE);

        holder.mTypeOneView.setVisibility(View.GONE);
        holder.mTypeTwoView.setVisibility(View.GONE);
        holder.mTypeThreeView.setVisibility(View.GONE);

        if (hospitalTypes != null) {

            for (int i = 0; i < hospitalTypes.size(); i++) {

                ImageView currHospitalIcon = null;
                TextView currTypeView = null;

                switch (i) {
                    case 0:
                        currHospitalIcon = holder.mHospitalTypeOneImage;
                        currTypeView = holder.mTypeOneView;
                        break;
                    case 1:
                        currHospitalIcon = holder.mHospitalTypeTwoImage;
                        currTypeView = holder.mTypeTwoView;
                        break;
                    case 2:
                        currHospitalIcon = holder.mHospitalTypeThreeImage;
                        currTypeView = holder.mTypeThreeView;
                        break;
                }

                currHospitalIcon.setVisibility(View.VISIBLE);
                currTypeView.setVisibility(View.VISIBLE);

                Drawable currImage = null;
                String currText = "";

                switch (hospitalTypes.get(i)) {
                    case ADULT_TRAUMA_CENTER:
                        currImage = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.person, null);
                        currText = mContext.getString(R.string.adult_trauma_center);
                        break;
                    case BRAIN:
                        currImage = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.brain, null);
                        currText = mContext.getString(R.string.brain);
                        break;
                    case HEART:
                        currImage = ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.heart, null);
                        currText = mContext.getString(R.string.heart);
                        break;
                }

                currHospitalIcon.setImageDrawable(currImage);
                currTypeView.setCompoundDrawablesWithIntrinsicBounds(currImage, null, null, null);
                currTypeView.setText(currText);
            }
        }
    }

    public void handleFavoritePin(ViewHolder holder, Hospital hospital) {
        if (hospital.isFavorite())
            holder.mFavoriteView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.filled_favorite_pin, null));
        else
            holder.mFavoriteView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.outlined_favorite_pin, null));

        holder.mFavoriteView.setOnClickListener(view -> {

            boolean favorite = hospital.isFavorite();

            hospital.setFavorite(!favorite);

            if (hospital.isFavorite())
                holder.mFavoriteView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.filled_favorite_pin, null));
            else
                holder.mFavoriteView.setImageDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.outlined_favorite_pin, null));

        });
    }

    public void handleExpandCollapse(ViewHolder holder, Hospital hospital, int position) {
        holder.mExpandButton.setVisibility(hospital.isExpanded() ? View.GONE : View.VISIBLE);
        holder.mExpandedHospitalCard.setVisibility(hospital.isExpanded() ? View.VISIBLE : View.GONE);

        holder.mExpandButton.setOnClickListener(view -> {

            hospital.setExpanded(true);
            notifyItemChanged(position);

        });

        holder.mCollapseButton.setOnClickListener(view -> {

            hospital.setExpanded(false);
            notifyItemChanged(position);

        });
    }

    /**
     * Custom ViewHolder that contains the individual views found in the item xml
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout mNedocsView;
        public TextView mNedocsLabel;
        public TextView mHospitalName;
        public ImageButton mFavoriteView;
        public TextView mDistanceLabel;
        public ImageView mDiversionView;
        public ImageView mHospitalTypeOneImage;
        public ImageView mHospitalTypeTwoImage;
        public ImageView mHospitalTypeThreeImage;
        public ImageButton mExpandButton;

        public ConstraintLayout mExpandedHospitalCard;
        public TextView mPhoneNumber;
        public TextView mAddressView;
        public ImageView mExpandedDiversionView;
        public TextView mDiversionDescription;
        public TextView mTypeOneView;
        public TextView mTypeTwoView;
        public TextView mTypeThreeView;
        public TextView mCountyRegionText;
        public TextView mRegionalCoordinatingText;
        public ImageButton mCollapseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mNedocsView = itemView.findViewById(R.id.nedocsView);
            mNedocsLabel = itemView.findViewById(R.id.nedocsLabel);
            mHospitalName = itemView.findViewById(R.id.hospitalName);
            mFavoriteView = itemView.findViewById(R.id.favoriteView);
            mDistanceLabel = itemView.findViewById(R.id.distanceLabel);
            mDiversionView = itemView.findViewById(R.id.diversionView);
            mHospitalTypeOneImage = itemView.findViewById(R.id.hospitalType1View);
            mHospitalTypeTwoImage = itemView.findViewById(R.id.hospitalType2View);
            mHospitalTypeThreeImage = itemView.findViewById(R.id.hospitalType3View);
            mExpandButton = itemView.findViewById(R.id.expandButton);

            mExpandedHospitalCard = itemView.findViewById(R.id.expandedHospitalCard);
            mPhoneNumber = itemView.findViewById(R.id.phoneNumberView);
            mAddressView = itemView.findViewById(R.id.addressView);
            mExpandedDiversionView = itemView.findViewById(R.id.expandedDiversionView);
            mDiversionDescription = itemView.findViewById(R.id.diversionDescription);;
            mTypeOneView = itemView.findViewById(R.id.hospitalType1Description);
            mTypeTwoView = itemView.findViewById(R.id.hospitalType2Description);
            mTypeThreeView = itemView.findViewById(R.id.hospitalType3Description);
            mCountyRegionText = itemView.findViewById(R.id.countyRegionView);
            mRegionalCoordinatingText = itemView.findViewById(R.id.regionalCoordinatingHospitalView);
            mCollapseButton = itemView.findViewById(R.id.collapseButton);
        }
    }

}
