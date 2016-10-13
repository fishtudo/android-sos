package api.server;

import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.Map;

import api.server.Connectors.Connector;

public class ServerRequest {

    private String path;
    private Map<String, String> restParams;
    private Map<String, String> queryParameters;

    public ServerRequest(String path, Map<String, String> restParams, Map<String, String> queryParameters) {
        this.path = path;
        this.restParams = restParams;
        this.queryParameters = queryParameters;
    }

    public AsyncResult<String> request() {
        try {
            String result = new Connector().connect(Config.getProtocol(),
                    Config.getHost(), Config.getPort(), Config.getContext() + path, Config.getBUFFER_SIZE(), restParams, queryParameters);
            return new AsyncResult<String>(result);
        } catch (MalformedURLException e) {
            Log.e("ServerConnector", "Malformed URL", e);
            return new AsyncResult<String>(AsyncResult.Error.MALFORMED_URL);
        } catch (ProtocolException e) {
            Log.e("ServerConnector", "Server not found", e);
            return new AsyncResult<String>(AsyncResult.Error.SERVER_NOT_FOUND);
        } catch (IOException e) {
            Log.e("ServerConnector", "IO Exception", e);
            return new AsyncResult<String>(AsyncResult.Error.IO_EXCEPTION);
        }
    }
}