package net.laggedhero.finalapp.services;

import net.laggedhero.finalapp.models.NasaApod;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NasaApodService {

    @GET("/planetary/apod")
    Call<NasaApod> getTodayPhoto(@Query("api_key") String apiKey);

    @GET("/planetary/apod")
    Call<NasaApod> getPhotoByDate(@Query("date") String date, @Query("api_key") String apiKey);
}
