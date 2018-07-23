package com.leadinsource.prudentcook;

import android.test.InstrumentationTestCase;

import com.leadinsource.prudentcook.model.Ingredient;
import com.leadinsource.prudentcook.model.IngredientImpl;
import com.leadinsource.prudentcook.net.WebService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class EdamamWebServiceTest  extends InstrumentationTestCase {

    String result;
    private CountDownLatch signal;

    public void testSimpleSearch() throws InterruptedException {
        signal = new CountDownLatch(1);

        try {
            runTestOnUiThread(new TestRun());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        signal.await(10, TimeUnit.SECONDS);
        assertTrue(result!=null);
        assertTrue(result.length()>0);
    }

    class TestRun implements Runnable, WebService.Callback {

        @Override
        public void run() {
            WebService webService = new com.leadinsource.prudentcook.net.EdamamWebService();
            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(new IngredientImpl("Chicken", "2kg of chicken"));
            webService.searchAPI(ingredients, this);
        }

        @Override
        public void onResult(String result) {
            Timber.d("Countdown with %s", result);
            EdamamWebServiceTest.this.result = result;
            signal.countDown();
        }
    }
}
