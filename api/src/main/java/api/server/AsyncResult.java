package api.server;

public class AsyncResult<T> {
	public enum Error {
		SERVER_NOT_FOUND, IO_EXCEPTION, MALFORMED_URL, PARSE_EXCEPTION, SERVER_ERROR_MESSAGE;
	}

	private Error error;
	private T result;
	private String errorMessage = "";

	public AsyncResult(T result) {
		this.result = result;
	}

	public AsyncResult(Error error) {
		if (error == Error.SERVER_ERROR_MESSAGE) {
			/**
			 * Use the next Constructor
			 */
			throw new RuntimeException(
					"Server error Message should create Asyncresult with message.");
		}
		this.error = error;
	}

	public AsyncResult(Error error, String message) {
		this.error = error;
		setErrorMessage(message);
	}

	public boolean containsError() {
		return error != null;
	}

	public T getResult() {
		return result;
	}

	public Error getError() {
		return error;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
