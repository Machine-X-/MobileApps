package com.thebeast.com.thebeast;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends Activity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
    }

}
