package edu.galileo.android.tipcalc.tipcalcpremium.history.adapters;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public interface OnItemClickListener {
    //void onItemClick(TipRecordPremium tipRecordPremium);
    void onItemLongClick(TipRecordPremium tipRecordPremium);
    void onFbShareClick(TipRecordPremium tipRecordPremium);
    void onFbSendClick(TipRecordPremium tipRecordPremium);
    void onMyLocationClick(TipRecordPremium tipRecordPremium);
}
