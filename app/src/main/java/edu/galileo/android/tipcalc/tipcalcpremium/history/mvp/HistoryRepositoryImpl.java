package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
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
    private Firebase dataReference;
    private FirebaseHelper firebaseHelper;

    public HistoryRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.dataReference = this.firebaseHelper.getDataReference();
    }

    @Override
    public void getTipHistory(String facebookUserId) {
        List<TipRecordPremium> items = new ArrayList<>();
        post(items, HistoryEvent.onHistoryRetrieved);
        Log.d("REPOSITORY", "getting tip history for facebook user id: " + facebookUserId);
    }

    @Override
    public void saveTip(final TipRecordPremium tipRecordPremium) {
        dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tipId = String.valueOf(tipRecordPremium.getTipId());
                Firebase tipReference = firebaseHelper.getMyTipsReference().child(tipId);
                tipReference.child("bill").setValue(tipRecordPremium.getBill());
                tipReference.child("tipPercentage").setValue(tipRecordPremium.getTipPercentage());
                tipReference.child("timestamp").setValue(tipRecordPremium.getDateFormatted());
                tipReference.child("latitutde").setValue(tipRecordPremium.getLatitutde());
                tipReference.child("longitude").setValue(tipRecordPremium.getLongitude());

                List<TipRecordPremium> items = Arrays.asList(tipRecordPremium);
                post(items, HistoryEvent.onHistoryAdded);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                post("Error onCancelled: " + firebaseError.getMessage(), HistoryEvent.onHistoryAdded);
            }
        });
        /*List<TipRecordPremium> items = Arrays.asList(tipRecordPremium);
        post(items, HistoryEvent.onHistoryAdded);*/
        Log.d("REPOSITORY", "save tip");
    }

    @Override
    public void deleteTip(TipRecordPremium tipRecordPremium) {
        List<TipRecordPremium> items = Arrays.asList(tipRecordPremium);
        post(items, HistoryEvent.onHistoryRemoved);
        Log.d("REPOSITORY", "delete tip");
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
