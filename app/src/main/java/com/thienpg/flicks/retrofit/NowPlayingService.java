package com.thienpg.flicks.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thienpg.flicks.model.NowPlaying;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

import static com.thienpg.flicks.utility.Constant.BASE_URL;
import static com.thienpg.flicks.utility.Constant.API_KEY;

/**
 * Created by ThienPG on 2/18/2017.
 */

public class NowPlayingService {

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
                Log.d("onResponse", response.body().getResults().get(0).getTitle());
                Log.d("onResponse", response.body().getResults().get(0).getPosterPath());
                Log.d("onResponse", response.body().getResults().get(0).getBackdropPath());
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

}
