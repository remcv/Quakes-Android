package com.example.quakes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder>
{
    // interface for clicking an item
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    // ViewHolder class
    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder
    {
        // fields
        public TextView magnitude;
        public TextView place;
        public TextView country;
        public TextView date;

        // constructor
        public EarthquakeViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            place = itemView.findViewById(R.id.place_TextView);
            magnitude = itemView.findViewById(R.id.magnitude_TextView);
            country = itemView.findViewById(R.id.country_textView);
            date = itemView.findViewById(R.id.date_TextView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    // member variables
    private List<EarthquakeObservation> mQuakes;
    private OnItemClickListener mListener;

    // constructor for adapter
    public EarthquakeAdapter(List<EarthquakeObservation> quakes)
    {
        mQuakes = quakes;
    }

    // methods
    @Override
    public int getItemCount()
    {
        return mQuakes.size();
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View quakeView = inflater.inflate(R.layout.list_item_layout, parent, false);

        // return a new holder instance
        EarthquakeViewHolder vh = new EarthquakeViewHolder(quakeView, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position)
    {
        // get the data model based on position
        EarthquakeObservation obs = mQuakes.get(position);

        String abovePlaceString;
        String placeString;

        if (obs.getPlace().split(" of ").length > 1)
        {
            placeString = obs.getPlace().split(" of ")[1].trim();
            abovePlaceString = obs.getPlace().split(" of ")[0].trim() + " of";
        }
        else
        {
            abovePlaceString = "Near";
            placeString = obs.getPlace();
        }

        // set item views based on your views and data model
        TextView magnitude = holder.magnitude;
        magnitude.setText(new DecimalFormat(".0").format(obs.getMagnitude()));

        if (obs.getMagnitude() < 2) magnitude.setBackgroundResource(R.drawable.circle1_02);
        else if (obs.getMagnitude() < 3) magnitude.setBackgroundResource(R.drawable.circle2_23);
        else if (obs.getMagnitude() < 4) magnitude.setBackgroundResource(R.drawable.circle3_34);
        else if (obs.getMagnitude() < 5) magnitude.setBackgroundResource(R.drawable.circle4_45);
        else if (obs.getMagnitude() < 6) magnitude.setBackgroundResource(R.drawable.circle5_56);
        else if (obs.getMagnitude() < 7) magnitude.setBackgroundResource(R.drawable.circle6_67);
        else if (obs.getMagnitude() < 8) magnitude.setBackgroundResource(R.drawable.circle7_78);
        else if (obs.getMagnitude() < 9) magnitude.setBackgroundResource(R.drawable.circle8_89);
        else if (obs.getMagnitude() < 10) magnitude.setBackgroundResource(R.drawable.circle9_910);
        else magnitude.setBackgroundResource(R.drawable.circle10_10plus);

        TextView place_TV = holder.place;
        place_TV.setText(abovePlaceString);

        TextView country_TV = holder.country;
        country_TV.setText(placeString);

        // date
        LocalDateTime dateObj = obs.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy\nHH:mm");
        String dateString = dateObj.format(formatter);
        TextView date = holder.date;
        date.setText(dateString);
    }

    // method to set the custom OnItemClickListener
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }
}
