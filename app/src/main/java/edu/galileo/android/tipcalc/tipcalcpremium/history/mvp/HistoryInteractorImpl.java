package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryInteractorImpl implements HistoryInteractor {
    private HistoryRepository repository;

    public HistoryInteractorImpl(HistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addTip(TipRecordPremium tipRecordPremium) {
        repository.saveTip(tipRecordPremium);
    }

    @Override
    public void deleteTip(TipRecordPremium tipRecordPremium) {
        repository.deleteTip(tipRecordPremium);
    }

    @Override
    public void getTipHistory(String facebookUserId) {
        repository.getTipHistory(facebookUserId);
    }
}
