package net.laggedhero.anotherapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyServiceInterface {

    @GET("v2/576539791100005a0ea92a52")
    Call<Positions> searchPositions();
}
