package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

/**
 * Created by Hari Nivas Kumar R P on 5/28/2016.
 */
public class JokeGCMBackEndTest extends AndroidTestCase implements EndpointsAsyncTask.JokeListener{
    private static final String LOG_TAG = JokeGCMBackEndTest.class.getSimpleName();

    public void testBackend() {
        new EndpointsAsyncTask(mContext, this,"HariNivas").execute();
    }

    @Override
    public void getJokeResult(String jokeString) {
        Log.d(LOG_TAG, "Joke is - " + jokeString + " and Length - " + jokeString.length());
        assertTrue(jokeString, jokeString.length() > 0);
    }
}
