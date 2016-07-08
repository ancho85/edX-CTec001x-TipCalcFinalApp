package edu.galileo.android.tipcalc.entities;

import java.util.Date;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class DolarPy {
    private String entity;
    private long purchase;
    private long sale;
    private long daily_ref = 0L;
    private Date last_update;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public long getPurchase() {
        return purchase;
    }

    public void setPurchase(long purchase) {
        this.purchase = purchase;
    }

    public long getSale() {
        return sale;
    }

    public void setSale(long sale) {
        this.sale = sale;
    }

    public long getDaily_ref() {
        return daily_ref;
    }

    public void setDaily_ref(long daily_ref) {
        this.daily_ref = daily_ref;
    }


    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public boolean isBCP(){
        boolean itIs = false;
        if (this.entity.equals("bcp")){
            itIs = true;
        }
        return itIs;
    }
}
