package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.harinivaskumarrp.android.library.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements View.OnClickListener, EndpointsAsyncTask.JokeListener{

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private Button mJokeButton;
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mJokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        mJokeButton.setOnClickListener(this);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        return root;
    }

    @Override
    public void onClick(View view) {
        displayJoke();
    }

    public void displayJoke(){
        if (Utility.isNetworkAvailable(getContext(), LOG_TAG)) {
            mProgressBar.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), R.string.please_wait, Toast.LENGTH_SHORT).show();
            new EndpointsAsyncTask(getContext(), this, "HariNivas-" + R.string.flavour)
                    .execute();
        }else{
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getJokeResult(String joke) {
        mProgressBar.setVisibility(View.GONE);
        if (null == joke){
            Toast.makeText(getActivity(), R.string.no_joke, Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Joke String is null!");
        }
        else {
            Intent jokeDisplayIntent = new Intent(getActivity(), JokeDisplayActivity.class);
            //jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, Joke.getJoke());
            jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, joke);
            startActivity(jokeDisplayIntent);
        }
    }
}
