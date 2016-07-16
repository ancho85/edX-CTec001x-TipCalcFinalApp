package edu.galileo.android.tipcalc.tipcalcpremium.extra.di;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.tipcalc.api.DolarPyClient;
import edu.galileo.android.tipcalc.api.DolarPyService;
import edu.galileo.android.tipcalc.entities.DolarPy;
import edu.galileo.android.tipcalc.libs.base.EventBus;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.adapters.ExtraAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraInteractor;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraInteractorImpl;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraPresenter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraPresenterImpl;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraRepository;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraRepositoryImpl;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraView;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
@Module
public class ExtraModule {
    private Context context;
    private ExtraView view;

    public ExtraModule(Context context, ExtraView view) {
        this.context = context;
        this.view = view;
    }

    @Singleton
    @Provides
    ExtraAdapter providesExtraAdapter(List<DolarPy> items){
        return new ExtraAdapter(items);
    }

    @Singleton
    @Provides
    List<DolarPy> providesItemsList(){
        return new ArrayList<DolarPy>();
    }

    @Singleton
    @Provides
    ExtraPresenter providesExtraPresenter(ExtraView view, EventBus eventBus, ExtraInteractor interactor){
        return new ExtraPresenterImpl(view, eventBus, interactor);
    }

    @Singleton
    @Provides
    ExtraView providesExtraView(){
        return this.view;
    }

    @Singleton
    @Provides
    ExtraInteractor providesExtraInteractor(ExtraRepository repository){
        return new ExtraInteractorImpl(repository);
    }

    @Singleton
    @Provides
    ExtraRepository providesExtraRepository(EventBus eventBus, DolarPyService service){
        return new ExtraRepositoryImpl(eventBus, service);
    }

    @Singleton
    @Provides
    DolarPyService providesDolarPyService(Context context){
        return new DolarPyClient(context).getDolarPyService();
    }

    @Provides
    @Singleton
    Context providesContext() {
        return context;
    }
}
