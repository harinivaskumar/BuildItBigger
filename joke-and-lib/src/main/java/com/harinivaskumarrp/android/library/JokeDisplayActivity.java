package com.harinivaskumarrp.android.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        TextView jokeTextView = (TextView) findViewById(R.id.jokeTextView);
        jokeTextView.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));
    }
}
