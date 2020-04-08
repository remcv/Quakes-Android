package com.example.quakes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    // member variables
    private final static String stringURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2020-04-01&endtime=2020-04-07&minmagnitude=0.8&orderby=time&limit=25";
    private List<EarthquakeObservation> earthquakes = new ArrayList<>();
    private RecyclerView rv;
    private ProgressBar progressBar;
    private TextView progressText;
    private ConnectivityManager conMan;
    NetworkInfo netInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_circular);
        progressText = findViewById(R.id.progress_TextView);

        updateStatus(0);

        // RecyclerView
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(itemDecoration);

        // connection manager
        conMan = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        netInfo = conMan.getActiveNetworkInfo();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (netInfo != null && netInfo.isConnected())
        {
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    JSONObject obj = NetFun.urlToJson(stringURL);
                    earthquakes = NetFun.jsonToList(obj);

                    if (earthquakes != null)
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                putDataInAdapter();
                                updateStatus(1);
                            }
                        });
                    }
                    else
                    {
                        runOnUiThread(() -> updateStatus(3));
                    }
                }
            }).start();
        }
        else
        {
            updateStatus(2);
        }
    }

    private void putDataInAdapter()
    {
        EarthquakeAdapter adapter = new EarthquakeAdapter(earthquakes);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new EarthquakeAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                String url = earthquakes.get(position).getUrl();
                Uri webPage = Uri.parse(url);

                Intent goToBrowserIntent = new Intent();
                goToBrowserIntent.setAction(Intent.ACTION_VIEW);
                goToBrowserIntent.setData(webPage);
                startActivity(goToBrowserIntent);
            }
        });
    }

    private void updateStatus(int statusCode)
    {
        switch (statusCode)
        {
            case 0: // startup
                progressBar.setEnabled(true);
                progressBar.setVisibility(View.VISIBLE);
                progressText.setText("Updating");
                progressText.setVisibility(View.VISIBLE);
                break;
            case 1: // on successful data retrieved
                progressBar.setEnabled(false);
                progressBar.setVisibility(View.GONE);
                progressText.setText("Updated");
                progressText.setVisibility(View.VISIBLE);
                break;
            case 2: // not connected to the Internet
                progressBar.setEnabled(false);
                progressBar.setVisibility(View.GONE);
                progressText.setText("No internet connection");
                progressText.setVisibility(View.VISIBLE);
                break;
            default: // internet connection is active, but some networking error occurred
                progressBar.setEnabled(false);
                progressBar.setVisibility(View.GONE);
                progressText.setText("No data retrieved");
                progressText.setVisibility(View.VISIBLE);
                break;
        }

    }

}
