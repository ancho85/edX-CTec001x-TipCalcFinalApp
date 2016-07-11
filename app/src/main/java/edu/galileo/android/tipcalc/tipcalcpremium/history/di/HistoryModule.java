package edu.galileo.android.tipcalc.tipcalcpremium.history.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.tipcalc.libs.base.EventBus;
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

    public HistoryModule(HistoryView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    HistoryView providesHistoryView() {
        return this.view;
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
