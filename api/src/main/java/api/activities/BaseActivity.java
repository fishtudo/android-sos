package api.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {

    private static Map<Integer, ActivityResultObserver> activityResultObservers;

    public void addActivityResultObserver(int requestCode,
                                          ActivityResultObserver activityResultObserver) {
        if (activityResultObservers == null) {
            activityResultObservers = new HashMap<Integer, ActivityResultObserver>();
        }
        activityResultObservers.put(requestCode, activityResultObserver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultObservers == null
                || !activityResultObservers.containsKey(requestCode))
            return;

        activityResultObservers.get(requestCode).onActivityResult(requestCode,
                resultCode, data);

    }
}
