package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import java.util.List;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public interface HistoryView {
    void showElements();
    void hideElements();
    void showProgress();
    void hideProgress();

    void tipAdded();
    void tipDeleted();

    void onError(String error);
    void setContent(List<TipRecordPremium> items);
}
