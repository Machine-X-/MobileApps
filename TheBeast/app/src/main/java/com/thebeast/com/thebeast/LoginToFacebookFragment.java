package com.thebeast.com.thebeast;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginToFacebookFragment extends Fragment {

    private ProfileTracker profileTracker;
    private LoginButton loginButton;
    private TextView username;
	   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.login_to_facebook_fragment, container, false);

        username = (TextView) view.findViewById(R.id.username);
        Profile profile = Profile.getCurrentProfile();
        if (profile != null && !profile.getFirstName().isEmpty()) {
            username.setText("Welcome " + profile.getFirstName() + " " + profile.getLastName() + "!");
        }

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(new String[]{"user_friends", "public_profile", "email", "user_birthday"}));

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
                if (currentProfile != null && !currentProfile.getFirstName().isEmpty()) {
                    username.setText("Welcome " + currentProfile.getFirstName() + " " + currentProfile.getLastName() + "!");
                } else {
                    username.setText("Please sign in for fun!");
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
