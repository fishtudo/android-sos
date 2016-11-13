package fishtudo.sos;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import fishtudo.sos.configs.ConfigurationLoader;
import fishtudo.sos.detailsfragment.DetailsFragment;
import fishtudo.sos.loadingconfigurations.LoadingServiceCallback;
import fishtudo.sos.loadingconfigurations.LoadingServicesFragment;
import fishtudo.sos.mainfragment.EstablishmentSelectListener;
import fishtudo.sos.mainfragment.MainFragment;

public class MainActivity extends AppCompatActivity implements LoadingServiceCallback, EstablishmentSelectListener {

    private Fragment currentFragment;

    private boolean active = false;

    private ArrayList<Establishment> establishments;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new ConfigurationLoader().loadServerConfigurations();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new LoadingServicesFragment());
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
        if(location != null && establishments !=null){
            addFragmentWithAnimations(
                    MainFragment.createInstance(location, establishments), R.anim.anim1_enter, R.anim.anim1_exit, false);
        }
    }

    private void addFragment(Fragment fragment){
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.centralfragment, fragment).commit();
    }

    private void addFragmentWithAnimations(Fragment fragment, int enter, int exit, boolean keepLastFragment){
        currentFragment = fragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enter, exit)
                .replace(R.id.centralfragment, fragment);
        if(keepLastFragment){
            fragmentTransaction.addToBackStack("backstack");
        }
        fragmentTransaction .commit();
    }

    private void addFragmentWithAnimations(Fragment fragment, int enter, int exit, int reEnter, int reExit){
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enter, exit, reEnter, reExit)
                .replace(R.id.centralfragment, fragment).addToBackStack("backstack").commit();
    }

    @Override
    public void onConfigurationsLoaded(Location location, ArrayList<Establishment> establishments) {
        this.location = location;
        this.establishments = establishments;
        if(active){
            addFragmentWithAnimations(MainFragment.createInstance(location, establishments), R.anim.anim1_enter,
                    R.anim.anim1_exit, false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onEstablishmentSelected(Establishment establishment) {
        addFragmentWithAnimations(DetailsFragment.createInstance(establishment),
                R.anim.anim2_enter, R.anim.anim2_exit, R.anim.anim3_enter, R.anim.anim3_exit);
    }
}