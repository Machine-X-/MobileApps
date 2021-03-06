package com.thebeast.com.thebeast;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class GameListFragment extends Fragment {

    private RelativeLayout mRoot;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    FirebaseRecyclerAdapter<Game, RecyclerViewHolder> mAdapter;
    Firebase mRef;
    Utility.SportFilter filter;

    public static GameListFragment newInstance(Utility.SportFilter filter) {
        GameListFragment fragment = new GameListFragment();

        Bundle args = new Bundle();
        args.putSerializable("filter", filter);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        filter = (Utility.SportFilter)bundle.getSerializable("filter");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        mRoot = (RelativeLayout)view.findViewById(R.id.root);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRef = new Firebase("https://sizzling-torch-801.firebaseio.com/games");
        Query mRefQuery = mRef;

        switch (filter) {
            case ALL:
                break;
            case BASKETBALL:
                mRefQuery = mRef.orderByChild("sport").equalTo("Basketball");
                break;
            case FOOTBALL:
                mRefQuery = mRef.orderByChild("sport").equalTo("Football");
                break;
            case SOCCER:
                mRefQuery = mRef.orderByChild("sport").equalTo("Soccer");
                break;
            case VOLLEYBALL:
                mRefQuery = mRef.orderByChild("sport").equalTo("Volleyball");
                break;
            default:
                mRefQuery = mRef;
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FirebaseRecyclerAdapter<Game, RecyclerViewHolder>(Game.class, R.layout.list_item_view, RecyclerViewHolder.class, mRefQuery) {
            @Override
            public void populateViewHolder(RecyclerViewHolder recyclerViewHolder, final Game game, int i) {

                recyclerViewHolder.setSportIcon(game.getSport());
                recyclerViewHolder.setLocationText(game.getLocation());
                recyclerViewHolder.setTimeText(game.getTime());
                recyclerViewHolder.setTeamSizeText(game.getTeamSize());

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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
    }
}
