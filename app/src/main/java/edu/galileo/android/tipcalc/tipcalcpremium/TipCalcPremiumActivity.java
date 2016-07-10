package edu.galileo.android.tipcalc.tipcalcpremium;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.TipCalcApp;
import edu.galileo.android.tipcalc.tipcalcpremium.adapters.TipCalcPremiumPagerAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.ui.ExtraFragment;
import edu.galileo.android.tipcalc.tipcalcpremium.history.ui.HistoryFragment;

public class TipCalcPremiumActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc_premium);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupAdapter();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        TipCalcApp app = (TipCalcApp) getApplication();
        app.logoutFacebook();
    }
}
