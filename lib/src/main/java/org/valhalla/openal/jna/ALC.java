package org.valhalla.openal.jna;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * interface that defines all the functions and variables present in "al/alc.h"<br>
 * <br>
 * jna will translate these functions into calls to the native OpenAL sound system.
 */
public interface ALC extends Library {

	/** Context attribute: &lt;int&gt; Hz. */
	public final static int ALC_FREQUENCY = 0x1007;

	/** Context attribute: &lt;int&gt; Hz. */
	public final static int ALC_REFRESH = 0x1008;

	/** Context attribute: AL_TRUE or AL_FALSE synchronous context. */
	public final static int ALC_SYNC = 0x1009;

	/** Context attribute: &lt;int&gt; requested Mono (3D) Sources. */
	public final static int ALC_MONO_SOURCES = 0x1010;

	/** Context attribute: &lt;int&gt; requested Stereo Sources. */
	public final static int ALC_STEREO_SOURCES = 0x1011;

	/*
	 * errors
	 */

	/** Errors: No error. */
	public final static int ALC_NO_ERROR = 0;

	/** Errors: Invalid device handle. */
	public final static int ALC_INVALID_DEVICE = 0xA001;

	/** Errors: Invalid context handle. */
	public final static int ALC_INVALID_CONTEXT = 0xA002;

	/** Errors: Invalid enum parameter passed to an ALC call. */
	public final static int ALC_INVALID_ENUM = 0xA003;

	/** Errors: Invalid value parameter passed to an ALC call. */
	public final static int ALC_INVALID_VALUE = 0xA004;

	/** Errors: Out of memory. */
	public final static int ALC_OUT_OF_MEMORY = 0xA005;

	/*
	 * The Specifier string for default device
	 */

	/** Runtime ALC major version. */
	public final static int ALC_MAJOR_VERSION = 0x1000;

	/** Runtime ALC minor version. */
	public final static int ALC_MINOR_VERSION = 0x1001;

	/** Context attribute list size. */
	public final static int ALC_ATTRIBUTES_SIZE = 0x1002;

	/** Context attribute list properties. */
	public final static int ALC_ALL_ATTRIBUTES = 0x1003;

	/** String for the default device specifier. */
	public final static int ALC_DEFAULT_DEVICE_SPECIFIER = 0x1004;

	/**
	 * String for the given device's specifier.<br>
	 * <br>
	 * If device handle is NULL, it is instead a null-char separated list of
	 * strings of known device specifiers (list ends with an empty string).
	 */
	public final static int ALC_DEVICE_SPECIFIER = 0x1005;

	/** String for space-separated list of ALC extensions. */
	public final static int ALC_EXTENSIONS = 0x1006;

	/*
	 * Capture extension
	 */

	/** Capture extension */
	public final static int ALC_EXT_CAPTURE = 1;

	/**
	 * String for the given capture device's specifier.<br>
	 * <br>
	 * If device handle is NULL, it is instead a null-char separated list of
	 * strings of known capture device specifiers (list ends with an empty string).
	 */
	public final static int ALC_CAPTURE_DEVICE_SPECIFIER = 0x310;

	/** String for the default capture device specifier. */
	public final static int ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER = 0x311;

	/** Number of sample frames available for capture. */
	public final static int ALC_CAPTURE_SAMPLES = 0x312;

	/*
	 * ALC_ENUMERATE_ALL_EXT enums
	 */

	/** Enumerate All extension */
	public final static int ALC_ENUMERATE_ALL_EXT = 1;

	/** String for the default extended device specifier. */
	public final static int ALC_DEFAULT_ALL_DEVICES_SPECIFIER = 0x1012;

	/**
	 * String for the given extended device's specifier.<br>
	 * <br>
	 * If device handle is NULL, it is instead a null-char separated list of
	 * strings of known extended device specifiers (list ends with an empty string).
	 */
	public final static int ALC_ALL_DEVICES_SPECIFIER = 0x1013;

	/*
	 * Context Management
	 */

