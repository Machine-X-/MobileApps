package com.thebeast.com.thebeast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

/**
 * Created by loganpatino on 4/7/16.
 */
public class SportStatFragment extends Fragment {

    private Utility.SportFilter filter;
    private ImageView mSportIcon;
    private TextView mGamesPlayed;
    private TextView mRecord;
    private TextView mSpecificStat1;
    private TextView mSpecificStat2;
    private Query mQuery;

    public static SportStatFragment newInstance(Utility.SportFilter filter) {
        SportStatFragment fragment = new SportStatFragment();

        Bundle args = new Bundle();
        args.putSerializable("filter", filter);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        filter = (Utility.SportFilter)bundle.getSerializable("filter");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sport_stat_layout, container, false);
        mSportIcon = (ImageView)view.findViewById(R.id.sportIcon);
        mGamesPlayed = (TextView)view.findViewById(R.id.gamesPlayed);
        mRecord = (TextView)view.findViewById(R.id.recordText);
        mSpecificStat1 = (TextView)view.findViewById(R.id.specific_stat_1);
        mSpecificStat2 = (TextView)view.findViewById(R.id.specific_stat_2);

        SharedPreferences prefs = getActivity().getSharedPreferences(Utility.prefsFile, Context.MODE_PRIVATE);
        String userName = prefs.getString("userName", null);

        Firebase ref = new Firebase("https://sizzling-torch-801.firebaseio.com/profiles");
        mQuery = ref.orderByChild("name").equalTo(userName);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        switch (filter) {
            case BASKETBALL:
                mSportIcon.setImageResource(R.drawable.basketball);
                mSpecificStat1.setText(getString(R.string.points));
                mSpecificStat2.setText(getString(R.string.assists));
                populateStats("basketball");
                break;
            case FOOTBALL:
                mSportIcon.setImageResource(R.drawable.football);
                mSpecificStat1.setText(getString(R.string.touchdowns));
                mSpecificStat2.setText(getString(R.string.interceptions));
                populateStats("football");
                break;
            case SOCCER:
                mSportIcon.setImageResource(R.drawable.soccer);
                mSpecificStat1.setText(getString(R.string.goals));
                mSpecificStat2.setText(getString(R.string.assists));
                populateStats("soccer");
                break;
            case VOLLEYBALL:
                mSportIcon.setImageResource(R.drawable.volleyball);
                mSpecificStat1.setText(getString(R.string.points));
                mSpecificStat2.setText(getString(R.string.aces));
                populateStats("volleyball");
                break;
        }
    }

    private void populateStats(final String filter) {
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot sportSnapshot : postSnapshot.getChildren()) {

                        if (sportSnapshot.getKey().equals(filter)) {
                            IndividualSportInfo sportInfo = sportSnapshot.getValue(IndividualSportInfo.class);

                            int wins = sportInfo.getWins();
                            int losses = sportInfo.getLosses();
                            int totalGames = wins+losses;
                            String record = wins + "-" + losses;
                            int stat1 = sportInfo.getStat1();
                            int stat2 = sportInfo.getStat2();

                            mGamesPlayed.append(" " + String.valueOf(totalGames));
                            mRecord.append(" " + record);
                            mSpecificStat1.append(" " + String.valueOf(stat1));
                            mSpecificStat2.append(" " + String.valueOf(stat2));
                        }

                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
            }
        });
    }
}
