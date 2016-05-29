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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.harinivaskumarrp.android.library.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
        implements View.OnClickListener, EndpointsAsyncTask.JokeListener{

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private Button mJokeButton;
    private ProgressBar mProgressBar;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private String mJokeString;

    private final int ADVIEW_TYPE_BANNER = 1;
    private final int ADVIEW_TYPE_INTERSTITIAL = 2;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mJokeButton = (Button) root.findViewById(R.id.tell_joke_button);
        mJokeButton.setOnClickListener(this);

        mAdView = (AdView) root.findViewById(R.id.adView);
        loadAd(ADVIEW_TYPE_BANNER);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        if (Utility.isNetworkAvailable(getContext(), LOG_TAG)){
            mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    Log.d(LOG_TAG, "AD-Interstitial Closed! Joke Activity will be Started.");
                    loadAd(ADVIEW_TYPE_INTERSTITIAL);
                    startJokeActivity();
                }
            });
            loadAd(ADVIEW_TYPE_INTERSTITIAL);
        }

        return root;
    }

    private void loadAd(int adType) {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        switch (adType){
            case ADVIEW_TYPE_BANNER:
                mAdView.loadAd(adRequest);
                Log.d(LOG_TAG, "Load AD-Banner Requested.");
                break;
            case ADVIEW_TYPE_INTERSTITIAL:
                mInterstitialAd.loadAd(adRequest);
                Log.d(LOG_TAG, "Load AD-Interstitial Requested.");
                break;
            default:
                Log.e(LOG_TAG, "Unsupported AdType!");
        }
    }

    @Override
    public void onClick(View view) {
        displayJoke();
    }

    public void displayJoke(){
        if (Utility.isNetworkAvailable(getContext(), LOG_TAG)) {
            if (!mInterstitialAd.isLoaded() && mInterstitialAd.isLoading()){
                loadAd(ADVIEW_TYPE_INTERSTITIAL);
            }

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
        mJokeString = joke;
        if (mInterstitialAd.isLoaded()) {
            Log.d(LOG_TAG, "Load AD-Interstitial Success! Ad will be displayed.");
            mInterstitialAd.show();
        }
        else {
            loadAd(ADVIEW_TYPE_INTERSTITIAL);
            Log.d(LOG_TAG, "Load AD-Interstitial Failure! Joke Activity will be displayed.");
            startJokeActivity();
        }
    }

    private void startJokeActivity(){
        mProgressBar.setVisibility(View.GONE);
        if (null == mJokeString){
            Toast.makeText(getActivity(), R.string.no_joke, Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Joke String is null!");
        }
        else {
            Intent jokeDisplayIntent = new Intent(getActivity(), JokeDisplayActivity.class);
            //jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, Joke.getJoke());
            jokeDisplayIntent.putExtra(Intent.EXTRA_TEXT, mJokeString);
            Log.d(LOG_TAG, "Joke Activity Intent Triggered!");
            startActivity(jokeDisplayIntent);
        }
    }
}
