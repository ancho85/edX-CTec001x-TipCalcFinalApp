package edu.galileo.android.tipcalc;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.firebase.client.Firebase;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by carlos.gomez on 30/05/2016.
 */
public class TipCalcApp extends Application {
    private final static String ABOUT_URL = "http://www.facebook.com/ancho85";

    public String getAboutUrl() {
        return ABOUT_URL;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();
        initDB();
        initFirebase();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        destroyDB();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(this);
    }

    private void initDB() {
        FlowManager.init(this);
    }

    private void destroyDB() {
        FlowManager.destroy();
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
