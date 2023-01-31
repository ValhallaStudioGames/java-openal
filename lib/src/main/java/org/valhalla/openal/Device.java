package org.valhalla.openal;

import java.util.List;

import org.valhalla.openal.jna.ALC;
import org.valhalla.openal.jna.ALCdevice;
import org.valhalla.openal.jna.ALFactory;
import org.valhalla.openal.jna.Util;

import com.sun.jna.Pointer;

/**
 * A class representing an OpenAL device.<br>
 * A device can be compared to a playback device on a computer.
 */
public class Device {

	/**
	 * Link to the OpenALC interface.<br>
	 * The ALC interface should be the interface on which the Device exists.
	 */
	final ALC alc;

	/**
	 * The internal context on which all actions will be performed.
	 */
	final ALCdevice device;

	/**
	 * Has the device been closed?
	 * Any and all actions on a closed device will result in a {@link ALException}.
	 */
	private boolean closed = false;

	/**
	 * Will create a new Device. The playback device chosen will be the default/ preferred device by the OS.
	 * @param factory the factory that provides an OpenALC context
	 * @throws ALException when the device cannot be created
	 */
	public Device(ALFactory factory) throws ALException {
		this(factory, null);
	}

	/**
	 * Will create a new Device, on which sound can be played back.
	 * @param factory the factory that provides an OpenALC context
	 * @param name the name of the playback device that should be used
	 * @throws ALException when the device cannot be created
	 * @see #availableDevices(ALFactory)
	 */
	public Device(ALFactory factory, String name) throws ALException {
		alc = factory.alc;
		device = alc.alcOpenDevice(name);

		if (device == null) {
			throw new ALException("Failed to open ALC device " + name);
		}

		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	/**
	 * Closes the OpenALC device, this will happen automatically when the class is no longer needed.
	 * After a device has been closed, using the device will throw an {@link ALException}.
	 */
	public void close() {
		if (!closed) {
			alc.alcCloseDevice(device);
			closed = true;
		}
	}

	/**
	 * Get the default playback device. This is usually set by the OS.
	 * @param factory the factory that provides an OpenALC context
	 * @return the name of the default device
	 */
	public static String defaultDevice(ALFactory factory) {
		return Util.getString(factory.alc.alcGetString(null, ALC.ALC_DEFAULT_DEVICE_SPECIFIER));
	}

	/**
	 * Get the list of all devices that can be used to playback audio.
	 * @param factory the factory that provides an OpenALC context
	 * @return the names of the devices
	 */
	public static List<String> availableDevices(ALFactory factory) throws ALException {
		Pointer stringsPtr;
		if (factory.alc.alcIsExtensionPresent(null, "ALC_ENUMERATE_ALL_EXT")) {
			stringsPtr = factory.alc.alcGetString(null, ALC.ALC_ALL_DEVICES_SPECIFIER);
		} else {
			stringsPtr = factory.alc.alcGetString(null, ALC.ALC_DEVICE_SPECIFIER);
		}
		if (stringsPtr == null) {
			throw Util.createALCException(factory.alc, null);
		}
		return Util.getStrings(stringsPtr);
	}

	/**
	 * Checks if there has been any errors on the OpenALC device since last checking for errors.
	 * @throws ALException when there has been an error on the device.
	 */
	public void checkForError() throws ALException {
		Util.checkForALCError(alc, device);
	}

	/**
	 * Returns a String representation of the device
	 * @return a String with details about the device
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "Device[" + device + "]";
	}
}
