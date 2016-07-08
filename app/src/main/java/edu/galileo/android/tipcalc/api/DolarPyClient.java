package edu.galileo.android.tipcalc.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class DolarPyClient {
    private Retrofit retrofit;
    private final static String BASE_URL = "https://dolar.melizeche.com/api/1.0/";

    public DolarPyClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public DolarPyService getDolarPyService(){
        return retrofit.create(DolarPyService.class);
    }
}
