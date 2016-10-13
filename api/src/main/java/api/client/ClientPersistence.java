package api.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;

import api.parser.JsonFactory;
import api.parser.JsonParser;

/**
 * Created by charles on 25/09/2015.
 */
public class ClientPersistence {

    private static String CLIENT_DATA = "client data parameter";

    public static void saveClient(Context context, String jSonClient) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CLIENT_DATA, jSonClient);
        editor.commit();
        editor.apply();
    }
    public static void saveClient(Context context, Client client) throws IOException {
        saveClient(context, new JsonFactory<Client>().objectToJson(client));
    }

    public static Client convertJsonToClient(String json) throws IOException {
        return new JsonParser<Client>().parse(json, Client.class);
    }

    public static Client getClient(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String jSonClient = prefs.getString(CLIENT_DATA, "");

        try {
            return convertJsonToClient(jSonClient);
        } catch (IOException e) {
        }
        return null;
    }
}
