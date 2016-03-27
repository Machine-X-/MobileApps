package com.thebeast.com.thebeast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by loganpatino on 2/24/16.
 */
public class TestFragment3 extends Fragment implements View.OnClickListener {

    private Button goToMapViewer;
    private Button goToFBLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout_3, container, false);

        goToMapViewer = (Button) view.findViewById(R.id.b_go_to_map_viewer);
        goToMapViewer.setOnClickListener(this);
        goToFBLogin = (Button) view.findViewById(R.id.b_go_to_fb_login);
        goToFBLogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.b_go_to_fb_login:
                startActivity(new Intent(getActivity(), LoginToFacebook.class));
                break;
            case R.id.b_go_to_map_viewer:
                startActivity(new Intent(getActivity(), MapsActivity.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached TestFragment3.onStart();");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached TestFragment3.onPause();");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached TestFragment3.onResume();");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached TestFragment3.onStop();");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached TestFragment3.onDestroy();");
    }
}
