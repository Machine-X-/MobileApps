package com.thebeast.com.thebeast;


import android.content.Intent;
import android.location.Address;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginToFacebookFragment extends Fragment {

    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private TextView tvHeader;
    private TextView tvUsername;
    private TextView tvWins;
    private TextView tvLosses;
    private TextView tvPointsScored;
    private Firebase mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.login_to_facebook_fragment, container, false);

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/profiles");
        tvHeader = (TextView) view.findViewById(R.id.tv_facebook_signon_header);
        tvUsername = (TextView) view.findViewById(R.id.username);
        tvWins = (TextView) view.findViewById(R.id.tv_player_wins);
        tvLosses = (TextView) view.findViewById(R.id.tv_player_losses);
        tvPointsScored = (TextView) view.findViewById(R.id.tv_player_points_scored);
        final Profile profile = Profile.getCurrentProfile();
        if (profile != null && !profile.getFirstName().isEmpty()) {
            tvUsername.setText("Welcome " + profile.getFirstName() + " " + profile.getLastName() + "!");
            tvHeader.setVisibility(View.GONE);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                    boolean found = false;
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        com.thebeast.com.thebeast.Profile dbProfile = postSnapshot.getValue(com.thebeast.com.thebeast.Profile.class);
                        if (dbProfile.getName().equals(profile.getName())) {
                            tvWins.append(String.valueOf(dbProfile.getWins()));
                            tvLosses.append(String.valueOf(dbProfile.getLosses()));
                            tvPointsScored.append(String.valueOf(dbProfile.getPointsScored()));
                            found = true;
                        }
                    }
                    if (!found) {
                        com.thebeast.com.thebeast.Profile newPlayer = new com.thebeast.com.thebeast.Profile(profile.getName(), 0, 0, 0);
                        mRef.push().setValue(newPlayer);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
                }
            });
        } else {
            tvWins.setVisibility(View.GONE);
            tvLosses.setVisibility(View.GONE);
            tvPointsScored.setVisibility(View.GONE);
        }

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(new String[]{"user_friends", "public_profile", "email", "user_birthday"}));

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    final Profile currentProfile) {
                // App code
                if (currentProfile != null && !currentProfile.getFirstName().isEmpty()) {
                    tvUsername.setText("Welcome " + currentProfile.getFirstName() + " " + currentProfile.getLastName() + "!");
                    tvWins.setVisibility(View.VISIBLE);
                    tvLosses.setVisibility(View.VISIBLE);
                    tvPointsScored.setVisibility(View.VISIBLE);
                    tvHeader.setVisibility(View.GONE);

                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                            boolean found = false;
                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                com.thebeast.com.thebeast.Profile dbProfile = postSnapshot.getValue(com.thebeast.com.thebeast.Profile.class);
                                if (dbProfile.getName().equals(profile.getName())) {
                                    tvWins.append(String.valueOf(dbProfile.getWins()));
                                    tvLosses.append(String.valueOf(dbProfile.getLosses()));
                                    tvPointsScored.append(String.valueOf(dbProfile.getPointsScored()));
                                    found = true;
                                }
                            }
                            if (!found) {
                                com.thebeast.com.thebeast.Profile newPlayer = new com.thebeast.com.thebeast.Profile(profile.getName(), 0, 0, 0);
                                mRef.push().setValue(newPlayer);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
                        }
                    });
                } else {
                    tvUsername.setText("Please sign in to proceed");
                    tvWins.setText("Wins: ");
                    tvLosses.setText("Losses: ");
                    tvPointsScored.setText("Points Scored: ");
                    tvWins.setVisibility(View.GONE);
                    tvLosses.setVisibility(View.GONE);
                    tvPointsScored.setVisibility(View.GONE);
                    tvHeader.setVisibility(View.VISIBLE);
                }
            }
        };

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
