package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by loganpatino on 2/24/16.
 */
public class TestFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    FirebaseRecyclerAdapter<Game, RecyclerViewHolder> mAdapter;
    Firebase mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<Game, RecyclerViewHolder>(Game.class, R.layout.list_item_view, RecyclerViewHolder.class, mRef) {
            @Override
            public void populateViewHolder(RecyclerViewHolder recyclerViewHolder, Game game, int i) {
                recyclerViewHolder.setItemText(game.getSport());
            }
        };

        mRecyclerView.setAdapter(mAdapter);
    }
}
