package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Hari Nivas Kumar R P on 5/29/2016.
 */
public class Utility {

    // Added from StackOverFlow
    public static boolean isNetworkAvailable(Context context, String logTag) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        Log.e(logTag, "No Internet Connection available.!");
        return false;
    }
}
