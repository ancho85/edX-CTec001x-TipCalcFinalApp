package edu.galileo.android.tipcalc.api;

import java.util.Date;

import edu.galileo.android.tipcalc.api.models.MoneyExchange;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class DolarPyResponse {
        private MoneyExchange dolarpy;

        private Date updated;

        public MoneyExchange getdolarpy() {
            return dolarpy;
        }

        public void setdolarpy(MoneyExchange dolarpy) {
            this.dolarpy = dolarpy;
        }

        public Date getUpdated() {
            return updated;
        }

        public void setUpdated(Date updated) {
            this.updated = updated;
        }
    }
