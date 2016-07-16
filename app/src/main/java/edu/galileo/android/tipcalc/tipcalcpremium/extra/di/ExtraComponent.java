package edu.galileo.android.tipcalc.tipcalcpremium.extra.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.tipcalc.libs.di.LibsModule;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.adapters.ExtraAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraPresenter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.ui.ExtraFragment;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, ExtraModule.class})
public interface ExtraComponent {
    void inject(ExtraFragment fragment);
    ExtraPresenter getPresenter();
    ExtraAdapter getAdapter();
}
