package api.callback;

import api.server.AsyncResult;

public interface Callback<T> {
	void onFinishLoad(AsyncResult<T> asyncResult);
}
