package com.thienpg.flicks;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private SwipeRefreshLayout swipeContainer;


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
                swipeContainer.setRefreshing(false);

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

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                getNowPlaying();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvNowPlaying.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("aaa", "Aa");
            }
        });
    }
}
