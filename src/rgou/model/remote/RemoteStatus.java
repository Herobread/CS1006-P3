package rgou.model.remote;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RemoteStatus {
	private List<ChangeListener> listeners = new ArrayList<>();

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	public List<ChangeListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<ChangeListener> listeners) {
		this.listeners.clear();
		this.listeners.addAll(listeners);
	}

	protected void notifyChange() {
		for (ChangeListener listener : listeners) {
			listener.stateChanged(new ChangeEvent(this));
		}
	}

	//////////

	private String status = "not connected";
	private RemoteConfig remoteConfig;

	public RemoteStatus(RemoteConfig remoteConfig) {
		this.remoteConfig = remoteConfig;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		notifyChange();
	}

	public RemoteConfig getRemoteConfig() {
		return remoteConfig;
	}

	public void setRemoteConfig(RemoteConfig remoteConfig) {
		this.remoteConfig = remoteConfig;
		notifyChange();
	}
}
