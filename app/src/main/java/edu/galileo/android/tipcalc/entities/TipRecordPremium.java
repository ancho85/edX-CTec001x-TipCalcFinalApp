package edu.galileo.android.tipcalc.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.galileo.android.tipcalc.db.TipCalcFinalAppDatabase;

/**
 * Created by carlos.gomez on 31/05/2016.
 */
@Table(database = TipCalcFinalAppDatabase.class)
public class TipRecordPremium extends BaseModel {
    @PrimaryKey
    private Long tipId;
    @Column
    private double bill;
    @Column
    private int tipPercentage;
    @Column
    private Date timestamp;
    @Column
    private String facebookUserId;
    @Column
    private double latitutde;
    @Column
    private double longitude;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getTipPercentage() {
        return tipPercentage;
    }

    public void setTipPercentage(int tipPercentage) {
        this.tipPercentage = tipPercentage;
    }

    public double getTip() {
        return bill * (tipPercentage / 100d);
    }

    public void setTipId(Long tipId) {
        this.tipId = tipId;
    }

    public Long getTipId() {
        return tipId;
    }

    public double getLatitutde() {
        return latitutde;
    }

    public void setLatitutde(double latitutde) {
        this.latitutde = latitutde;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDateFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd mmm yyyy HH:mm:ss zzz", Locale.getDefault());
        return simpleDateFormat.format(timestamp);
    }

    public String getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(String facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof TipRecordPremium) {
            TipRecordPremium tipRecordPremium = (TipRecordPremium) obj;
            equal = this.tipId == tipRecordPremium.getTipId();
        }
        return equal;
    }
}
