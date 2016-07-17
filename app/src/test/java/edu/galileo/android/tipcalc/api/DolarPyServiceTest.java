package edu.galileo.android.tipcalc.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import edu.galileo.android.tipcalc.BaseTest;
import edu.galileo.android.tipcalc.BuildConfig;
import edu.galileo.android.tipcalc.entities.DolarPy;
import edu.galileo.android.tipcalc.tipcalc.ui.MainActivity;
import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DolarPyServiceTest extends BaseTest {
    private DolarPyService service;
    private MainActivity activity;
    private ActivityController<MainActivity> controller;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = new MainActivity();
        controller = ActivityController.of(Robolectric.getShadowsAdapter(), activity).create().visible();
        activity = controller.get();
        DolarPyClient client = new DolarPyClient(activity.getApplicationContext());
        service = client.getDolarPyService();
    }

    @Test
    public void testGetCurrency() throws Exception {
        Call<DolarPyResponse> call = service.getCurrencyRates();

        Response<DolarPyResponse> response = call.execute();
        assertTrue(response.isSuccess());

        DolarPyResponse dolarPyResponse = response.body();
        assertEquals(1, dolarPyResponse.getCount());

        DolarPy dolarPy = dolarPyResponse.getFirstDolarPy();
        assertNotNull(dolarPy);
    }
}
