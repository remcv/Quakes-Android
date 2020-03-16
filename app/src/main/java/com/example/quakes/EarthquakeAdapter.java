package com.example.quakes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder>
{
    // ViewHolder class
    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder
    {
        // fields
        public ImageView magnitude;
        public TextView city;
        public TextView distance;
        public TextView date;

        // constructor
        public EarthquakeViewHolder(@NonNull View itemView)
        {
            super(itemView);
            city = itemView.findViewById(R.id.city_textView);
            magnitude = itemView.findViewById(R.id.magnitude_ImageView);
            distance = itemView.findViewById(R.id.distance_TextView);
            date = itemView.findViewById(R.id.date_TextView);
        }
    }

    // member variables
    private List<EarthquakeObservation> mQuakes;

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
        EarthquakeViewHolder vh = new EarthquakeViewHolder(quakeView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position)
    {
        // get the data model based on position
        EarthquakeObservation obs = mQuakes.get(position);

        // set item views based on your views and data model
        ImageView magnitude = holder.magnitude;
        magnitude.setImageResource(obs.getResMagnitude());

        TextView city = holder.city;
        city.setText(obs.getCity());

        TextView distance = holder.distance;
        distance.setText(String.valueOf(obs.getDistance()));

        TextView date = holder.date;
        date.setText(obs.getDate().toString());
    }
}
