package com.thienpg.flicks;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thienpg.flicks.model.NowPlaying;
import com.thienpg.flicks.model.Result;
import com.thienpg.flicks.retrofit.ApiEndpointInterface;
import com.thienpg.flicks.retrofit.NowPlayingService;
import com.thienpg.flicks.utility.NowPlayingArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.thienpg.flicks.utility.Constant.API_KEY;
import static com.thienpg.flicks.utility.Constant.BASE_URL;

public class MainActivity extends AppCompatActivity {
    NowPlayingService service = new NowPlayingService();
    private ListView lvNowPlaying;
    NowPlaying data;
    Context mContext;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    ApiEndpointInterface apiService =
            retrofit.create(ApiEndpointInterface.class);


    public void getNowPlaying() {
        Call<NowPlaying> nowPlayings = apiService.getNowPlaying(API_KEY,1);
        nowPlayings.enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                data = response.body();
                lvNowPlaying.setAdapter(new NowPlayingArrayAdapter(mContext, data));
                Log.d("NowPlaying", data.getResults().get(0).getPosterPath());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lvNowPlaying = (ListView) findViewById(R.id.lvNowPlaying);
        mContext = this;
        getNowPlaying();

    }
}
