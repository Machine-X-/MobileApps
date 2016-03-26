package com.thebeast.com.thebeast;

import com.firebase.client.Firebase;

/**
 * Created by loganpatino on 3/25/16.
 */
public class TheBeast extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
