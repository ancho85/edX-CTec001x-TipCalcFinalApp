package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import edu.galileo.android.tipcalc.domain.FirebaseHelper;
import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.libs.base.EventBus;
import edu.galileo.android.tipcalc.tipcalcpremium.history.events.HistoryEvent;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryRepositoryImpl implements HistoryRepository {
    private EventBus eventBus;
    private FirebaseHelper firebaseHelper;

    public HistoryRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        this.firebaseHelper = FirebaseHelper.getInstance();
    }

    @Override
    public void getTipHistory(String facebookUserId) {

    }

    @Override
    public void saveTip(final TipRecordPremium tipRecordPremium) {
        Firebase userReference = firebaseHelper.getUserReference(tipRecordPremium.getFacebookUserId());
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TipRecordPremium tipRecord = dataSnapshot.getValue(TipRecordPremium.class);
                if (tipRecord != null) {
                    Firebase myReference = firebaseHelper.getUserReference(tipRecordPremium.getFacebookUserId());
                    myReference.child(
                            String.valueOf(tipRecordPremium.getTipId())
                    ).setValue(String.valueOf(tipRecordPremium.getDateFormatted()));
                    List<TipRecordPremium> items = Arrays.asList(tipRecordPremium);
                    post(items, HistoryEvent.onHistoryAdded);
                }else {
                    post("Error onDataChange", HistoryEvent.onHistoryAdded);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                post("Error onCancelled: " + firebaseError.getMessage(), HistoryEvent.onHistoryAdded);
            }
        });
    }

    @Override
    public void deleteTip(TipRecordPremium tipRecordPremium) {

    }


    private void post(List<TipRecordPremium> items, int eventType) {
        post(items, null, eventType);
    }

    private void post(String error, int eventType) {
        post(null, error, eventType);
    }

    private void post(List<TipRecordPremium> items, String error, int eventType) {
        HistoryEvent event = new HistoryEvent();
        event.setError(error);
        event.setTipRecordPremium(items);
        event.setEventType(eventType);
        eventBus.post(event);
    }
}