	/**
	 * This function creates a context using a specified device.<br>
	 * <br>
	 * The attribute list can be NULL, or a zero terminated list of integer pairs composed of valid
	 * ALC attribute tokens and requested values.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_VALUE - An additional context can not be created for this device.<br>
	 * ALC_INVALID_DEVICE - The specified device is not a valid output device.
	 * @param device a pointer to a device
	 * @param attrlist a pointer to a set of attributes<br>
	 *                 [ALC_FREQUENCY, ALC_MONO_SOURCES, ALC_REFRESH, ALC_STEREO_SOURCES, ALC_SYNC]
	 * @return Returns a pointer to the new context (NULL on failure).
	 * @see #alcDestroyContext(ALCcontext) 
	 * @see #alcMakeContextCurrent(ALCcontext) 
	 */
	public ALCcontext alcCreateContext(ALCdevice device, IntByReference attrlist);

	/**
	 * This function makes a specified context the current context.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_CONTEXT - The specified context is invalid.
	 * @param context a pointer to the new context
	 * @return ALC_TRUE on success, or ALC_FALSE on failure.
	 * @see #alcCreateContext(ALCdevice, IntByReference) 
	 * @see #alcDestroyContext(ALCcontext) 
	 */
	public boolean alcMakeContextCurrent(ALCcontext context);

	/**
	 * This function tells a context to begin processing.<br>
	 * <br>
	 * When a context is suspended, changes in OpenAL state will be accepted but will not be processed.
	 * {@link #alcSuspendContext(ALCcontext)} can be used to suspend a context,
	 * and then all the OpenAL state changes can be applied at once,
	 * followed by a call to {@link #alcProcessContext(ALCcontext)} to apply all the state changes immediately.
	 * In some cases, this procedure may be more efficient than application of properties in a non-suspended state.
	 * In some implementations, process and suspend calls are each a NOP.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_CONTEXT - The specified context is invalid.
	 * @param context a pointer to the new context
	 * @see #alcSuspendContext(ALCcontext)    
	 */
	public void alcProcessContext(ALCcontext context);

	/**
	 * This function suspends processing on a specified context.<br>
	 * <br>
	 * When a context is suspended, changes in OpenAL state will be accepted but will not be processed.
	 * A typical use of alcSuspendContext would be to suspend a context,
	 * apply all the OpenAL state changes at once,
	 * and then call {@link #alcProcessContext(ALCcontext)} to apply all the state changes at once.
	 * In some cases, this procedure may be more efficient than application of properties in a non-suspended state.
	 * In some implementations, process and suspend calls are each a NOP.<br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_CONTEXT - The specified context is invalid.
	 * @param context a pointer to the context to be suspended
	 * @see #alcProcessContext(ALCcontext)    
	 */
	public void alcSuspendContext(ALCcontext context);

	/**
	 * This function destroys a context.<br>
	 * <br>
	 * A context which is not current can be destroyed at any time
	 * (all sources within that context will also be deleted).
	 * {@link #alcMakeContextCurrent(ALCcontext)} should be used to make sure the context to be destroyed is not current
	 * (NULL is valid for {@link #alcMakeContextCurrent(ALCcontext)}).<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_CONTEXT - The specified context is invalid.
	 * @param context a pointer to the new context
	 * @see #alcCreateContext(ALCdevice, IntByReference)    
	 * @see #alcMakeContextCurrent(ALCcontext) 
	 */
	public void alcDestroyContext(ALCcontext context);

	/**
	 * This function retrieves the current context.
	 * @see #alcGetContextsDevice(ALCcontext) 
	 * @return a pointer to the current context.
	 */
	public ALCcontext alcGetCurrentContext();

	/**
	 * This function retrieves a context's device pointer.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_CONTEXT - The specified context is invalid.
	 * @param context a pointer to a context
	 * @return a pointer to the specified context's device.
	 * @see #alcGetCurrentContext()
	 */
	public ALCdevice alcGetContextsDevice(ALCcontext context);

