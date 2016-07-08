package edu.galileo.android.tipcalc.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public interface DolarPyService {
    @GET //No hay ninguna url extra que pasar al webservice
    Call<DolarPyResponse> getCurrencyRates(@Url String url);
}
