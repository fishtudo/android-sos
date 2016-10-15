package fishtudo.sos;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import fishtudo.sos.configs.ConfigurationLoader;
import fishtudo.sos.loadingconfigurations.LoadingServiceCallback;
import fishtudo.sos.loadingconfigurations.LoadingServicesFragment;
import fishtudo.sos.mainfragment.MainFragment;

public class MainActivity extends AppCompatActivity implements LoadingServiceCallback {

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ConfigurationLoader().loadServerConfigurations();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new LoadingServicesFragment());
    }

    private void addFragment(Fragment fragment){
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.centralfragment, fragment).commit();
    }

    private void addFragmentWithAnimations(Fragment fragment, int enter, int exit){
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enter, exit)
                .replace(R.id.centralfragment, fragment).commit();
    }

    @Override
    public void onConfigurationsLoaded(Location location, ArrayList<Establishment> establishments) {
        addFragmentWithAnimations(MainFragment.createInstance(location, establishments), R.anim.enter, R.anim.exit);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}