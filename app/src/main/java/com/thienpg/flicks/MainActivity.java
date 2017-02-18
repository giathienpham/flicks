package com.thienpg.flicks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thienpg.flicks.model.NowPlaying;
import com.thienpg.flicks.retrofit.NowPlayingService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NowPlayingService service = new NowPlayingService();
        service.getNowPlaying();
    }
}