	/*
	 * Error support. Obtain the most recent Context error
	 */

	/**
	 * This function retrieves the current context error state.
	 * @param device a pointer to the device to retrieve the error state from
	 * @return (ALCenum) the most recent error state (or ALC_NO_ERROR if none).
	 */
	public/* ALCenum */int alcGetError(ALCdevice device);

	/*
	 * Device Management
	 */

	/**
	 * This function opens a device by name.
	 * @param devicename a null-terminated string describing a device
	 * @return a pointer to the opened device. Will return NULL if a device can not be opened.
	 * @see #alcCloseDevice(ALCdevice) 
	 */
	public ALCdevice alcOpenDevice(String devicename);

	/**
	 * This function closes a device by name.<br>
	 * <br>
	 * Closing a device will fail if the device contains any contexts or buffers.
	 * @param device a pointer to an opened device
	 * @return ALC_TRUE will be returned on success or ALC_FALSE on failure.
	 * @see #alcOpenDevice(String) 
	 */
	public boolean alcCloseDevice(ALCdevice device);

	/*
	 * Extension support. Query for the presence of an extension, and obtain any
	 * appropriate function pointers and enum values.
	 */

	/**
	 * This function queries if a specified context extension is available.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_VALUE - The string pointer is not valid.
	 * @param device a pointer to the device to be queried for an extension
	 * @param extName a null-terminated string describing the extension
	 * @return ALC_TRUE if the extension is available, ALC_FALSE if the extension is not available.
	 * @see #alcGetProcAddress(ALCdevice, String) 
	 * @see #alcGetEnumValue(ALCdevice, String) 
	 */
	public boolean alcIsExtensionPresent(ALCdevice device, String extName);

	/**
	 * This function retrieves the address of a specified context extension function.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_VALUE - The string pointer is not valid.
	 * @param device a pointer to the device to be queried for the function
	 * @param funcName a null-terminated string describing the function
	 * @return the address of the function, or NULL if it is not found.
	 * @see #alcIsExtensionPresent(ALCdevice, String) 
	 * @see #alcGetEnumValue(ALCdevice, String) 
	 */
	public int alcGetProcAddress(ALCdevice device, String funcName);

	/**
	 * This function retrieves the enum value for a specified enumeration name.<br>
	 * <br>
	 * his is most often used for querying an enum value for an ALC extension.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_VALUE - The string pointer is not valid.
	 * @param device a pointer to the device to be queried
	 * @param enumName a null terminated string describing the enum value
	 * @return (ALCenum) the enum value described by the enumName string.
	 * @see #alcIsExtensionPresent(ALCdevice, String) 
	 * @see #alcGetProcAddress(ALCdevice, String) 
	 */
	public/* ALCenum */int alcGetEnumValue(ALCdevice device, String enumName);

	/*
	 * Query functions
	 */

	/**
	 * This function returns pointers to strings related to the context.<br>
	 * <br>
	 * ALC_DEFAULT_DEVICE_SPECIFIER will return the name of the default output device.<br>
	 * <br>
	 * ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER will return the name of the default capture device.<br>
	 * <br>
	 * ALC_DEVICE_SPECIFIER will return the name of the specified output device if a pointer is supplied,
	 * or will return a list of all available devices if a NULL device pointer is supplied.
	 * A list is a pointer to a series of strings separated by NULL characters,
	 * with the list terminated by two NULL characters.
	 * See Enumeration Extension for more details.<br>
	 * <br>
	 * ALC_CAPTURE_DEVICE_SPECIFIER will return the name of the specified capture device if a pointer is supplied,
	 * or will return a list of all available devices if a NULL device pointer is supplied.<br>
	 * <br>
	 * ALC_EXTENSIONS returns a list of available context extensions,
	 * with each extension separated by a space and the list terminated by a NULL character.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_ENUM - The specified parameter is not valid.
	 * @param device a pointer to the device to be queried
	 * @param param (ALCenum) an attribute to be retrieved<br>
	 *              [ALC_DEFAULT_DEVICE_SPECIFIER, ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER, ALC_DEVICE_SPECIFIER
	 *              ALC_CAPTURE_DEVICE_SPECIFIER, ALC_EXTENSIONS]
	 * @return This function returns pointers to strings related to the context.
	 */
	public Pointer alcGetString(ALCdevice device, /* ALCenum */int param);

