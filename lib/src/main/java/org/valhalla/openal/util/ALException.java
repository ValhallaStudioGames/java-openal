package org.valhalla.openal.util;

/**
 * Error class representing an OpenAL error.
 * This exception will get thrown if there has been an OpenAL or OpenALC error.
 */
public class ALException extends Exception {

	/**
	 * Creates a new ALException with an empty error message and no cause
	 * @see #ALException(String)
	 * @see #ALException(String, Throwable)
	 */
	public ALException() {
		super();
	}

	/**
	 * Creates a new ALException
	 * @param message the message that indicates what went wrong
	 * @param cause the cause of the exception
	 * @see #ALException(String)
	 */
	public ALException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new ALException without a cause
	 * @param message the message that indicates what went wrong
	 * @see #ALException(String, Throwable)
	 */
	public ALException(String message) {
		super(message);
	}

	/**
	 * Creates a new ALException without a message
	 * @param cause the cause of the exception
	 * @see #ALException(String)
	 * @see #ALException(String, Throwable)
	 */
	public ALException(Throwable cause) {
		super(cause);
	}
}
