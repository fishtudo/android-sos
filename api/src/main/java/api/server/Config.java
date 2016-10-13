package api.server;

public class Config {
	private static String host;
	private static int port;
	private static String protocol;
	private static int BUFFER_SIZE = 4096;
    private static String context;

	public static void setConfigurations(String host, int port,
			String protocol, String context) {
		Config.host = host;
		Config.port = port;
		Config.protocol = protocol;
		Config.context = context;
	}

	public static String getHost() {
		return host;
	}

	public static int getPort() {
		return port;
	}

	public static String getProtocol() {
		return protocol;
	}

	public static String getContext() {
		return context;
	}

	public static int getBUFFER_SIZE() {
		return BUFFER_SIZE;
	}
}