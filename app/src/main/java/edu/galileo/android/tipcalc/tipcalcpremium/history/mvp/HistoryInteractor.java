package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public interface HistoryInteractor {
    void addTip(TipRecordPremium tipRecordPremium);
    void deleteTip(TipRecordPremium tipRecordPremium);
    void getTipHistory();
}
