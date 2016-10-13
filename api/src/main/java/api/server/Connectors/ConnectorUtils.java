package api.server.Connectors;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by charles on 09/11/2015.
 */
public class ConnectorUtils {

    public static char concat = '/';

    public static char equal = '/';

    public static String buildParameters(Map<String, String> parameters) throws UnsupportedEncodingException{
        return buildParameters(concat, equal, parameters);
    }

    public static String buildParameters(char concat, char equal, Map<String, String> parameters)
            throws UnsupportedEncodingException {
        if (parameters == null) {
            return "";
        }
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            if (postData.length() != 0)
                postData.append(concat);
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append(equal);
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
                    "UTF-8"));
        }
        return postData.toString();
    }

}
