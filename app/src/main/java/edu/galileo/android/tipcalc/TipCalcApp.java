package edu.galileo.android.tipcalc;

import android.app.Application;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.raizlabs.android.dbflow.config.FlowManager;

import edu.galileo.android.tipcalc.domain.FirebaseHelper;
import edu.galileo.android.tipcalc.libs.di.LibsModule;
import edu.galileo.android.tipcalc.tipcalc.ui.MainActivity;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.OnItemClickListener;
import edu.galileo.android.tipcalc.tipcalcpremium.history.di.DaggerHistoryComponent;
import edu.galileo.android.tipcalc.tipcalcpremium.history.di.HistoryComponent;
import edu.galileo.android.tipcalc.tipcalcpremium.history.di.HistoryModule;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryView;

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

    private void logoutFirebase(){
        FirebaseHelper.getInstance().signOff();
    }

    public void logoutFacebook() {
        logoutFirebase();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public HistoryComponent getHistoryComponent(Fragment fragment, HistoryView view, OnItemClickListener listener){
        return DaggerHistoryComponent
                .builder()
                .libsModule(new LibsModule(fragment))
                .historyModule(new HistoryModule(view, listener))
                .build();
    }
}
