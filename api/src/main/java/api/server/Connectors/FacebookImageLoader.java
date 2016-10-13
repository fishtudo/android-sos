package api.server.Connectors;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import api.callback.Callback;
import api.server.AsyncResult;
import api.server.Config;
import api.util.PictureUtils;
import api.util.StringUtil;

/**
 * Created by charles on 10/10/2015.
 */
public class FacebookImageLoader extends AsyncTask<Void, Void, AsyncResult<String>> {

    private String id;

    private Callback<String> callback;

    public FacebookImageLoader(String id, Callback<String> callback) {
        this.id = id;
        this.callback = callback;
    }

    @Override
    protected AsyncResult<String> doInBackground(Void... params) {

        try {
            URL img_value = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
            return new AsyncResult<String>(PictureUtils.encodeTobase64(
                    BitmapFactory.decodeStream(img_value.openConnection().getInputStream())));
        } catch (IOException e) {
        }
        return new AsyncResult<String>(AsyncResult.Error.IO_EXCEPTION);
    }

    @Override
    protected void onPostExecute(AsyncResult<String> result) {
        super.onPostExecute(result);
        if (callback != null) {
            callback.onFinishLoad(result);
        }
    }
}