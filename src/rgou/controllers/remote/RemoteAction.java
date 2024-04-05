package rgou.controllers.remote;

import java.util.Arrays;

/**
 * Represents an action received from a remote source.
 */
public class RemoteAction {
	private String unparsedAction;
	private String action;
	private int[] metadata;

	/**
	 * Initializes a RemoteAction object with the given unparsed action string.
	 * 
	 * @param unparsed The unparsed action string.
	 * @throws IllegalArgumentException if the data cannot be parsed.
	 */
	public RemoteAction(String unparsed) {
		String[] items = unparsed.split(",");

		if (items.length != 2) {
			throw new IllegalArgumentException("Unparsable data: " + unparsed);
		}

		action = items[0];

		String[] metadataItems = items[1].split("\\|");

		metadata = new int[metadataItems.length];

		for (int i = 0; i < metadataItems.length; i++) {
			metadata[i] = Integer.parseInt(metadataItems[i]);
		}
	}

	/**
	 * Retrieves the action.
	 * 
	 * @return The action.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 * 
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Retrieves the metadata associated with the action.
	 * 
	 * @return The metadata.
	 */
	public int[] getMetadata() {
		return metadata;
	}

	/**
	 * Sets the metadata associated with the action.
	 * 
	 * @param metadata The metadata to set.
	 */
	public void setMetadata(int[] metadata) {
		this.metadata = metadata;
	}

	/**
	 * Retrieves the unparsed action string.
	 * 
	 * @return The unparsed action string.
	 */
	public String getUnparsedAction() {
		return unparsedAction;
	}

	/**
	 * Sets the unparsed action string.
	 * 
	 * @param unparsedAction The unparsed action string to set.
	 */
	public void setUnparsedAction(String unparsedAction) {
		this.unparsedAction = unparsedAction;
	}

	@Override
	public String toString() {
		return "RemoteAction [unparsedAction=" + unparsedAction + ", action=" + action + ", metadata="
				+ Arrays.toString(metadata) + "]";
	}

}
