package edu.galileo.android.tipcalc.tipcalcpremium.history.ui;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 11/07/2016.
 */
public interface HistoryFragmentListener {
    void addToHistory(TipRecordPremium tipRecordPremium);
    void deleteFromHistory(TipRecordPremium tipRecordPremium);
}
