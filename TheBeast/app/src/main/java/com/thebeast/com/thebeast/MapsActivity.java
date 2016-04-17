package com.thebeast.com.thebeast;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Firebase mRef;
    private Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mGeocoder = new Geocoder(this);

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Game game = postSnapshot.getValue(Game.class);
                    try {
                        List<Address> address = mGeocoder.getFromLocationName(game.getLocation(), 5);
                        if (address != null && address.size() > 0) {
                            Address location = address.get(0);
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(latLng).title(postSnapshot.getKey())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        }
                    } catch (IOException e) {
                        Log.e("SHTUFF", "ERROR OCCURRED WHILE SEARCHING FOR ADDRESS :: ERROR MESSAGE = " + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
            }
        });

        mMap.setMyLocationEnabled(true);
        Location myLocation = mMap.getMyLocation();
        LatLng myLatLng = new LatLng(40.002606, -83.015257);
        if (myLocation != null) {
            myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        }
        CameraPosition myPosition = new CameraPosition.Builder()
                .target(myLatLng).zoom(15).tilt(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 400, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        boolean returnStatus = true;

        final String title = marker.getTitle();

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (title.toUpperCase().equals(postSnapshot.getKey().toUpperCase())) {
                        Game game = postSnapshot.getValue(Game.class);
                        Intent intent = new Intent(MapsActivity.this, GameDetailActivity.class);
                        intent.putExtra("sport", game.getSport());
                        intent.putExtra("location", game.getLocation());
                        intent.putExtra("additional_info", game.getAdditionalInfo());
                        intent.putExtra("time", game.getTime());
                        intent.putExtra("team_size", game.getTeamSize());
                        MapsActivity.this.startActivity(intent);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
            }
        });


        return returnStatus;
    }

    @Override
    public void onLocationChanged(Location location) {
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        CameraPosition myPosition = new CameraPosition.Builder()
//                .target(latLng).zoom(15).tilt(15).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        }
//        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

}