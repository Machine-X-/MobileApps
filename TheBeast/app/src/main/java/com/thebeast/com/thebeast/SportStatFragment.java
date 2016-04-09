package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by loganpatino on 4/7/16.
 */
public class SportStatFragment extends Fragment {

    private Utility.SportFilter filter;
    private ImageView sportIcon;
    private TextView gamesPlayed;
    private TextView specificStat1;
    private TextView specificStat2;

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
        sportIcon = (ImageView)view.findViewById(R.id.sportIcon);
        gamesPlayed = (TextView)view.findViewById(R.id.gamesPlayed);
        specificStat1 = (TextView)view.findViewById(R.id.specific_stat_1);
        specificStat2 = (TextView)view.findViewById(R.id.specific_stat_2);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        switch (filter) {
            case BASKETBALL:
                sportIcon.setImageResource(R.drawable.basketball);
                specificStat1.setText(getString(R.string.points));
                specificStat2.setText(getString(R.string.assists));
                break;
            case FOOTBALL:
                sportIcon.setImageResource(R.drawable.football);
                specificStat1.setText(getString(R.string.touchdowns));
                specificStat2.setText(getString(R.string.interceptions));
                break;
            case SOCCER:
                sportIcon.setImageResource(R.drawable.soccer);
                specificStat1.setText(getString(R.string.goals));
                specificStat2.setText(getString(R.string.assists));
                break;
            case VOLLEYBALL:
                sportIcon.setImageResource(R.drawable.volleyball);
                specificStat1.setText(getString(R.string.points));
                specificStat2.setText(getString(R.string.aces));
                break;
        }
    }
}
