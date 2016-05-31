package edu.galileo.android.tipcalc;

import android.app.Application;

/**
 * Created by carlos.gomez on 30/05/2016.
 */
public class TipCalcApp extends Application {
    private final static String ABOUT_URL = "http://www.facebook.com/ancho85";

    public String getAboutUrl(){
        return ABOUT_URL;
    }
}
