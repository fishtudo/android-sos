package api.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import api.activities.BaseActivity;

/**
 * Created by charles on 20/09/2015.
 */
public abstract class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, getActivityToStart()));
                finish();
            }
        }, getDurationInMs());
    }

    protected abstract int getDurationInMs();

    protected abstract Class getActivityToStart();

    public abstract int getContentViewId();
}
