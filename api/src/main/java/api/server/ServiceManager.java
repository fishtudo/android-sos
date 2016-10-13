package api.server;

import java.io.IOException;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;

import api.callback.Callback;
import api.parser.JsonParser;

public class ServiceManager<T> extends AsyncTask<Void, Void, AsyncResult<T>> {
    private ServerRequest mServerRequest;
    private JsonParser<T> mParser;
    private Callback<T> mCallback;
    private Class<T> mClassType = null;
    private TypeReference<T> mTypedReference = null;

    public ServiceManager(ServerRequest serverRequest, JsonParser<T> parser,
                          Callback<T> callback, Class<T> classType) {
        mServerRequest = serverRequest;
        mParser = parser;
        mCallback = callback;
        mClassType = classType;

    }

    public ServiceManager(ServerRequest serverRequest, JsonParser<T> parser,
                          Callback<T> callback, TypeReference<T> typedReference) {
        mServerRequest = serverRequest;
        mParser = parser;
        mCallback = callback;
        mTypedReference = typedReference;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AsyncResult<T> doInBackground(Void... params) {
        AsyncResult<String> json = mServerRequest.request();
        try {
            if (json.containsError() || mParser == null
                    || mClassType == Class.forName("java.lang.String"))
                return (AsyncResult<T>) json;
        } catch (ClassNotFoundException e) {
        }
        if (!json.containsError()) {
            try {
                String jsonResult = json.getResult();
                T result = parse(jsonResult);
                return new AsyncResult<T>(result);
            } catch (IOException exception) {
                return new AsyncResult<T>(AsyncResult.Error.PARSE_EXCEPTION);
            }
        }
        return new AsyncResult<T>(AsyncResult.Error.PARSE_EXCEPTION);
    }

    private T parse(String stringToParse) throws IOException {
        if (mClassType != null)
            return mParser.parse(stringToParse, mClassType);
        if (mTypedReference != null)
            return mParser.parse(stringToParse, mTypedReference);
        throw new IOException("No type to parse");
    }

    @Override
    protected void onPostExecute(AsyncResult<T> result) {
        super.onPostExecute(result);
        if (mCallback != null)
            mCallback.onFinishLoad(result);
    }
}