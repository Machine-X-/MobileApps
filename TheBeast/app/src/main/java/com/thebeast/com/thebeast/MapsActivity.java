package com.thebeast.com.thebeast;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /////----------------------------------Zooming camera to position user-----------------

        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);

        Log.d("LOCATION_SERVICE", "Showing current location");
        Location myLocation = mMap.getMyLocation();

        LatLng myLatLng = new LatLng(myLocation.getLatitude(),
                myLocation.getLongitude());

        CameraPosition myPosition = new CameraPosition.Builder()
                .target(myLatLng).zoom(17).bearing(90).tilt(30).build();
        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(myPosition));

        /////----------------------------------Zooming camera to position user-----------------
    }

}