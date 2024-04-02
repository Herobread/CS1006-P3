package rgou.model.remote;

public class RemoteConfig {
	private String hostname;
	private String port;
	private RemoteTypes remoteType;

	public RemoteConfig(String hostname, String port, RemoteTypes remoteType) {
		this.hostname = hostname;
		this.port = port;
		this.remoteType = remoteType;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public RemoteTypes getRemoteType() {
		return remoteType;
	}

	public void setRemoteType(RemoteTypes remoteType) {
		this.remoteType = remoteType;
	}
}
