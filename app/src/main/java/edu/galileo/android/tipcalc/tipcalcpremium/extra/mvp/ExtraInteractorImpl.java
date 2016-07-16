package edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public class ExtraInteractorImpl implements ExtraInteractor {
    ExtraRepository repository;

    public ExtraInteractorImpl(ExtraRepository repository) {
        this.repository = repository;
    }

    @Override
    public void getDataFromWebService() {
        repository.getDataFromWebService();
    }
}
