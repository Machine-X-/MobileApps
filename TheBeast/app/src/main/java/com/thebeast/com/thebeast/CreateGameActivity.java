package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class CreateGameActivity extends AppCompatActivity {

    static TextView timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Spinner sportSpinner = (Spinner)findViewById(R.id.sport_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sport_entries,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(adapter);

        timeText =(TextView)findViewById(R.id.timeText);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String defaultTime = formatTime(hour, minute);
        timeText.setText(defaultTime);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static void setTime(int hour, int minute) {
        String newTime = formatTime(hour, minute);
        timeText.setText(newTime);
    }

    private static String formatTime(int hour, int minute) {
        String minString = String.valueOf(minute);
        if (hour - 12 > 0) {
            hour = hour - 12;
        }
        if (minute < 10) {
            minString = "0" + minString;
        }
        return hour + ":" + minString;
    }

}
