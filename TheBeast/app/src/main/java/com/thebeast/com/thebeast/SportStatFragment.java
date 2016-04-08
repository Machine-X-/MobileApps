package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by loganpatino on 4/7/16.
 */
public class SportStatFragment extends Fragment {

    private Utility.SportFilter filter;
    private TextView sportText;

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
        sportText = (TextView)view.findViewById(R.id.sport_text);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        switch (filter) {
            case BASKETBALL:
                sportText.setText("BASKETBALL");
                break;
            case FOOTBALL:
                sportText.setText("FOOTBALL");
                break;
            case SOCCER:
                sportText.setText("SOCCER");
                break;
            case VOLLEYBALL:
                sportText.setText("VOLLEYBALL");
                break;
            default:
                sportText.setText("BASKETBALL");
        }
    }
}
