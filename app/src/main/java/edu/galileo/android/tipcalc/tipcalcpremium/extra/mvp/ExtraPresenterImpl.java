package edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.tipcalc.libs.base.EventBus;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.events.ExtraEvent;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public class ExtraPresenterImpl implements ExtraPresenter {
    private ExtraView view;
    private EventBus eventBus;
    private ExtraInteractor interactor;

    public ExtraPresenterImpl(ExtraView view, EventBus eventBus, ExtraInteractor interactor) {
        this.view = view;
        this.eventBus = eventBus;
        this.interactor = interactor;
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getDataFromWebService() {
        if (view != null){
            view.hideData();
            view.showProgress();
        }
        interactor.getDataFromWebService();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ExtraEvent event) {
        String errorMsg = event.getError();
        if (view != null){
            view.showData();
            view.hideProgress();
            if (errorMsg != null){
                view.onError(errorMsg);
            }else{
                view.setContent(event.getItems());
            }
        }
    }
}
