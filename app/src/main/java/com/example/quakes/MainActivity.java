package com.example.quakes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // dummy data
        List<EarthquakeObservation> earthquakes = new ArrayList<>();
        earthquakes.add(new EarthquakeObservation("Sydney", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Melbourne", 150, 5.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Cape Town", 43, 6, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Haiti", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Dakar", 124, 4.5,LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Adelaide", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Nairobi", 27, 4.5,LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Santiago de Chile", 27, 4.5,LocalDate.of(2019, 5, 12),  R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Montevideo", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Moscow", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Moscow", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Moscow", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Moscow", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));
        earthquakes.add(new EarthquakeObservation("Moscow", 27, 4.5, LocalDate.of(2019, 5, 12), R.drawable.sydney));

        // RecyclerView
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        EarthquakeAdapter adapter = new EarthquakeAdapter(earthquakes);
        rv.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(itemDecoration);
    }
}
