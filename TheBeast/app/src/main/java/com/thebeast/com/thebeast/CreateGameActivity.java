package com.thebeast.com.thebeast;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class CreateGameActivity extends AppCompatActivity {

    static TextView timeText;
    private TextView errorText;
    private EditText mLocation;
    private static int timeInMins;
    Firebase mRef;
    private Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");

        final EditText infoText = (EditText)findViewById(R.id.additional_info);
        final EditText teamSizeNum = (EditText)findViewById(R.id.team_size);
        final Spinner sportSpinner = (Spinner)findViewById(R.id.sport_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sport_entries,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(adapter);

        timeText =(TextView)findViewById(R.id.time_text);
        errorText = (TextView) findViewById(R.id.error_text);
        errorText.setVisibility(View.GONE);
        mLocation = (EditText) findViewById(R.id.location);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String defaultTime = Utility.getTime(hour, minute);
        timeText.setText(defaultTime);

        mGeocoder = new Geocoder(this);

        Button createButton = (Button)findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sport = sportSpinner.getSelectedItem().toString();
                String location = mLocation.getText().toString();
                String additionalInfo = infoText.getText().toString();
                int teamSize = Integer.valueOf(teamSizeNum.getText().toString());

                Game newGame = new Game(additionalInfo, location, sport, teamSize, timeInMins);
                mRef.push().setValue(newGame);
                try {
                    List<Address> returnedAddresses = mGeocoder.getFromLocationName(newGame.getLocation(), 5);
                    if (returnedAddresses != null && returnedAddresses.size() > 0) {
                        errorText.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("SHTUFF", "INVALID ADDRESS ENTERED");
                        errorText.setVisibility(View.VISIBLE);
                    }
                } catch (IOException e) {
                    Log.e("SHTUFF", "ERROR OCCURRED WHILE SEARCHING FOR ADDRESS :: ERROR MESSAGE = " + e.getMessage());
                }
            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static void setTime(int hour, int minute) {
        timeInMins = Utility.getMins(hour, minute);
        String newTime = Utility.getTime(hour, minute);
        timeText.setText(newTime);
    }



}
