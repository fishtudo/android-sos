package api.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by charles on 30/08/2015.
 */
public class LocationUtils {

    public static Location createLocation(double latitude, double longitude) {
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static String formmatDistance(double distance){
        if(distance > 1000)
            return formmatDistanceNumber(distance / 1000) + "km";

        return formmatDistanceNumber(distance) + "m";
    }

    private static String formmatDistanceNumber(double distance){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(distance);
    }

    public static double getLatitude(Location location) {
        return location.getLatitude();
    }

    public static double getLongitude(Location location) {
        return location.getLongitude();
    }

    public static String getCityName(Context activity, Location location) {
        String cityName = null;
        Geocoder gcd = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(getLatitude(location), getLongitude(location), 1);
            if (addresses.size() > 0)
                return addresses.get(0).getLocality();
        } catch (IOException e) {
            Log.e("", e.getMessage());
        }
        return "";
    }
}
