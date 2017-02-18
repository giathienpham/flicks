package com.thienpg.flicks.retrofit;

import com.thienpg.flicks.model.NowPlaying;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ThienPG on 2/18/2017.
 */

public interface ApiEndpointInterface {

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String key,
            @Query("page") Integer page);



}
