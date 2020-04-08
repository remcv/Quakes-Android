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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    // member variables
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
        if (conMan != null)
        {
            netInfo = conMan.getActiveNetworkInfo();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (netInfo != null && netInfo.isConnected())
        {
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    JSONObject obj = NetFun.urlToJson(makeStartupString(3,2.9, 30));
                    earthquakes = NetFun.jsonToList(obj);

                    if (earthquakes != null)
                    {
                        runOnUiThread(new Runnable() {
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

        adapter.setOnItemClickListener(new EarthquakeAdapter.OnItemClickListener() {
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

    private String makeStartupString(long numberOfDays, double minMagnitude, int resultsLimit)
    {
        String startDate;
        String endDate;

        LocalDate today = LocalDate.now();

        startDate = today.minusDays(numberOfDays).toString();
        endDate = today.toString();

        return String.format("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s&minmagnitude=%f&orderby=time&limit=%d",
                startDate, endDate, minMagnitude, resultsLimit);
    }

}
