package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.concurrent.ExecutionException;

/**
 * Created by Hari Nivas Kumar R P on 5/28/2016.
 */
public class JokeGCMBackEndTest extends AndroidTestCase implements EndpointsAsyncTask.JokeListener{
    private static final String LOG_TAG = JokeGCMBackEndTest.class.getSimpleName();
    private String jokeStr;

    public void testBackend() throws ExecutionException, InterruptedException{
        new EndpointsAsyncTask(mContext, this,"Hari").execute().get();
    }

    @Override
    public void getJokeResult(String jokeString) {
        Log.d(LOG_TAG, "Joke is - " + jokeString + " and Length - " + jokeString.length());
        assertTrue(jokeString, jokeString.length() > 0);
    }

    public void testBackend2() throws ExecutionException, InterruptedException {
        new EndpointsAsyncTask(mContext,
                new EndpointsAsyncTask.JokeListener() {
                    @Override
                    public void getJokeResult(String jokeResult) {
                        jokeStr = jokeResult;
                    }
                },
                "Nivas")
                .execute().get();
        
        Log.d(LOG_TAG, "Joke is - " + jokeStr + " and Length - " + jokeStr.length());
        assertTrue(jokeStr, jokeStr.length() > 0);
    }
}
