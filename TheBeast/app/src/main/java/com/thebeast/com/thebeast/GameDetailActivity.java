package com.thebeast.com.thebeast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Intent intent = this.getIntent();
        String sport = intent.getStringExtra("sport");
        String location = intent.getStringExtra("location");
        String additionalInfo = intent.getStringExtra("additional_info");
        int time = intent.getIntExtra("time", 0);
        int teamSize = intent.getIntExtra("team_size", 0);

        TextView sportTextView = (TextView)findViewById(R.id.sportTextView);
        TextView locationTextView = (TextView)findViewById(R.id.locationTextView);
        TextView infoTextView = (TextView)findViewById(R.id.infoTextView);
        TextView timeTextView = (TextView)findViewById(R.id.timeTextView);
        TextView sizeTextView = (TextView)findViewById(R.id.sizeTextView);

        sportTextView.setText(sport);
        locationTextView.setText(location);
        infoTextView.setText(additionalInfo);
        timeTextView.setText(String.valueOf(time));
        sizeTextView.setText(String.valueOf(teamSize));
    }
}
