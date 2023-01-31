package org.valhalla.openal;

import org.valhalla.openal.jna.ALC;
import org.valhalla.openal.jna.ALCcontext;

/**
 * A class representing an OpenAL context.<br>
 * A context represents a sound interface on a physical device.
 */
public class Context {

	/**
	 * Link to the OpenALC interface.<br>
	 * The ALC interface should be the interface on which the Context exists.
	 */
	final ALC alc;

	/**
	 * The internal context on which all actions will be performed.
	 */
	private final ALCcontext context;

	/**
	 * Has the context been closed?
	 * Any and all actions on a closed context will result in a {@link ALException}.
	 */
	private boolean closed = false;

	/**
	 * Creates a new OpenAL context on a specific device.<br>
	 * This device will be opened and set as the current context to play sounds on.
	 * @param device the device for which the context will be created
	 * @throws ALException when an error creating or making the context current occurs
	 */
	public Context(Device device) throws ALException {
		alc = device.alc;
		context = alc.alcCreateContext(device.device, null);

		if (context == null) {
			throw new ALException("Could not create context");
		}

		if (!alc.alcMakeContextCurrent(context)) {
			alc.alcDestroyContext(context);
			throw new ALException("Could not make context current");
		}
		device.checkForError();

		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	/**
	 * Closes the OpenALC context, this will happen automatically when the class is no longer needed.
	 * After a context has been closed, using the context will throw an {@link ALException}.
	 */
	public void close() {
		if (!closed) {
			alc.alcDestroyContext(context);
			closed = true;
		}
	}

	/**
	 * Returns a String representation of the context
	 * @return a String with details about the context
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "Context[" + context + "]";
	}
}
