package fishtudo.sos.loadingconfigurations;

import android.location.Location;

import java.util.ArrayList;

import fishtudo.sos.Establishment;

/**
 * Created by Carlos on 10/10/2016.
 */
public interface LoadingServiceCallback {

    void onConfigurationsLoaded(Location location, ArrayList<Establishment> establishments);

}
