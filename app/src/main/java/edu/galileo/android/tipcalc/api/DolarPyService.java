package edu.galileo.android.tipcalc.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public interface DolarPyService {
    @GET("/api/1.0/")
    Call<DolarPyResponse> getCurrencyRates();
}
