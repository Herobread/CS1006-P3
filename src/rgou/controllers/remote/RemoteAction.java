package rgou.controllers.remote;

import java.util.Arrays;

public class RemoteAction {
	private String unparsedAction;
	private String action;
	private int[] metadata;

	public RemoteAction(String unparsed) {
		System.out.println("Creating RemoteAction object with unparsed data: " + unparsed);

		String[] items = unparsed.split(",");

		if (items.length != 2) {
			throw new IllegalArgumentException("Unparsable data: " + unparsed);
		}

		action = items[0];
		System.out.println("Action set to: " + action);

		String[] metadataItems = items[1].split("\\|");
		System.out.println("Metadata items count: " + metadataItems.length);

		metadata = new int[metadataItems.length];

		for (int i = 0; i < metadataItems.length; i++) {
			metadata[i] = Integer.parseInt(metadataItems[i]);
			System.out.println("Metadata[" + i + "] set to: " + metadata[i]);
		}

		System.out.println(this);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int[] getMetadata() {
		return metadata;
	}

	public void setMetadata(int[] metadata) {
		this.metadata = metadata;
	}

	public String getUnparsedAction() {
		return unparsedAction;
	}

	public void setUnparsedAction(String unparsedAction) {
		this.unparsedAction = unparsedAction;
	}

	@Override
	public String toString() {
		return "RemoteAction [unparsedAction=" + unparsedAction + ", action=" + action + ", metadata="
				+ Arrays.toString(metadata) + "]";
	}

}
