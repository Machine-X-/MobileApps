package com.thebeast.com.thebeast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class SoccerListFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    FirebaseRecyclerAdapter<Game, RecyclerViewHolder> mAdapter;
    Firebase mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");
        Query mRefQuery = mRef.orderByChild("sport").equalTo("Soccer");

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<Game, RecyclerViewHolder>(Game.class, R.layout.list_item_view, RecyclerViewHolder.class, mRefQuery) {
            @Override
            public void populateViewHolder(RecyclerViewHolder recyclerViewHolder, final Game game, int i) {
                recyclerViewHolder.setItemText(game.getSport());
                recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
                        intent.putExtra("sport", game.getSport());
                        intent.putExtra("location", game.getLocation());
                        intent.putExtra("additional_info", game.getAdditionalInfo());
                        intent.putExtra("time", game.getTime());
                        intent.putExtra("team_size", game.getTeamSize());
                        startActivity(intent);
                    }
                });
            }
        };

        mRecyclerView.setAdapter(mAdapter);
    }
}
