package edu.galileo.android.tipcalc.api.models;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public class MoneyExchange {
    private Cambiosmaxi maxicambios;
    private Cambiosalberdi cambiosalberdi;
    private Cambioschaco cambioschaco;
    private Cambiosbcp bcp;

    public Cambiosbcp getbcp() {
        return bcp;
    }

    public void setbcp(Cambiosbcp bcp) {
        this.bcp = bcp;
    }

    public Cambiosmaxi getMaxicambios() {
        return maxicambios;
    }

    public void setMaxicambios(Cambiosmaxi maxicambios) {
        this.maxicambios = maxicambios;
    }

    public Cambiosalberdi getCambiosalberdi() {
        return cambiosalberdi;
    }

    public void setCambiosalberdi(Cambiosalberdi cambiosalberdi) {
        this.cambiosalberdi = cambiosalberdi;
    }

    public Cambioschaco getCambioschaco() {
        return cambioschaco;
    }

    public void setCambioschaco(Cambioschaco cambioschaco) {
        this.cambioschaco = cambioschaco;
    }
}
