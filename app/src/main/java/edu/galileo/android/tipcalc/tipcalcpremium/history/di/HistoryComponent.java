package edu.galileo.android.tipcalc.tipcalcpremium.history.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.tipcalc.libs.di.LibsModule;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.HistoryAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryPresenter;

/**
 * Created by carlos.gomez on 11/07/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, HistoryModule.class})
public interface HistoryComponent {
    HistoryPresenter getPresenter();
    HistoryAdapter getAdapter();
}
