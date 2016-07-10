package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public interface HistoryRepository {
    void getTipHistory(String facebookUserId);
    void saveTip(String facebookUserId);
    void deleteTip(String facebookUserId);
}
