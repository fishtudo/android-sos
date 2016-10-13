package fishtudo.sos;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import fishtudo.sos.configs.ConfigurationLoader;
import fishtudo.sos.loadingconfigurations.LoadingServiceCallback;
import fishtudo.sos.loadingconfigurations.LoadingServicesFragment;
import fishtudo.sos.mainfragment.MainFragment;

public class MainActivity extends AppCompatActivity implements LoadingServiceCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ConfigurationLoader().loadServerConfigurations();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.centralfragment, new LoadingServicesFragment()).commit();
    }

    @Override
    public void onConfigurationsLoaded(Location location, ArrayList<Establishment> establishments) {
        MainFragment mainFragment = MainFragment.createInstance(location, establishments);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .replace(R.id.centralfragment, mainFragment)
                .commit();
    }
}