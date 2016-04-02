package com.thebeast.com.thebeast;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class UserProfileActivity extends Fragment {

    private TextView tvUsername;
    private TextView tvWins;
    private TextView tvLosses;
    private TextView tvPointsScored;
    private Firebase mRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.activity_user_profile_layout, container, false);

        tvUsername = (TextView) view.findViewById(R.id.username);
        tvWins = (TextView) view.findViewById(R.id.tv_player_wins);
        tvLosses = (TextView) view.findViewById(R.id.tv_player_losses);
        tvPointsScored = (TextView) view.findViewById(R.id.tv_player_points_scored);

        SharedPreferences prefs = getActivity().getSharedPreferences(Utility.prefsFile, Context.MODE_PRIVATE);
        final String userName = prefs.getString("userName", null);

        tvUsername.append(" " + userName + "!");

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/profiles");
        Query queryRef = mRef.orderByChild("name").equalTo(userName);

        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    com.thebeast.com.thebeast.Profile dbProfile = postSnapshot.getValue(com.thebeast.com.thebeast.Profile.class);
                    tvWins.append(String.valueOf(dbProfile.getWins()));
                    tvLosses.append(String.valueOf(dbProfile.getLosses()));
                    tvPointsScored.append(String.valueOf(dbProfile.getPointsScored()));
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
