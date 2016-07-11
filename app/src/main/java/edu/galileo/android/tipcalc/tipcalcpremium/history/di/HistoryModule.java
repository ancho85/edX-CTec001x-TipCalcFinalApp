package edu.galileo.android.tipcalc.tipcalcpremium.history.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.libs.base.EventBus;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.HistoryAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.OnItemClickListener;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryInteractor;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryInteractorImpl;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryPresenter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryPresenterImpl;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryRepository;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryRepositoryImpl;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryView;

/**
 * Created by carlos.gomez on 11/07/2016.
 */
@Module
public class HistoryModule {
    HistoryView view;
    OnItemClickListener listener;

    public HistoryModule(HistoryView view, OnItemClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Provides
    @Singleton
    HistoryView providesHistoryView() {
        return this.view;
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.listener;
    }

    @Provides
    @Singleton
    HistoryAdapter providesHistoryAdapter(List<TipRecordPremium> items, OnItemClickListener clickListener){
        return new HistoryAdapter(items, clickListener);
    }

    @Provides
    @Singleton
    List<TipRecordPremium> providesItemsList(){
        return new ArrayList<TipRecordPremium>();
    }

    @Provides
    @Singleton
    HistoryPresenter providesHistoryPresenter(HistoryView view, EventBus eventBus, HistoryInteractor interactor) {
        return new HistoryPresenterImpl(view, eventBus, interactor);
    }

    @Provides
    @Singleton
    HistoryInteractor providesHistoryInteractor(HistoryRepository repository) {
        return new HistoryInteractorImpl(repository);
    }

    @Provides
    @Singleton
    HistoryRepository providesHistoryRepository(EventBus eventBus) {
        return new HistoryRepositoryImpl(eventBus);
    }
}
