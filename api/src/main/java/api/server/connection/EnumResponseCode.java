package api.server.connection;

public enum EnumResponseCode {
	NONE(0), OK(200), INTERNAL_SERVER_ERROR(500);

	private int status;

	private EnumResponseCode(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public static EnumResponseCode getEnumResponseCode(int code) {
		if (code == EnumResponseCode.OK.getStatus()) {
			return EnumResponseCode.OK;
		}
		if (code == EnumResponseCode.INTERNAL_SERVER_ERROR.getStatus()) {
			return EnumResponseCode.INTERNAL_SERVER_ERROR;
		}
		return EnumResponseCode.NONE;
	}

}
