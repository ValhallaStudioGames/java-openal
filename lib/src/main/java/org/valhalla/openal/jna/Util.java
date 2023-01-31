package org.valhalla.openal.jna;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.valhalla.openal.ALException;

import com.sun.jna.Pointer;

/**
 * Class with static utility functions to convert OpenAL types into Java.
 */
public class Util {

	/**
	 * Converts a pointer to a c-styled string in memory to a Java type String
	 * @param pointer first character of the string
	 * @return the referenced string as a Java string
	 */
	public static String getString(Pointer pointer) {
		if (pointer == null) {
			return null;
		}
		return pointer.getString(0);
	}

	/**
	 * Converts a pointer to a list of c-styled strings to a List of Java Strings.
	 * @param pointerToStrings c-styled pointer to the list of strings
	 * @return a list of Java strings
	 */
	public static List<String> getStrings(Pointer pointerToStrings) {
		List<String> result = new LinkedList<>();
		int offset = 0;
		String current = pointerToStrings.getString(offset);
		while (current.length() > 0) {
			result.add(current);
			offset += current.length() + 1;
			current = pointerToStrings.getString(offset);
		}
		return result;
	}

	/**
	 * Reads the most recent error and ignores the result
	 * @param al the OpenAL interface to check
	 */
	public static void clearErrors(AL al) {
		al.alGetError();
	}

	/**
	 * Check there has been an OpenAL error since last checking for an error.
	 * @param al the OpenAL interface to check
	 * @throws ALException when there has been an OpenAL error
	 */
	public static void checkForALError(AL al) throws ALException {
		int errorCode = al.alGetError();
		if (errorCode != AL.AL_NO_ERROR) {
			throw createALException(al, errorCode);
		}

	}

	/**
	 * Creates a new OpenAL Exception by checking for the error code when making a new exception.
	 * @param al the OpenAL interface to check
	 * @return a new OpenAL Exception
	 */
	public static ALException createALException(AL al) {
		return createALException(al, al.alGetError());
	}

	/**
	 * Creates a new OpenAL Exception.
	 * @param al the OpenAL interface to check
	 * @param errorCode the thrown OpenAL error code
	 * @return a new OpenAL Exception
	 */
	private static ALException createALException(AL al, int errorCode) {
		return new ALException("AL Error " + String.format("0x%x", errorCode) + ": "
				+ getString(al.alGetString(errorCode)));
	}

	/**
	 * Check there has been an OpenAL error since last checking for an error.
	 * @param alc the OpenALC interface to check
	 * @param device the device that the error might have occurred on
	 * @throws ALException when there has been an OpenALC error
	 */
	public static void checkForALCError(ALC alc, ALCdevice device) throws ALException {
		int errorCode = alc.alcGetError(device);
		if (errorCode != ALC.ALC_NO_ERROR) {
			throw createALCException(alc, device, errorCode);
		}
	}

	/**
	 * Creates a new OpenALC Exception by checking for the error code when making a new exception.
	 * @param alc the OpenALC interface to check
	 * @param device the device that the error occurred on
	 * @return a new OpenAL Exception
	 */
	public static ALException createALCException(ALC alc, ALCdevice device) {
		return createALCException(alc, device, alc.alcGetError(device));
	}

	/**
	 * Creates a new OpenALC Exception bases on an OpenALC error code
	 * @param alc the OpenALC interface to check
	 * @param device the device that the error occurred on
	 * @param errorCode the thrown OpenALC error code
	 * @return a new OpenAL Exception
	 */
	private static ALException createALCException(ALC alc, ALCdevice device, int errorCode) {
		return new ALException("ALC Error " + String.format("%x", errorCode) + ": "
				+ getString(alc.alcGetString(device, errorCode)));
	}

	/**
	 * Reads the contents of an InputStream and turns them into a byte array.
	 * @param input an input stream
	 * @return a byte-array based representation of the input stream
	 * @throws IOException when the InputSteam cannot be read
	 */
	public static byte[] readStreamContents(InputStream input) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n;
		while (-1 != (n = input.read(buffer))) {
			result.write(buffer, 0, n);
		}
		return result.toByteArray();
	}
}
