package api.server.connection;

public class ServerErrorMessageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumResponseCode errorCode;
	private String errorMessage;

	public ServerErrorMessageException(EnumResponseCode errorCode,
			String errorMessage) {
		setErrorCode(errorCode);
		setErrorMessage(errorMessage);
	}

	public EnumResponseCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(EnumResponseCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
