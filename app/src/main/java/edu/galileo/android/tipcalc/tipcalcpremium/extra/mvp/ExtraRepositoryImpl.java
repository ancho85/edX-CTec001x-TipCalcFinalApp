package edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp;

import java.util.ArrayList;
import java.util.List;

import edu.galileo.android.tipcalc.api.DolarPyResponse;
import edu.galileo.android.tipcalc.api.DolarPyService;
import edu.galileo.android.tipcalc.entities.DolarPy;
import edu.galileo.android.tipcalc.libs.base.EventBus;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.events.ExtraEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public class ExtraRepositoryImpl implements ExtraRepository {
    private EventBus eventBus;
    private DolarPyService service;

    public ExtraRepositoryImpl(EventBus eventBus, DolarPyService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getDataFromWebService() {
        //llamada a retrofit
        Call<DolarPyResponse> call = service.getCurrencyRates("");
        Callback<DolarPyResponse> callback = new Callback<DolarPyResponse>() {
            @Override
            public void onResponse(Call<DolarPyResponse> call, Response<DolarPyResponse> response) {
                List<DolarPy> items = new ArrayList<>();
                if (response.isSuccess()) {
                    DolarPyResponse dolarPyResponse = response.body(); //Gson parsea aqui
                    DolarPy dolarPy = dolarPyResponse.getFirstDolarPy();
                    if (dolarPy != null) {
                        items.add(dolarPy);
                    } else {
                        post(response.message());
                    }
                }
                post(items);
            }

            @Override
            public void onFailure(Call<DolarPyResponse> call, Throwable t) {
                post(t.getLocalizedMessage());
            }
        };
        call.enqueue(callback);
    }

    private void post(List<DolarPy> items) {
        post(items, null);
    }

    private void post(String error) {
        post(null, error);
    }

    private void post(List<DolarPy> items, String error) {
        ExtraEvent event = new ExtraEvent();
        event.setError(error);
        event.setItems(items);
        eventBus.post(event);
    }
}
