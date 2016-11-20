package fishtudo.sos.loadingconfigurations;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.callback.Callback;
import api.location.LocationUtils;
import api.parser.JsonParser;
import api.server.AsyncResult;
import api.server.ServerRequest;
import api.server.ServiceManager;
import fishtudo.sos.Establishment;
import fishtudo.sos.R;
import fishtudo.sos.configs.Constants;
import fishtudo.sos.configs.Parameters;
import fishtudo.sos.configs.Path;

/**
 * Created by Carlos on 10/10/2016.
 */
public class LoadingServicesFragment extends Fragment implements LocationListener {

    private View rootView;

    private final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 12345;

    private LoadingServiceCallback loadingServiceCallback;

    private Location location = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.loadingfragment, container, false);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            return rootView;
        }
        requestLocation();
        configureLoadingLocationAnimation();

//        mock();
//        loadService(getMockLocation());
        return rootView;
    }

    private void configureLoadingLocationAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.searching_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if(location != null){
                    rootView.findViewById(R.id.searching_location).setAnimation(null);
                    rootView.findViewById(R.id.searching_location).setVisibility(View.GONE);
                    rootView.findViewById(R.id.you_are_in).setVisibility(View.VISIBLE);
                    ((TextView)rootView.findViewById(R.id.city_name)).setText(LocationUtils.getCityName(getActivity(), location));
                    rootView.findViewById(R.id.textView_searching).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.textView_searching).startAnimation(
                            AnimationUtils.loadAnimation(getActivity(), R.anim.searching_anim));
                }
            }
        });
        rootView.findViewById(R.id.searching_location).startAnimation(animation);
    }

    private void mock(){
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<Establishment> arrayList = new ArrayList();
                Location mockLocation = getRandomMockLocationNearby();
                arrayList.add(new Establishment("Hospital 1", "0619876-5432", mockLocation.getLatitude(), mockLocation.getLongitude()));
                mockLocation = getRandomMockLocationNearby();
                arrayList.add(new Establishment("Hospital 2", "0619988-7766", mockLocation.getLatitude(), mockLocation.getLongitude()));
                mockLocation = getRandomMockLocationNearby();
                arrayList.add(new Establishment("Hospital 3", "0619123-4567", mockLocation.getLatitude(), mockLocation.getLongitude()));
                getLoadingServiceCallback().onConfigurationsLoaded(getMockLocation(), arrayList);
            }
        }, 2000);
    }

    private Location getMockLocation(){
        return LocationUtils.createLocation(-15.8332002, -48.0458664);
    }
    private Location getRandomMockLocationNearby(){
        Location location = getMockLocation();
        if(Math.random()*2 % 2 == 0){
            location.setLatitude(location.getLatitude() + Math.random());
        } else{
            location.setLongitude(location.getLongitude() + Math.random());
        }
        return location;
    }

    private void requestLocation(){
        LocationManager locationManager =   ((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE));
        try{
            locationManager .requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 2000, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2000, this);
        } catch (SecurityException e){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation();
                }
            default:
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(getActivity() == null)
            return;

        this.location = location;
        loadService(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void loadService(final Location location) {
        Callback<ArrayList<Establishment>> callback = new Callback<ArrayList<Establishment>>() {
            @Override
            public void onFinishLoad(AsyncResult<ArrayList<Establishment>> asyncResult) {
                if(asyncResult.containsError()){
                    Toast.makeText(getActivity(), "Chegou um resultado com erro.", Toast.LENGTH_LONG).show();
                } else{
                    if(getLoadingServiceCallback() != null){
                        getLoadingServiceCallback().onConfigurationsLoaded(
                                location, asyncResult.getResult());
                    }
                }
            }
        };

        Map<String, String> parameters = new HashMap<>();
        parameters.put(Parameters.PARAM_LATITUDE, "" + location.getLatitude());
        parameters.put(Parameters.PARAM_LONGITUDE, "" + location.getLongitude());
        parameters.put(Parameters.PARAM_RADIUS, Constants.CONSTANT_VALUE_RADIUS);

        Map<String, String> queryParameter = new HashMap<>();
        queryParameter.put(Parameters.PARAM_QUANTITY, Constants.CONSTANT_VALUE_QUANTITY_OF_HOSPITALS);

        ServerRequest serverRequest = new ServerRequest(
                Path.LOAD_PLACES, parameters, queryParameter);

        ServiceManager<ArrayList<Establishment>> serviceManager = new ServiceManager<>(
                serverRequest, new JsonParser<ArrayList<Establishment>>(),
                callback, new TypeReference<ArrayList<Establishment>>() {});
        serviceManager.execute();
    }

    public LoadingServiceCallback getLoadingServiceCallback() {
        if(loadingServiceCallback == null){
            if(getActivity() instanceof LoadingServiceCallback){
                loadingServiceCallback = (LoadingServiceCallback)getActivity();
            }
        }
        return loadingServiceCallback;
    }

    public void setLoadingServiceCallback(LoadingServiceCallback loadingServiceCallback){
        this.loadingServiceCallback = loadingServiceCallback;
    }
}