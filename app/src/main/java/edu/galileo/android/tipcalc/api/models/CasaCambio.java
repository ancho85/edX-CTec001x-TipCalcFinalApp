package edu.galileo.android.tipcalc.api.models;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public abstract class CasaCambio {
    private String compra;
    private String venta;
    private String referencial_diario;

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getReferencial_diario() {
        return referencial_diario;
    }

    public void setReferencial_diario(String referencial_diario) {
        this.referencial_diario = referencial_diario;
    }
}