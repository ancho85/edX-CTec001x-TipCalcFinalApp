package edu.galileo.android.tipcalc.tipcalcpremium.history.mvp;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
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
    private static final String TAG = HistoryRepositoryImpl.class.getSimpleName();
    private EventBus eventBus;
    private Firebase dataReference;
    private FirebaseHelper firebaseHelper;

    public HistoryRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        this.firebaseHelper = FirebaseHelper.getInstance();
        this.dataReference = this.firebaseHelper.getDataReference();
    }

    @Override
    public void getTipHistory() {
        Log.d(TAG, "getting tip history for facebook user id: " + firebaseHelper.getAuthUserId());
        Firebase tipReference = firebaseHelper.getMyTipsReference();
        Query queryTips = tipReference.orderByKey();
        queryTips.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TipRecordPremium tipRecordPremium = dataSnapshot.getValue(TipRecordPremium.class);
                Log.d(TAG, "Adding" + String.valueOf(tipRecordPremium.getDateFormatted()));
                post(Arrays.asList(tipRecordPremium), HistoryEvent.onHistoryRetrieved);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
                tipReference.child("timestamp").setValue(tipRecordPremium.getTimestamp());
                tipReference.child("latitutde").setValue(tipRecordPremium.getLatitutde());
                tipReference.child("longitude").setValue(tipRecordPremium.getLongitude());
                tipReference.child("longitude").setValue(tipRecordPremium.getLongitude());

                List<TipRecordPremium> items = Arrays.asList(tipRecordPremium);
                post(items, HistoryEvent.onHistoryAdded);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                post("Error onCancelled: " + firebaseError.getMessage(), HistoryEvent.onHistoryAdded);
            }
        });
    }

    @Override
    public void deleteTip(final TipRecordPremium tipRecordPremium) {
        dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tipId = String.valueOf(tipRecordPremium.getTipId());
                firebaseHelper.getMyTipsReference().child(tipId).removeValue();
                List<TipRecordPremium> items = Arrays.asList(tipRecordPremium);
                post(items, HistoryEvent.onHistoryRemoved);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                post("Error onCancelled: " + firebaseError.getMessage(), HistoryEvent.onHistoryRemoved);
            }
        });
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
