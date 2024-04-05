package rgou.view.exceptions;

/**
 * Exception thrown when attempting to load or access an invalid scene.
 */
public class InvalidSceneException extends RuntimeException {
	/**
	 * Constructs an InvalidSceneException with a default message.
	 */
	public InvalidSceneException() {
		super("Invalid scene specified.");
	}

	/**
	 * Constructs an InvalidSceneException with a specified message.
	 *
	 * @param message The detail message.
	 */
	public InvalidSceneException(String message) {
		super(message);
	}

	/**
	 * Constructs an InvalidSceneException with a specified message and cause.
	 *
	 * @param message The detail message.
	 * @param cause   The cause.
	 */
	public InvalidSceneException(String message, Throwable cause) {
		super(message, cause);
	}
}