	/**
	 * This function returns integers related to the context.<br>
	 * <br>
	 * The versions returned refer to the specification version that the implementation meets.
	 * @param device a pointer to the device to be queried
	 * @param param (ALCenum) an attribute to be retrieved<br>
	 *              [ALC_MAJOR_VERSION, ALC_MINOR_VERSION, ALC_ATTRIBUTES_SIZE, ALC_ALL_ATTRIBUTES]
	 * @param size (ALCsizei) the size of the destination buffer provided
	 * @param data a pointer to the data to be returned
	 */
	public void alcGetIntegerv(ALCdevice device, /* ALCenum */int param, /* ALCsizei */int size, IntByReference data);

	/*
	 * Capture functions
	 */

	/**
	 * This function opens a capture device by name.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_VALUE - One of the parameters has an invalid value.<br>
	 * ALC_OUT_OF_MEMORY - The specified device is invalid, or can not capture audio.
	 * @param devicename a pointer to a device name string
	 * @param frequency the frequency that the data should be captured at
	 * @param format (ALCenum) the requested capture buffer format
	 * @param buffersize (ALCsizei) the size of the capture buffer
	 * @return the capture device pointer, or NULL on failure.
	 * @see #alcCaptureCloseDevice(ALCdevice) 
	 */
	public ALCdevice alcCaptureOpenDevice(String devicename, int frequency, /* ALCenum */int format, /* ALCsizei */int buffersize);

	/**
	 * This function closes the specified capture device.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_DEVICE - The specified device is not a valid capture device.
	 * @param device a pointer to a capture device
	 * @return ALC_TRUE if the close operation was successful, ALC_FALSE on failure.
	 * @see #alcCaptureOpenDevice(String, int, int, int) 
	 */
	public boolean alcCaptureCloseDevice(ALCdevice device);

	/**
	 * This function begins a capture operation.<br>
	 * <br>
	 * alcCaptureStart will begin recording to an internal ring buffer of the size specified when opening the capture device.
	 * The application can then retrieve the number of samples currently available using the ALC_CAPTURE_SAPMPLES token with {@link #alcGetIntegerv(ALCdevice, int, int, IntByReference)}.
	 * When the application determines that enough samples are available for processing, then it can obtain them with a call to {@link #alcCaptureSamples(ALCdevice, int, int)}.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_DEVICE - The specified device is not a valid capture device.
	 * @param device a pointer to a capture device
	 * @see #alcCaptureStop(ALCdevice)    
	 * @see #alcCaptureSamples(ALCdevice, int, int) 
	 */
	public void alcCaptureStart(ALCdevice device);

	/**
	 * This function stops a capture operation.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_DEVICE - The specified device is not a valid capture device.
	 * @param device a pointer to a capture device
	 * @see #alcCaptureStart(ALCdevice)    
	 * @see #alcCaptureSamples(ALCdevice, int, int) 
	 */
	public void alcCaptureStop(ALCdevice device);

	/**
	 * This function completes a capture operation, and does not block.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * ALC_INVALID_VALUE - The specified number of samples is larger than the number of available samples.
	 * ALC_INVALID_DEVICE - The specified device is not a valid capture device.
	 * @param device a pointer to a capture device
	 * @param buffer (ALCvoid) a pointer to a data buffer, which must be large enough to accommodate samples number of samples
	 * @param samples (ALCsizei) the number of samples to be retrieved
	 * @see #alcCaptureStart(ALCdevice)    
	 * @see #alcCaptureStop(ALCdevice) 
	 */
	public void alcCaptureSamples(ALCdevice device, /* ALCvoid* */int buffer, /* ALCsizei */int samples);
}
