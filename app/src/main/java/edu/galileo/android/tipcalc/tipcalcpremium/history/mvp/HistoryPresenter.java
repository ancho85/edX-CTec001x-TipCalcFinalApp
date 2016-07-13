package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.tipcalcpremium.history.events.HistoryEvent;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public interface HistoryPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getTipHistory();
    void onEventMainThread(HistoryEvent event);

    void addTip(TipRecordPremium tipRecordPremium);
    void deleteTip(TipRecordPremium tipRecordPremium);
}
