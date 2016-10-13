package api.callback;

import api.server.AsyncResult;

public interface Callback<T> {
	public void onFinishLoad(AsyncResult<T> asyncResult);
}
