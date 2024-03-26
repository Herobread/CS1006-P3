package rgou.view.exceptions;

public class InvalidSceneException extends RuntimeException {
	public InvalidSceneException() {
		super("Invalid scene specified.");
	}

	public InvalidSceneException(String message) {
		super(message);
	}

	public InvalidSceneException(String message, Throwable cause) {
		super(message, cause);
	}
}
