package edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp;

import java.text.DecimalFormat;
import java.text.ParseException;
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
    private static final String TAG = ExtraRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private DolarPyService service;
    private List<DolarPy> items;

    public ExtraRepositoryImpl(EventBus eventBus, DolarPyService service) {
        this.eventBus = eventBus;
        this.service = service;
        this.items = new ArrayList<>();
    }

    @Override
    public void getDataFromWebService() {
        //llamada a retrofit
        Call<DolarPyResponse> call = service.getCurrencyRates();
        Callback<DolarPyResponse> callback = new Callback<DolarPyResponse>() {
            @Override
            public void onResponse(Call<DolarPyResponse> call, Response<DolarPyResponse> response) {
                if (response.isSuccess()) {
                    DolarPyResponse dolarPyResponse = response.body(); //Gson parsea aqui
                    ExtraRepositoryImpl.this.getAlberdiCurrency(dolarPyResponse);
                    ExtraRepositoryImpl.this.getBcpCurrency(dolarPyResponse);
                    ExtraRepositoryImpl.this.getMaxiCurrency(dolarPyResponse);
                    ExtraRepositoryImpl.this.getChacoCurrency(dolarPyResponse);
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

    public void getAlberdiCurrency(DolarPyResponse dolarPyResponse){
        DolarPy dolarPy = new DolarPy();
        DecimalFormat decimalFormat = new DecimalFormat(".");
        dolarPy.setEntity("Cambios Alberdi");
        dolarPy.setPurchase(0L);
        dolarPy.setSale(0L);
        String purchase = dolarPyResponse.getdolarpy().getCambiosalberdi().getCompra();
        String sale = dolarPyResponse.getdolarpy().getCambiosalberdi().getVenta();
        try {
            dolarPy.setPurchase(decimalFormat.parse(purchase).longValue());
            dolarPy.setSale(decimalFormat.parse(sale).longValue());
        } catch (ParseException e) {
            post(e.getLocalizedMessage());
        }
        dolarPy.setLast_update(dolarPyResponse.getUpdated());
        items.add(dolarPy);
    }

    public void getBcpCurrency(DolarPyResponse dolarPyResponse){
        DolarPy dolarPy = new DolarPy();
        DecimalFormat decimalFormat = new DecimalFormat(".");
        dolarPy.setEntity("BCP");
        dolarPy.setPurchase(0L);
        dolarPy.setSale(0L);
        String purchase = dolarPyResponse.getdolarpy().getbcp().getCompra();
        String sale = dolarPyResponse.getdolarpy().getbcp().getVenta();
        String daily = dolarPyResponse.getdolarpy().getbcp().getReferencial_diario();
        try {
            dolarPy.setPurchase(decimalFormat.parse(purchase).longValue());
            dolarPy.setSale(decimalFormat.parse(sale).longValue());
            dolarPy.setDaily_ref(decimalFormat.parse(daily).longValue());
        } catch (ParseException e) {
            post(e.getLocalizedMessage());
        }
        dolarPy.setLast_update(dolarPyResponse.getUpdated());
        items.add(dolarPy);
    }

    public void getChacoCurrency(DolarPyResponse dolarPyResponse){
        DolarPy dolarPy = new DolarPy();
        DecimalFormat decimalFormat = new DecimalFormat(".");
        dolarPy.setEntity("Cambios Chaco");
        dolarPy.setPurchase(0L);
        dolarPy.setSale(0L);
        String purchase = dolarPyResponse.getdolarpy().getCambioschaco().getCompra();
        String sale = dolarPyResponse.getdolarpy().getCambioschaco().getVenta();
        try {
            dolarPy.setPurchase(decimalFormat.parse(purchase).longValue());
            dolarPy.setSale(decimalFormat.parse(sale).longValue());
        } catch (ParseException e) {
            post(e.getLocalizedMessage());
        }
        dolarPy.setLast_update(dolarPyResponse.getUpdated());
        items.add(dolarPy);
    }

    public void getMaxiCurrency(DolarPyResponse dolarPyResponse){
        DolarPy dolarPy = new DolarPy();
        DecimalFormat decimalFormat = new DecimalFormat(".");
        dolarPy.setEntity("Maxi Cambios");
        dolarPy.setPurchase(0L);
        dolarPy.setSale(0L);
        String purchase = dolarPyResponse.getdolarpy().getMaxicambios().getCompra();
        String sale = dolarPyResponse.getdolarpy().getMaxicambios().getVenta();
        try {
            dolarPy.setPurchase(decimalFormat.parse(purchase).longValue());
            dolarPy.setSale(decimalFormat.parse(sale).longValue());
        } catch (ParseException e) {
            post(e.getLocalizedMessage());
        }
        dolarPy.setLast_update(dolarPyResponse.getUpdated());
        items.add(dolarPy);
    }
}
