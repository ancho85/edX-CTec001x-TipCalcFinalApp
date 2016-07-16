package edu.galileo.android.tipcalc.tipcalcpremium.extra.events;

import java.util.List;

import edu.galileo.android.tipcalc.entities.DolarPy;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public class ExtraEvent {
    private String error;
    private List<DolarPy> items;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DolarPy> getItems() {
        return items;
    }

    public void setImages(List<DolarPy> items) {
        this.items = items;
    }
}
