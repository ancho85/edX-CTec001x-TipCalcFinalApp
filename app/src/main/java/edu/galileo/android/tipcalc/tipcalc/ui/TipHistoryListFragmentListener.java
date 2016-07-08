package edu.galileo.android.tipcalc.tipcalc.ui;

import edu.galileo.android.tipcalc.entities.TipRecord;

/**
 * Created by carlos.gomez on 31/05/2016.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);

    void clearList();

}
