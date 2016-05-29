package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.harinivaskumarrp.android.library.JokeDisplayActivity;
import com.harinivaskumarrp.jokegcm.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Hari Nivas Kumar R P on 5/28/2016.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();
    private static MyApi myApiService = null;
    private Context mContext;
    private String mJokeString;
    private String mNameString;

    public EndpointsAsyncTask(Context context, String nameStr){
        mContext = context;
        mNameString = nameStr;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://hello-hari.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver
            myApiService = builder.build();
        }

        try {
            mJokeString = myApiService.sayHi(mNameString).execute().getData();
            Log.d(LOG_TAG, "Joke Received - " + mJokeString);
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void jokeString) {
        //Toast.makeText(mContext, mJokeString, Toast.LENGTH_LONG).show();
        Intent jokeDisplayIntent = new Intent(mContext, JokeDisplayActivity.class);
        //jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, Joke.getJoke());
        jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, mJokeString);
        mContext.startActivity(jokeDisplayIntent);
    }
}