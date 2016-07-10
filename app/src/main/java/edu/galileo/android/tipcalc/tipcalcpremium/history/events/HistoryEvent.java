package edu.galileo.android.tipcalc.tipcalcpremium.history.events;

import java.util.List;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryEvent {
    public final static int onHistoryAdded = 0;
    public final static int onHistoryRemoved = 1;

    private String error;
    private List<TipRecordPremium> tipRecordPremiumList;
    private int eventType;

    public List<TipRecordPremium> getTipRecordPremiumList() {
        return tipRecordPremiumList;
    }

    public void setTipRecordPremium(List<TipRecordPremium> tipRecordPremiumList) {
        this.tipRecordPremiumList = tipRecordPremiumList;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
