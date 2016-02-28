package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by loganpatino on 2/24/16.
 */
public class TestFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout, container, false);

        String[] gameList = {"Game 1", "Game 2", "Game 3", "Game 4"
                , "Game 5", "Game 6", "Game 7", "Game 8", "Game 9"
                , "Game 10", "Game 11", "Game 12"};

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(gameList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
