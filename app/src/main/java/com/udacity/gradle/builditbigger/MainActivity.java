package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.harinivaskumarrp.android.library.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.JokeListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayJoke(View view){
        if (Utility.isNetworkAvailable(this, LOG_TAG)) {
            new EndpointsAsyncTask(this, this, "HariNivas")
                    .execute();
        }else{
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getJokeResult(String joke) {
        if (null == joke){
            Toast.makeText(this, "No Joke currently! try after some time.", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Joke String is null!");
        }
        else {
            Intent jokeDisplayIntent = new Intent(this, JokeDisplayActivity.class);
            //jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, Joke.getJoke());
            jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, joke);
            startActivity(jokeDisplayIntent);
        }
    }
}
