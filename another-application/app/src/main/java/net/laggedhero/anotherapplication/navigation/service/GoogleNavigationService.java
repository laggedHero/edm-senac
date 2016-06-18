package net.laggedhero.anotherapplication.navigation.service;

import net.laggedhero.anotherapplication.navigation.data.Directions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleNavigationService {

    @GET("maps/api/directions/json")
    Call<Directions> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String apiKey);
}
