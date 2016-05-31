package edu.galileo.android.tipcalc.fragments;

import edu.galileo.android.tipcalc.models.TipRecord;

/**
 * Created by carlos.gomez on 31/05/2016.
 */
public interface TipHistoryListFragmentListener {
    void addToList(TipRecord record);

    void clearList();

}
