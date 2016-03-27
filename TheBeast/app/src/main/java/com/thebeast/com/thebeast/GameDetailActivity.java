package com.thebeast.com.thebeast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class GameDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = this.getIntent();
        String sport = intent.getStringExtra("sport");
        String location = intent.getStringExtra("location");
        String additionalInfo = intent.getStringExtra("additional_info");
        int timeInMins = intent.getIntExtra("time", 0);
        String time = Utility.getTime(timeInMins);
        int teamSize = intent.getIntExtra("team_size", 0);

        TextView sportTextView = (TextView)findViewById(R.id.sportTextView);
        TextView locationTextView = (TextView)findViewById(R.id.locationTextView);
        TextView infoTextView = (TextView)findViewById(R.id.infoTextView);
        TextView timeTextView = (TextView)findViewById(R.id.timeTextView);
        TextView sizeTextView = (TextView)findViewById(R.id.sizeTextView);

        sportTextView.append(sport);
        locationTextView.append(location);
        infoTextView.append(additionalInfo);
        timeTextView.append(time);
        sizeTextView.append(String.valueOf(teamSize));
    }
}
