package edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp;

import edu.galileo.android.tipcalc.tipcalcpremium.extra.events.ExtraEvent;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public interface ExtraPresenter {
    void onResume();
    void onPause();
    void onDestroy();

    void getDataFromWebService();
    void onEventMainThread(ExtraEvent event);
}
