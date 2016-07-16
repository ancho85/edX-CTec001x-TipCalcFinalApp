package edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp;

import java.util.List;

import edu.galileo.android.tipcalc.entities.DolarPy;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public interface ExtraView {
    void showData();
    void hideData();
    void showProgress();
    void hideProgress();

    void onError(String error);
    void setContent(List<DolarPy> items);
}
