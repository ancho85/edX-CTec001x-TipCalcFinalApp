package edu.galileo.android.tipcalc.tipcalcpremium;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.TipCalcApp;
import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.tipcalcpremium.adapters.TipCalcPremiumPagerAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.ui.ExtraFragment;
import edu.galileo.android.tipcalc.tipcalcpremium.history.ui.HistoryFragment;

public class TipCalcPremiumActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
    @Bind(R.id.btnIncrease)
    Button btnIncrease;
    @Bind(R.id.btnDecrease)
    Button btnDecrease;
    @Bind(R.id.separator)
    View separator;
    @Bind(R.id.txtTip)
    TextView txtTip;
    @Bind(R.id.container)
    ViewPager container;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;

    private Location lastLocation;
    private GoogleApiClient googleApiClient;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    private static final int TIP_STEP_CHANGE = 1;
    private static final int DEFAULT_TIP_PERCENTAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc_premium);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupAdapter();
        setupGoogleAPIClient();
    }

    private void setupAdapter() {
        Fragment[] fragments = new Fragment[]{new HistoryFragment(), new ExtraFragment()};
        String[] titles = new String[]{
                getString(R.string.tipcalcpremium_header_history),
                getString(R.string.tipcalcpremium_header_extra)};
        TipCalcPremiumPagerAdapter adapter =
                new TipCalcPremiumPagerAdapter(getSupportFragmentManager(),
                        titles, fragments);
        container.setAdapter(adapter);
        tabs.setupWithViewPager(container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tipcalcpremium_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
        }else if (id == R.id.action_refresh){
            refreshHistory();
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshHistory() {
        TipCalcPremiumPagerAdapter adapter = (TipCalcPremiumPagerAdapter) container.getAdapter();
        HistoryFragment fragment = (HistoryFragment) adapter.getItem(0);
        fragment.tipShow();
    }

    private void logout() {
        TipCalcApp app = (TipCalcApp) getApplication();
        app.logoutFacebook();
    }

    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit() {
        hideKeyboard();
        String strInputTotal = inputBill.getText().toString().trim();
        if (!strInputTotal.isEmpty()) {
            double total = Double.parseDouble(strInputTotal);
            int tipPercentage = getTipPercentage();

            TipRecordPremium tipRecordPremium = new TipRecordPremium();
            tipRecordPremium.setBill(total);
            tipRecordPremium.setTipPercentage(tipPercentage);
            Date currentDate = new Date();
            tipRecordPremium.setTimestamp(currentDate);
            tipRecordPremium.setTipId(currentDate.getTime()); // unique always
            if (lastLocation != null) { //may haven't granted permissions
                tipRecordPremium.setLatitutde(lastLocation.getLatitude());
                tipRecordPremium.setLongitude(lastLocation.getLongitude());
            }
            String strTip = String.format(getString(R.string.global_message_tip),
                    tipRecordPremium.getTip());
            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);

            TipCalcPremiumPagerAdapter adapter = (TipCalcPremiumPagerAdapter) container.getAdapter();
            HistoryFragment fragment = (HistoryFragment) adapter.getItem(0);
            fragment.addToHistory(tipRecordPremium);
        }
    }

    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease() {
        hideKeyboard();
        handleTipChange(TIP_STEP_CHANGE);

    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease() {
        hideKeyboard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    private void handleTipChange(int change) {
        int tipPercentage = getTipPercentage();
        tipPercentage += change;
        if (tipPercentage > 0) {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
    }

    private int getTipPercentage() {
        int tipPercentage = DEFAULT_TIP_PERCENTAGE;
        String strInputTipPercentage = inputPercentage.getText().toString().trim();
        if (!strInputTipPercentage.isEmpty()) {
            tipPercentage = Integer.parseInt(strInputTipPercentage);
        } else {
            inputPercentage.setText(String.valueOf(String.valueOf(tipPercentage)));
        }
        return tipPercentage;
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }

    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void setupGoogleAPIClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        String errorMsg = connectionResult.getErrorMessage() == null ? "Google API connection failed" : connectionResult.getErrorMessage();
        Snackbar.make(mainContent, errorMsg, Snackbar.LENGTH_SHORT).show();
    }
}
