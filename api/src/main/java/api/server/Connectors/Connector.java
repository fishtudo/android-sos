package api.server.Connectors;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import api.util.StringUtil;

/**
 *
 * @author charles
 */
public class Connector {

    private final int TIME_OUT = 120000;

    public String connect(String protocol, String host, int port, String file,
                          int BUFFER_SIZE, Map<String, String> restParams, Map<String, String> queryParameters) throws IOException {

        InputStream is = null;
        String strRestParameters = ConnectorUtils.buildParameters('/', '/', restParams);
        if (!StringUtil.isEmpty(strRestParameters)) {
            file += "/" + strRestParameters;
        }

        String strQueryParameters = ConnectorUtils.buildParameters('&', '=', queryParameters);
        if (!StringUtil.isEmpty(strQueryParameters)) {
            file += "?" + strQueryParameters;
        }

        try {
            URL url = new URL(protocol, host, port, file);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            connection.getResponseCode();
            is = connection.getInputStream();
            Reader reader = new InputStreamReader(is);
            String read = "";
            char[] buffer = new char[BUFFER_SIZE];
            do {
                for (int i = 0; i < BUFFER_SIZE; i++) {
                    buffer[i] = '*';
                }
                reader.read(buffer);
                read += new String(buffer).replace("*", "");
            } while (new String(buffer).replace("*", "") != "");
            return read;
        } finally {
            if (is != null)
                is.close();
        }
    }
}