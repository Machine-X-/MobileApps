package com.thebeast.com.thebeast;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by loganpatino on 3/26/16.
 */
public class Utility {

    public enum SportFilter {
        ALL,
        BASKETBALL,
        FOOTBALL,
        SOCCER,
        VOLLEYBALL
    }

    public static String prefsFile = "prefsFile";

    public static String getTime(int hour, int minute) {
        String period = " AM";
        String minString = String.valueOf(minute);
        if (hour == 12) {
            period = " PM";
        }
        else if (hour == 24) {
            hour = hour - 12;
        }
        else if (hour - 12 > 0) {
            hour = hour - 12;
            period = " PM";
        }
        if (minute < 10) {
            minString = "0" + minString;
        }
        return hour + ":" + minString + period;
    }

    public static String getTime(int minutes) {
        String period = " AM";
        int hour = minutes / 60;
        if (hour == 12) {
            period = " PM";
        }
        else if (hour > 12) {
            hour = hour - 12;
            period = " PM";
        }
        minutes = minutes % 60;
        String minString = String.valueOf(minutes);
        if (minutes < 10) {
            minString = "0" + minString;
        }
        return hour + ":" + minString + period;
    }

    public static int getMins(int hour, int minute) {
        return hour * 60 + minute;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

}
