package api.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

/**
 * Created by charles on 23/09/2015.
 */
public class FirstAccessManager {

    private String FIRST_ACCESS_PARAMETER = "first access parameter";

    private Context context;

    public FirstAccessManager(Context context) {
        this.context = context;
    }

    public void saveFirstAccessCompleted() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_ACCESS_PARAMETER, true);
        editor.commit();
        editor.apply();
    }
    public void cancelFirstAccess() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(FIRST_ACCESS_PARAMETER, false);
        editor.commit();
        editor.apply();
    }

    public boolean isFirstAccessCompleted() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(FIRST_ACCESS_PARAMETER, false);
    }
}
