package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.libs.base.EventBus;
import edu.galileo.android.tipcalc.tipcalcpremium.history.events.HistoryEvent;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryPresenterImpl implements HistoryPresenter {
    private HistoryView view;
    private EventBus eventBus;
    private HistoryInteractor interactor;

    public HistoryPresenterImpl(HistoryView view, EventBus eventBus, HistoryInteractor interactor) {
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
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getTipHistory(String facebookUserId) {
        if (view != null) {
            view.hideElements();
            view.showProgress();
            interactor.getTipHistory(facebookUserId);
        }
    }

    @Override
    public void addTip(TipRecordPremium tipRecordPremium) {
        if (view != null) {
            view.hideElements();
            view.showProgress();
        }
        interactor.addTip(tipRecordPremium);
    }

    @Override
    public void deleteTip(TipRecordPremium tipRecordPremium) {
        if (view != null) {
            view.hideElements();
            view.showProgress();
        }
        interactor.deleteTip(tipRecordPremium);
    }

    @Override
    @Subscribe
    public void onEventMainThread(HistoryEvent event) {
        String errorMsg = event.getError();
        if (view != null) {
            view.hideProgress();
            view.showElements();
            if (errorMsg != null) {
                view.onError(errorMsg);
            } else {
                if (event.getEventType() == HistoryEvent.onHistoryAdded) {
                    view.tipAdded();
                } else if (event.getEventType() == HistoryEvent.onHistoryRemoved) {
                    view.tipDeleted();
                } else if (event.getEventType() == HistoryEvent.onHistoryRetrieved){
                    view.setContent(event.getTipRecordPremiumList());
                }
            }
        }
    }
}
