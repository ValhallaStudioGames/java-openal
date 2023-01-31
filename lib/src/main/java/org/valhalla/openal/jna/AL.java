package org.valhalla.openal.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

/**
 * interface that defines all the functions and variables present in "al/al.h"<br>
 * <br>
 * jna will translate these functions into calls to the native OpenAL sound system.
 */
public interface AL extends Library {
	/**
	 * Gets or loads the applications OpenAL instance.
	 */
	public final static AL instance = (AL) Native.load("openal", AL.class);

	/** "no distance model" or "no buffer" */
	public final static int AL_NONE = 0;

	/** Boolean False. */
	public final static int AL_FALSE = 0;

	/** OpenAL Boolean True. */
	public final static int AL_TRUE = 1;

	/**
	 * Relative source.<br>
	 * Type:    ALboolean<br>
	 * Range:   [AL_TRUE, AL_FALSE]<br>
	 * Default: AL_FALSE<br>
	 * <br>
	 * Specifies if the Source has relative coordinates.
	 */
	public final static int AL_SOURCE_RELATIVE = 0x202;

	/**
	 * Inner cone angle, in degrees.<br>
	 * Type:    ALint, ALfloat<br>
	 * Range:   [0 - 360]<br>
	 * Default: 360<br>
	 * <br>
	 * The angle covered by the inner cone, where the source will not attenuate.
	 */
	public final static int AL_CONE_INNER_ANGLE = 0x1001;

	/**
	 * Outer cone angle, in degrees.<br>
	 * Range:   [0 - 360]<br>
	 * Default: 360<br>
	 * <br>
	 * The angle covered by the outer cone, where the source will be fully attenuated.
	 */
	public final static int AL_CONE_OUTER_ANGLE = 0x1002;

	/**
	 * Source pitch.<br>
	 * Type:    ALfloat<br>
	 * Range:   [0.5 - 2.0]<br>
	 * Default: 1.0<br>
	 * <br>
	 * A multiplier for the frequency (sample rate) of the source's buffer.
	 */
	public final static int AL_PITCH = 0x1003;

	/**
	 * Source or listener position.<br>
	 * Type:    ALfloat[3], ALint[3]<br>
	 * Default: {0, 0, 0}<br>
	 * <br>
	 * The source or listener location in three-dimensional space.<br>
	 * <br>
	 * OpenAL, like OpenGL, uses a right-handed coordinate system,
	 * where in a frontal default view X (thumb) points right,
	 * Y points up (index finger),
	 * and Z points towards the viewer/camera (middle finger).<br>
	 * <br>
	 * To switch from a left-handed coordinate system, flip the sign on the Z coordinate.
	 */
	public final static int AL_POSITION = 0x1004;

	/**
	 * Source direction.<br>
	 * Type:    ALfloat[3], ALint[3]<br>
	 * Default: {0, 0, 0}<br>
	 * <br>
	 * Specifies the current direction in local space.<br>
	 * A zero-length vector specifies an omnidirectional source (cone is ignored).
	 */
	public final static int AL_DIRECTION = 0x1005;

	/**
	 * Source or listener velocity.<br>
	 * Type:    ALfloat[3], ALint[3]<br>
	 * Default: {0, 0, 0}<br>
	 * <br>
	 * Specifies the current velocity in local space.
	 */
	public final static int AL_VELOCITY = 0x1006;

	/**
	 * Source looping.
	 * Type:    ALboolean<br>
	 * Range:   [AL_TRUE, AL_FALSE]<br>
	 * Default: AL_FALSE<br>
	 * <br>
	 * Specifies whether source is looping.
	 */
	public final static int AL_LOOPING = 0x1007;

	/**
	 * Source buffer.<br>
	 * Type:  ALuint<br>
	 * Range: any valid Buffer.<br>
	 * <br>
	 * Specifies the buffer to provide sound samples.
	 */
	public final static int AL_BUFFER = 0x1009;

	/**
	 * Source or listener gain.<br>
	 * Type:  ALfloat<br>
	 * Range: [0.0 - ]<br>
	 * <br>
	 * A value of 1.0 means unattenuated. Each division by 2 equals an attenuation
	 * of about -6dB. Each multiplication by 2 equals an amplification of about
	 * +6dB.<br>
	 * <br>
	 * A value of 0.0 is meaningless with respect to a logarithmic scale; it is silent.
	 */
	public final static int AL_GAIN = 0x100A;

	/**
	 * Minimum source gain.<br>
	 * Type:  ALfloat<br>
	 * Range: [0.0 - 1.0]<br>
	 * <br>
	 * The minimum gain allowed for a source, after distance and cone attention is applied (if applicable).
	 */
	public final static int AL_MIN_GAIN = 0x100D;

	/**
	 * Maximum source gain.<br>
	 * Type:  ALfloat<br>
	 * Range: [0.0 - 1.0]<br>
	 * <br>
	 * The maximum gain allowed for a source, after distance and cone attention applied (if applicable).
	 */
	public final static int AL_MAX_GAIN = 0x100E;

	/**
	 * Listener orientation.<br>
	 * Type: ALfloat[6]<br>
	 * Default: {0.0, 0.0, -1.0, 0.0, 1.0, 0.0}<br>
	 * <br>
	 * Effectively two three-dimensional vectors. The first vector is the front (or
	 * "at") and the second is the top (or "up").<br>
	 * <br>
	 * Both vectors are in local space.
	 */
	public final static int AL_ORIENTATION = 0x100F;

	/**
	 * Source state (query only).<br>
	 * Type:  ALint<br>
	 * Range: [AL_INITIAL, AL_PLAYING, AL_PAUSED, AL_STOPPED]
	 */
	public final static int AL_SOURCE_STATE = 0x1010;

	/** Source state initial value */
	public final static int AL_INITIAL = 0x1011;
	/** Source state playing value */
	public final static int AL_PLAYING = 0x1012;
	/** Source state paused value */
	public final static int AL_PAUSED = 0x1013;
	/** Source state stopped value */
	public final static int AL_STOPPED = 0x1014;

	/**
	 * Source Buffer Queue size (query only).<br>
	 * Type: ALint<br>
	 * <br>
	 * The number of buffers queued using alSourceQueueBuffers, minus the buffers removed with alSourceUnqueueBuffers.
	 */
	public final static int AL_BUFFERS_QUEUED = 0x1015;

	/**
	 * Source Buffer Queue processed count (query only).<br>
	 * Type: ALint<br>
	 * <br>
	 * The number of queued buffers that have been fully processed, and can be removed with alSourceUnqueueBuffers.<br>
	 * <br>
	 * Looping sources will never fully process buffers because they will be set to play again for when the source loops.
	 */
	public final static int AL_BUFFERS_PROCESSED = 0x1016;

	/**
	 * Source reference distance.<br>
	 * Type:    ALfloat<br>
	 * Range:   [0.0 - ]<br>
	 * Default: 1.0<br>
	 * <br>
	 * The distance in units that no attenuation occurs.<br>
	 * <br>
	 * At 0.0, no distance attenuation ever occurs on non-linear attenuation models.
	 */
	public final static int AL_REFERENCE_DISTANCE = 0x1020;

	/**
	 * Source rolloff factor.<br>
 	 * Type:    ALfloat<br>
 	 * Range:   [0.0 - ]<br>
 	 * Default: 1.0<br>
 	 * <br>
 	 * Multiplier to exaggerate or diminish distance attenuation.<br>
 	 * <br>
 	 * At 0.0, no distance attenuation ever occurs.
 	 */
 	public static final int AL_ROLLOFF_FACTOR = 0x1021;

	/**
 	 * Outer cone gain.<br>
 	 * Type:    ALfloat<br>
	 * Range:   [0.0 - 1.0]<br>
	 * Default: 0.0<br>
 	 * <br>
 	 * The gain attenuation applied when the listener is outside the source's outer cone.
 	 */
	public static final int AL_CONE_OUTER_GAIN = 0x1022;

	/** Source buffer position, in seconds */
	public final static int AL_SEC_OFFSET = 0x1024;
	/** Source buffer position, in sample frames */
	public final static int AL_SAMPLE_OFFSET = 0x1025;
	/** Source buffer position, in bytes */
	public final static int AL_BYTE_OFFSET = 0x1026;

	/**
	 * Source type (query only).<br>
	 * Type:  ALint<br>
	 * Range: [AL_STATIC, AL_STREAMING, AL_UNDETERMINED]<br>
	 * <br>
	 * A Source is Static if a Buffer has been attached using AL_BUFFER.<br>
	 * <br>
	 * A Source is Streaming if one or more Buffers have been attached using alSourceQueueBuffers.
	 * <br>
	 * A Source is Undetermined when it has the NULL buffer attached using AL_BUFFER.
	 */
	public final static int AL_SOURCE_TYPE = 0x1027;

	/** Source type value static. */
	public final static int AL_STATIC = 0x1028;
	/** Source type value streaming. */
	public final static int AL_STREAMING = 0x1029;
	/** Source type value undetermined. */
	public final static int AL_UNDETERMINED = 0x1030;

	/** Unsigned 8-bit mono buffer format. */
	public final static int AL_FORMAT_MONO8 = 0x1100;
	/** Signed 16-bit mono buffer format. */
	public final static int AL_FORMAT_MONO16 = 0x1101;
	/** Unsigned 8-bit stereo buffer format. */
	public final static int AL_FORMAT_STEREO8 = 0x1102;
	/** Signed 16-bit stereo buffer format. */
	public final static int AL_FORMAT_STEREO16 = 0x1103;

	/** Buffer frequency (query only). */
	public final static int AL_FREQUENCY = 0x2001;
	/** Buffer bits per sample (query only). */
	public final static int AL_BITS = 0x2002;
	/** Buffer channel count (query only). */
	public final static int AL_CHANNELS = 0x2003;
	/** Buffer data size (query only). */
	public final static int AL_SIZE = 0x2004;

	/** Buffer state unused. Not for public use. */
	public final static int AL_UNUSED = 0x2010;
	/** Buffer state pending. Not for public use. */
	public final static int AL_PENDING = 0x2011;
	/** Buffer state processed. Not for public use. */
	public final static int AL_PROCESSED = 0x2012;

	/** Errors: No Error. */
	public final static int AL_NO_ERROR = 0;

	/** Errors: Invalid Name parameter passed to AL call. */
	public final static int AL_INVALID_NAME = 0xA001;

	/** Errors: Invalid Enum parameter passed to AL call. */
	public final static int AL_INVALID_ENUM = 0xA002;

	/** Errors: Invalid value parameter passed to AL call. */
	public final static int AL_INVALID_VALUE = 0xA003;

	/** Errors: Illegal AL call. */
	public final static int AL_INVALID_OPERATION = 0xA004;

	/** Errors: Not enough memory. */
	public final static int AL_OUT_OF_MEMORY = 0xA005;

	/** Context string: Vendor ID. */
	public final static int AL_VENDOR = 0xB001;
	/** Context string: Version. */
	public final static int AL_VERSION = 0xB002;
	/** Context string: Renderer ID. */
	public final static int AL_RENDERER = 0xB003;
	/** Context string: Space-separated extension list. */
	public final static int AL_EXTENSIONS = 0xB004;

	/* Global tweak-age. */

	/**
	 * Doppler scale.<br>
	 * Type:    ALfloat<br>
	 * Range:   [0.0 - ]<br>
	 * Default: 1.0<br>
	 * <br>
	 * Scale for source and listener velocities.
	 */
	public final static int AL_DOPPLER_FACTOR = 0xC000;

	/**
	 * Doppler velocity (deprecated).<br>
	 * <br>
	 * A multiplier applied to the Speed of Sound.
	 */
	public final static int AL_DOPPLER_VELOCITY = 0xC001;

	/**
	 * Speed of Sound, in units per second.<br>
	 * Type:    ALfloat<br>
	 * Range:   [0.0001 - ]<br>
	 * Default: 343.3<br>
	 * <br>
	 * The speed at which sound waves are assumed to travel, when calculating the doppler effect.
	 */
	public final static int AL_SPEED_OF_SOUND = 0xC003;

	/**
	 * Distance attenuation model.<br>
	 * Type:    ALint<br>
	 * Range:   [AL_NONE, AL_INVERSE_DISTANCE, AL_INVERSE_DISTANCE_CLAMPED,
	 *           AL_LINEAR_DISTANCE, AL_LINEAR_DISTANCE_CLAMPED,
	 *           AL_EXPONENT_DISTANCE, AL_EXPONENT_DISTANCE_CLAMPED]<br>
	 * Default: AL_INVERSE_DISTANCE_CLAMPED<br>
	 * <br>
	 * The model by which sources attenuate with distance.<br>
	 * <br>
	 * None     - No distance attenuation.<br>
	 * Inverse  - Doubling the distance halves the source gain.<br>
	 * Linear   - Linear gain scaling between the reference and max distances.<br>
	 * Exponent - Exponential gain drop-off.<br>
	 * <br>
	 * Clamped variations work like the non-clamped counterparts, except the
	 * distance calculated is clamped between the reference and max distances.
	 */
	public final static int AL_DISTANCE_MODEL = 0xD000;

	/** Distance model value inverse distance. */
	public final static int AL_INVERSE_DISTANCE = 0xD001;
	/** Distance model value inverse distance clamped. */
	public final static int AL_INVERSE_DISTANCE_CLAMPED = 0xD002;
	/** Distance model value linear distance. */
	public final static int AL_LINEAR_DISTANCE = 0xD003;
	/** Distance model value linear distance clamped. */
	public final static int AL_LINEAR_DISTANCE_CLAMPED = 0xD004;
	/** Distance model value exponent distance. */
	public final static int AL_EXPONENT_DISTANCE = 0xD005;
	/** Distance model value exponent distance clamped. */
	public final static int AL_EXPONENT_DISTANCE_CLAMPED = 0xD006;

	/* Renderer State management */

	/**
	 * This function enables a feature of the OpenAL driver.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified capability is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param capability (ALenum) the name of a capability to enable
	 * @see #alDisable(int)
	 * @see #alIsEnabled(int)
	 */
	void alEnable( /* ALenum */int capability);

	/**
	 * This function disables a feature of the OpenAL driver.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified capability is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param capability (ALenum) the name of a capability to disable
	 * @see #alEnable(int) 
	 * @see #alIsEnabled(int)
	 */
	void alDisable( /* ALenum */int capability);

	/**
	 * This function returns a boolean indicating if a specific feature is enabled in the OpenAL driver.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified capability is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param capability (ALenum) the capability to be returned
	 * @return if the given capability is enabled.
	 * @see #alEnable(int)
	 * @see #alDisable(int)
	 */
	boolean alIsEnabled( /* ALenum */int capability);

	/* State retrieval */

	/**
	 * This function retrieves an OpenAL string property.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified capability is not valid.
	 * @param param (ALenum) the property to be returned<br>
	 * 				[AL_VENDOR, AL_VERSION, AL_RENDERER, AL_EXTENSIONS]
	 * @return the requested OpenAL string property.
	 */
	Pointer alGetString( /* ALenum */int param);

	/**
	 * This function retrieves a boolean OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_VALUE - The specified data pointer is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 * 				[AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @param data a pointer to the location where the state will be stored
	 * @see #alGetBoolean(int)
	 * @see #alGetDouble(int)
	 * @see #alGetDoublev(int, DoubleByReference) 
	 * @see #alGetFloat(int) 
	 * @see #alGetFloatv(int, FloatByReference) 
	 * @see #alGetInteger(int) 
	 * @see #alGetIntegerv(int, IntByReference) 
	 */
	void alGetBooleanv( /* ALenum */int param, ByteByReference data);

	/**
	 * This function retrieves an integer OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_VALUE - The specified data pointer is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 * 				[AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @param data a pointer to the location where the state will be stored
	 * @see #alGetBoolean(int)
	 * @see #alGetBooleanv(int, ByteByReference) 
	 * @see #alGetDouble(int)
	 * @see #alGetDoublev(int, DoubleByReference)
	 * @see #alGetFloat(int)
	 * @see #alGetFloatv(int, FloatByReference)
	 * @see #alGetInteger(int)
	 */
	void alGetIntegerv( /* ALenum */int param, IntByReference data);

	/**
	 * This function retrieves a floating point OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_VALUE - The specified data pointer is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 * 				[AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @param data a pointer to the location where the state will be stored
	 * @see #alGetBoolean(int)
	 * @see #alGetBooleanv(int, ByteByReference)
	 * @see #alGetDouble(int)
	 * @see #alGetDoublev(int, DoubleByReference)
	 * @see #alGetFloat(int)
	 * @see #alGetInteger(int)
	 * @see #alGetIntegerv(int, IntByReference)
	 */
	void alGetFloatv( /* ALenum */int param, FloatByReference data);

	/**
	 * This function retrieves a double precision floating point OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_VALUE - The specified data pointer is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 * 				[AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @param data a pointer to the location where the state will be stored
	 * @see #alGetBoolean(int)
	 * @see #alGetBooleanv(int, ByteByReference)
	 * @see #alGetDouble(int)
	 * @see #alGetFloat(int)
	 * @see #alGetFloatv(int, FloatByReference)
	 * @see #alGetInteger(int)
	 * @see #alGetIntegerv(int, IntByReference)
	 */
	void alGetDoublev( /* ALenum */int param, DoubleByReference data);

	/**
	 * This function retrieves a boolean OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 * 				[AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @return the boolean state described by the param.
	 * @see #alGetBooleanv(int, ByteByReference)
	 * @see #alGetDouble(int)
	 * @see #alGetDoublev(int, DoubleByReference)
	 * @see #alGetFloat(int)
	 * @see #alGetFloatv(int, FloatByReference)
	 * @see #alGetInteger(int)
	 * @see #alGetIntegerv(int, IntByReference)
	 */
	boolean alGetBoolean( /* ALenum */int param);

	/**
	 * This function retrieves an integer OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 *              [AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @return the integer value described by the param.
	 * @see #alGetBoolean(int)
	 * @see #alGetBooleanv(int, ByteByReference)
	 * @see #alGetDouble(int)
	 * @see #alGetDoublev(int, DoubleByReference)
	 * @see #alGetFloat(int)
	 * @see #alGetFloatv(int, FloatByReference)
	 * @see #alGetIntegerv(int, IntByReference)
	 */
	int alGetInteger( /* ALenum */int param);

	/**
	 * This function retrieves a floating point OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 *              [AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @return the floating point value described by the param.
	 * @see #alGetBoolean(int)
	 * @see #alGetBooleanv(int, ByteByReference)
	 * @see #alGetDouble(int)
	 * @see #alGetDoublev(int, DoubleByReference)
	 * @see #alGetFloatv(int, FloatByReference)
	 * @see #alGetInteger(int)
	 * @see #alGetIntegerv(int, IntByReference)
	 */
	float alGetFloat( /* ALenum */int param);

	/**
	 * This function retrieves a double precision floating point OpenAL state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the state to be returned<br>
	 *              [AL_DOPPLER_FACTOR, AL_SPEED_OF_SOUND, AL_DISTANCE_MODEL]
	 * @return the double value described by the param.
	 * @see #alGetBoolean(int)
	 * @see #alGetBooleanv(int, ByteByReference)
	 * @see #alGetDoublev(int, DoubleByReference)
	 * @see #alGetFloat(int)
	 * @see #alGetFloatv(int, FloatByReference)
	 * @see #alGetInteger(int)
	 * @see #alGetIntegerv(int, IntByReference)
	 */
	double alGetDouble( /* ALenum */int param);

	/* Error retrieval */

	/**
	 * Returns an ALenum representing the error state. When an OpenAL error occurs, the
	 * error state is set and will not be changed until the error state is retrieved using alGetError.
	 * Whenever alGetError is called, the error state is cleared and the last state (the current
	 * state when the call was made) is returned. To isolate error detection to a specific portion
	 * of code, alGetError should be called before the isolated section to clear the current error
	 * state.
	 * @return the current error state.
	 */
	/* ALenum */int alGetError();

	/**
	 * This function tests if a specific extension is available for the OpenAL driver.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The specified extension string is not a valid pointer.
	 * @param extname a String describing the desired extension
	 * @return AL_TRUE if the extension is available, AL_FALSE if the extension is not available.
	 * @see #alGetProcAddress(String)
	 * @see #alGetEnumValue(String) 
	 */
	boolean alIsExtensionPresent(String extname);

	/**
	 * This function returns the address of an OpenAL extension function.
	 * @param fname a String containing the function name
	 * @return a pointer to the specified function. The return value will be NULL if the function is not found.
	 * @see #alIsExtensionPresent(String) 
	 * @see #alGetEnumValue(String) 
	 */
	Pointer alGetProcAddress(String fname);

	/**
	 * This function returns the enumeration value of an OpenAL enum described by a string.
	 * @param ename a String describing an OpenAL enum
	 * @return the actual ALenum described by a string. Returns NULL if the string doesn’t describe a valid OpenAL enum.
	 * @see #alIsExtensionPresent(String) 
	 * @see #alGetProcAddress(String) 
	 */
	/* ALenum */int alGetEnumValue(String ename);

	/*
	 * LISTENER represents the location and orientation of the 'user'
	 * in 3D-space.
	 * 
	 * Properties include: -
	 * 
	 * Gain AL_GAIN float Position AL_POSITION float[3] Velocity AL_VELOCITY
	 * float[3] Orientation AL_ORIENTATION float[6] (Forward then Up vectors)
	 */

	/**
	 * This function sets a floating point property for the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be set<br>
	 *              [AL_GAIN]
	 * @param value the float value to set the attribute to
	 * @see #alListener3f(int, float, float, float)
	 * @see #alListenerfv(int, FloatByReference)
	 * @see #alGetListenerf(int, FloatByReference)
	 * @see #alGetListener3f(int, FloatByReference, FloatByReference, FloatByReference) 
	 * @see #alGetListenerfv(int, FloatByReference) 
	 */
	void alListenerf( /* ALenum */int param, float value);

	/**
	 * This function sets a floating point property for the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to set<br>
	 *              [AL_POSITION, AL_VELOCITY]
	 * @param value1 the value to set the attribute to
	 * @param value2 the value to set the attribute to
	 * @param value3 the value to set the attribute to
	 * @see #alListenerf(int, float)
	 * @see #alListenerfv(int, FloatByReference) 
	 * @see #alGetListenerf(int, FloatByReference)
	 * @see #alGetListener3f(int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetListenerfv(int, FloatByReference)
	 */
	void alListener3f( /* ALenum */int param, float value1, float value2, float value3);

	/**
	 * This function sets a floating point-vector property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to set<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_ORIENTATION]
	 * @param values pointer to floating point-vector values
	 * @see #alListenerf(int, float)
	 * @see #alListener3f(int, float, float, float)
	 * @see #alGetListenerf(int, FloatByReference)
	 * @see #alGetListener3f(int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetListenerfv(int, FloatByReference)
	 */
	void alListenerfv( /* ALenum */int param, FloatByReference values);

	/**
	 * This function sets an integer property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be set<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_ORIENTATION]
	 * @param value the integer value to set the attribute to
	 * @see #alListener3i(int, int, int, int)
	 * @see #alListeneriv(int, IntByReference) 
	 * @see #alGetListeneri(int, IntByReference) 
	 * @see #alGetListener3i(int, int, int, int) 
	 * @see #alGetListeneriv(int, IntByReference) 
	 */
	void alListeneri( /* ALenum */int param, int value);

	/**
	 * This function sets an integer property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be set<br>
	 *              [AL_POSITION, AL_VELOCITY]
	 * @param value1 the integer values to set the attribute to
	 * @param value2 the integer values to set the attribute to
	 * @param value3 the integer values to set the attribute to
	 * @see #alListeneri(int, int)
	 * @see #alListeneriv(int, IntByReference)
	 * @see #alGetListeneri(int, IntByReference)
	 * @see #alGetListener3i(int, int, int, int)
	 * @see #alGetListeneriv(int, IntByReference)
	 */
	void alListener3i( /* ALenum */int param, int value1, int value2, int value3);

	/**
	 * This function sets an integer property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be set<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_ORIENTATION]
	 * @param values pointer to the integer values to set the attribute to
	 * @see #alListeneri(int, int)
	 * @see #alListener3i(int, int, int, int)
	 * @see #alGetListeneri(int, IntByReference)
	 * @see #alGetListener3i(int, int, int, int)
	 * @see #alGetListeneriv(int, IntByReference)
	 */
	void alListeneriv( /* ALenum */int param, IntByReference values);

	/*
	 * Get Listener parameters
	 */

	/**
	 * This function retrieves a floating point property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_GAIN]
	 * @param value a pointer to the floating point value being retrieved
	 * @see #alListenerf(int, float)
	 * @see #alListener3f(int, float, float, float)
	 * @see #alListenerfv(int, FloatByReference) 
	 * @see #alGetListener3f(int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetListenerfv(int, FloatByReference)
	 */
	void alGetListenerf( /* ALenum */int param, FloatByReference value);

	/**
	 * This function retrieves a set of three floating point values from a property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_POSITION, AL_VELOCITY]
	 * @param value1 pointers to the three floating point being retrieved
	 * @param value2 pointers to the three floating point being retrieved
	 * @param value3 pointers to the three floating point being retrieved
	 * @see #alListenerf(int, float)
	 * @see #alListener3f(int, float, float, float)
	 * @see #alListenerfv(int, FloatByReference)
	 * @see #alGetListenerf(int, FloatByReference)
	 * @see #alGetListenerfv(int, FloatByReference)
	 */
	void alGetListener3f( /* ALenum */int param, FloatByReference value1, FloatByReference value2, FloatByReference value3);

	/**
	 * This function retrieves a floating point-vector property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_ORIENTATION]
	 * @param values a pointer to the floating point-vector value being retrieved
	 * @see #alListenerf(int, float)
	 * @see #alListener3f(int, float, float, float)
	 * @see #alListenerfv(int, FloatByReference)
	 * @see #alGetListenerf(int, FloatByReference)
	 * @see #alGetListener3f(int, FloatByReference, FloatByReference, FloatByReference)
	 */
	void alGetListenerfv( /* ALenum */int param, FloatByReference values);

	/**
	 * This function retrieves an integer property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be retrieved
	 * @param value a pointer to the integer value being retrieved
	 * @see #alListeneri(int, int)
	 * @see #alListener3i(int, int, int, int)
	 * @see #alListeneriv(int, IntByReference)
	 * @see #alGetListener3i(int, int, int, int)
	 * @see #alGetListeneriv(int, IntByReference)
	 */
	void alGetListeneri( /* ALenum */int param, IntByReference value);

	/**
	 * This function retrieves an integer property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_POSITION, AL_VELOCITY]
	 * @param value1 pointers to the integer values being retrieved
	 * @param value2 pointers to the integer values being retrieved
	 * @param value3 pointers to the integer values being retrieved
	 * @see #alListeneri(int, int)
	 * @see #alListener3i(int, int, int, int)
	 * @see #alListeneriv(int, IntByReference)
	 * @see #alGetListeneri(int, IntByReference)
	 * @see #alGetListeneriv(int, IntByReference)
	 */
	void alGetListener3i( /* ALenum */int param, int value1, int value2, int value3);

	/**
	 * This function retrieves an integer property of the listener.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is not valid.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_ORIENTATION]
	 * @param values a pointer to the integer values being retrieved
	 * @see #alListeneri(int, int)
	 * @see #alListener3i(int, int, int, int)
	 * @see #alListeneriv(int, IntByReference)
	 * @see #alGetListeneri(int, IntByReference)
	 * @see #alGetListener3i(int, int, int, int)
	 */
	void alGetListeneriv( /* ALenum */int param, IntByReference values);

	/**
	 * This function generates one or more sources. References to sources are ALuint values,
	 * which are used wherever a source reference is needed (in calls such as {@link #alDeleteSources(int, IntByReference)}
	 * and {@link #alSourcei(int, int, int)}).<br>
	 * <br>
	 * If the requested number of sources cannot be created, an error will be generated which
	 * can be detected with alGetError. If an error occurs, no sources will be generated. If n
	 * equals zero, alGenSources does nothing and does not return an error.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_OUT_OF_MEMORY - There is not enough memory to generate all the requested sources.<br>
	 * AL_INVALID_VALUE - There are not enough non-memory resources to create all the requested sources, or the array pointer is not valid.<br>
	 * AL_INVALID_OPERATION - There is no context to create sources in.
	 * @param n (ALsizei) the number of sources to be generated
	 * @param sources pointer to an array of ALuint values which will store the names of the new sources
	 * @see #alDeleteSources(int, IntByReference)    
	 * @see #alIsSource(int) 
	 */
	void alGenSources( /* ALsizei */int n, IntByReference sources);

	/* Delete Source objects */

	/**
	 * This function deletes one or more sources.<br>
	 * <br>
	 * If the requested number of sources cannot be deleted,
	 * an error will be generated which can be detected with alGetError.
	 * If an error occurs, no sources will be deleted.
	 * If n equals zero, alDeleteSources does nothing and will not return an error.<br>
	 * <br>
	 * A playing source can be deleted – the source will be stopped and then deleted.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - At least one specified source is not valid, or an attempt is being made to delete more sources than exist.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param n (ALsizei) the number of sources to be deleted
	 * @param sources pointer to an array of source names identifying the sources to be deleted
	 * @see #alGenSources(int, IntByReference)    
	 * @see #alIsSource(int) 
	 */
	void alDeleteSources( /* ALsizei */int n, IntByReference sources);

	/* Verify a handle is a valid Source */

	/**
	 * This function tests if a source name is valid, returning AL_TRUE if valid and AL_FALSE if not.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source a source name to be tested for validity
	 * @return if the source name is valid.
	 * @see #alGenSources(int, IntByReference) 
	 * @see #alDeleteSources(int, IntByReference) 
	 */
	boolean alIsSource(int source);

	/*
	 * Set Source parameters
	 */

	/**
	 * This function sets a floating point property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being set
	 * @param param (ALenum) the name of the attribute to set<br>
	 *              [AL_PITCH, AL_GAIN, AL_MIN_GAIN, AL_MAX_GAIN, AL_MAX_DISTANCE, AL_ROLLOFF_FACTOR,
	 * 				AL_CONE_OUTER_GAIN, AL_CONE_INNER_ANGLE, AL_CONE_OUTER_ANGLE, AL_REFERENCE_DISTANCE]
	 * @param value the value to set the attribute to
	 * @see #alSource3f(int, int, float, float, float)
	 * @see #alSourcefv(int, int, FloatByReference) 
	 * @see #alGetSourcef(int, int, FloatByReference) 
	 * @see #alGetSource3f(int, int, FloatByReference, FloatByReference, FloatByReference) 
	 * @see #alGetSourcefv(int, int, FloatByReference) 
	 */
	void alSourcef(int source, /* ALenum */int param, float value);

	/**
	 * This function sets a source property requiring three floating point values.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being set
	 * @param param (ALenum) the three ALfloat values which the attribute will be set to<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param value1 (ALfloat) value which the attribute will be set to
	 * @param value2 (ALfloat) value which the attribute will be set to
	 * @param value3 (ALfloat) value which the attribute will be set to
	 * @see #alSourcef(int, int, float)
	 * @see #alSourcefv(int, int, FloatByReference)
	 * @see #alGetSourcef(int, int, FloatByReference)
	 * @see #alGetSource3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetSourcefv(int, int, FloatByReference)
	 */
	void alSource3f(int source, /* ALenum */int param, float value1, float value2, float value3);

	/**
	 * This function sets a floating point-vector property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being set
	 * @param param (ALenum) the name of the attribute being set<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param values a pointer to the vector to set the attribute to
	 * @see #alSourcef(int, int, float)
	 * @see #alSource3f(int, int, float, float, float)
	 * @see #alGetSourcef(int, int, FloatByReference)
	 * @see #alGetSource3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetSourcefv(int, int, FloatByReference)
	 */
	void alSourcefv(int source, /* ALenum */int param, FloatByReference values);

	/**
	 * This function sets an integer property of a source.<br>
	 * <br>
	 * The buffer name zero is reserved as a "NULL Buffer" and is accepted by alSourcei(..., AL_BUFFER, ...)
	 * as a valid buffer of zero length.
	 * The NULL Buffer is extremely useful for detaching buffers from a source
	 * which were attached using this call or with {@link #alSourceQueueBuffers(int, int, int[])}.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being set
	 * @param param (ALenum) the name of the attribute to set<br>
	 *              [AL_SOURCE_RELATIVE, AL_CONE_INNER_ANGLE, AL_CONE_OUTER_ANGLE, AL_LOOPING, AL_BUFFER, AL_SOURCE_STATE]
	 * @param value the value to set the attribute to
	 * @see #alSource3i(int, int, int, int, int)
	 * @see #alSourceiv(int, int, IntByReference)
	 * @see #alGetSourcei(int, int, IntByReference)
	 * @see #alGetSource3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetSourceiv(int, int, IntByReference) 
	 */
	void alSourcei(int source, /* ALenum */int param, int value);

	/**
	 * This function sets an integer property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being set
	 * @param param (ALenum) the name of the attribute to set<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param value1 value to set the attribute to
	 * @param value2 value to set the attribute to
	 * @param value3 value to set the attribute to
	 * @see #alSourcei(int, int, int)
	 * @see #alSourceiv(int, int, IntByReference)
	 * @see #alGetSourcei(int, int, IntByReference)
	 * @see #alGetSource3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetSourceiv(int, int, IntByReference)
	 */
	void alSource3i(int source, /* ALenum */int param, int value1, int value2, int value3);

	/**
	 * This function sets an integer property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being set
	 * @param param (ALenum) the name of the attribute to set<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param values the values to set the attribute to
	 * @see #alSourcei(int, int, int)
	 * @see #alSource3i(int, int, int, int, int)
	 * @see #alGetSourcei(int, int, IntByReference)
	 * @see #alGetSource3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetSourceiv(int, int, IntByReference)
	 */
	void alSourceiv(int source, /* ALenum */int param, IntByReference values);

	/*
	 * Get Source parameters
	 */

	/**
	 * This function retrieves a floating point property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to retrieve<br>
	 *              [AL_PITCH, AL_GAIN, AL_MIN_GAIN, AL_MAX_GAIN, AL_MAX_DISTANCE, AL_ROLLOFF_FACTOR,
	 * 				AL_CONE_OUTER_GAIN, AL_CONE_INNER_ANGLE, AL_CONE_OUTER_ANGLE, AL_REFERENCE_DISTANCE]
	 * @param value a pointer to the floating point value being retrieved
	 * @see #alSourcef(int, int, float)
	 * @see #alSource3f(int, int, float, float, float)
	 * @see #alSourcefv(int, int, FloatByReference)
	 * @see #alGetSource3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetSourcefv(int, int, FloatByReference)
	 */
	void alGetSourcef(int source, /* ALenum */int param, FloatByReference value);

	/**
	 * This function retrieves three floating point values representing a property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute being retrieved<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param value1 pointer to the values to retrieve
	 * @param value2 pointer to the values to retrieve
	 * @param value3 pointer to the values to retrieve
	 * @see #alSourcef(int, int, float)
	 * @see #alSource3f(int, int, float, float, float)
	 * @see #alSourcefv(int, int, FloatByReference)
	 * @see #alGetSourcef(int, int, FloatByReference)
	 * @see #alGetSourcefv(int, int, FloatByReference)
	 */
	void alGetSource3f(int source, /* ALenum */int param, FloatByReference value1, FloatByReference value2, FloatByReference value3);

	/**
	 * This function retrieves a floating point-vector property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute being retrieved
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param values a pointer to the values to retrieve<br>
	 * @see #alSourcef(int, int, float)
	 * @see #alSource3f(int, int, float, float, float)
	 * @see #alSourcefv(int, int, FloatByReference)
	 * @see #alGetSourcef(int, int, FloatByReference)
	 * @see #alGetSource3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 */
	void alGetSourcefv(int source, /* ALenum */int param, FloatByReference values);

	/**
	 * This function retrieves an integer property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to retrieve<br>
	 *              [AL_SOURCE_RELATIVE, AL_BUFFER, AL_SOURCE_STATE, AL_BUFFERS_QUEUED, AL_BUFFERS_PROCESSED]
	 * @param value a pointer to the integer value being retrieved
	 * @see #alSourcei(int, int, int)
	 * @see #alSource3i(int, int, int, int, int)
	 * @see #alSourceiv(int, int, IntByReference)
	 * @see #alGetSource3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetSourceiv(int, int, IntByReference)
	 */
	void alGetSourcei(int source, /* ALenum */int param, IntByReference value);

	/**
	 * This function retrieves an integer property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to retrieve<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param value1 pointer to the integer values being retrieved
	 * @param value2 pointer to the integer values being retrieved
	 * @param value3 pointer to the integer values being retrieved
	 * @see #alSourcei(int, int, int)
	 * @see #alSource3i(int, int, int, int, int)
	 * @see #alSourceiv(int, int, IntByReference)
	 * @see #alGetSourcei(int, int, IntByReference)
	 * @see #alGetSourceiv(int, int, IntByReference)
	 */
	void alGetSource3i(int source, /* ALenum */int param, IntByReference value1, IntByReference value2, IntByReference value3);

	/**
	 * This function retrieves an integer property of a source.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value given is out of range.<br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source source name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to retrieve<br>
	 *              [AL_POSITION, AL_VELOCITY, AL_DIRECTION]
	 * @param values pointer to the integer values being retrieved
	 * @see #alSourcei(int, int, int)
	 * @see #alSource3i(int, int, int, int, int)
	 * @see #alSourceiv(int, int, IntByReference)
	 * @see #alGetSourcei(int, int, IntByReference)
	 * @see #alGetSource3i(int, int, IntByReference, IntByReference, IntByReference)
	 */
	void alGetSourceiv(int source, /* ALenum */int param, IntByReference values);

	/*
	 * Source vector based playback calls
	 */

	/**
	 * This function plays a set of sources.<br>
	 * <br>
	 * The playing sources will have their state changed to AL_PLAYING.
	 * When called on a source which is already playing, the source will restart at the beginning.
	 * When the attached buffer(s) are done playing, the source will progress to the AL_STOPPED state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value pointer given is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param n (ALsizei) the number of sources to be played
	 * @param sources a pointer to an array of sources to be played
	 * @see #alSourcePlay(int)    
	 * @see #alSourcePause(int)
	 * @see #alSourcePausev(int, IntByReference) 
	 * @see #alSourceRewind(int) 
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStop(int)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourcePlayv( /* ALsizei */int n, IntByReference sources);

	/**
	 * This function stops a set of sources.<br>
	 * <br>
	 * The stopped sources will have their state changed to AL_STOPPED.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value pointer given is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param n (ALsizei) the number of sources to stop
	 * @param sources a pointer to an array of sources to be stopped
	 * @see #alSourcePlay(int)
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePause(int)
	 * @see #alSourcePausev(int, IntByReference)
	 * @see #alSourceRewind(int)
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStop(int)
	 */
	void alSourceStopv( /* ALsizei */int n, IntByReference sources);

	/**
	 * This function stops a set of sources and sets all their states to AL_INITIAL.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value pointer given is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param n (ALsizei) the number of sources to be rewound
	 * @param sources a pointer to an array of sources to be rewound
	 * @see #alSourcePlay(int)
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePause(int)
	 * @see #alSourcePausev(int, IntByReference)
	 * @see #alSourceRewind(int)
	 * @see #alSourceStop(int)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourceRewindv( /* ALsizei */int n, IntByReference sources);

	/**
	 * This function pauses a set of sources.<br>
	 * <br>
	 * The paused sources will have their state changed to AL_PAUSED.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The value pointer given is not valid.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param n (ALsizei) the number of sources to be paused
	 * @param sources a pointer to an array of sources to be paused
	 * @see #alSourcePlay(int)
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePause(int)
	 * @see #alSourceRewind(int)
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStop(int)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourcePausev( /* ALsizei */int n, IntByReference sources);

	/*
	 * Source based playback calls
	 */

	/**
	 * This function plays a source.<br>
	 * <br>
	 * The playing source will have its state changed to AL_PLAYING.
	 * When called on a source which is already playing, the source will restart at the beginning.
	 * When the attached buffer(s) are done playing, the source will progress to the AL_STOPPED state.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source the name of the source to be played
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePause(int)
	 * @see #alSourcePausev(int, IntByReference)
	 * @see #alSourceRewind(int)
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStop(int)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourcePlay(int source);

	/**
	 * This function stops a source.<br>
	 * <br>
	 * The stopped source will have its state changed to AL_STOPPED.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source the name of the source to be stopped
	 * @see #alSourcePlay(int)
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePause(int)
	 * @see #alSourcePausev(int, IntByReference)
	 * @see #alSourceRewind(int)
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourceStop(int source);

	/**
	 * This function stops the source and sets its state to AL_INITIAL.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source the name of the source to be rewound
	 * @see #alSourcePlay(int)
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePause(int)
	 * @see #alSourcePausev(int, IntByReference)
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStop(int)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourceRewind(int source);

	/**
	 * This function pauses a source.<br>
	 * <br>
	 * The paused source will have its state changed to AL_PAUSED.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source the name of the source to be paused
	 * @see #alSourcePlay(int)
	 * @see #alSourcePlayv(int, IntByReference)
	 * @see #alSourcePausev(int, IntByReference)
	 * @see #alSourceRewind(int)
	 * @see #alSourceRewindv(int, IntByReference)
	 * @see #alSourceStop(int)
	 * @see #alSourceStopv(int, IntByReference)
	 */
	void alSourcePause(int source);

	/*
	 * Source Queuing
	 */

	/**
	 * This function queues a set of buffers on a source.
	 * All buffers attached to a source will be played in sequence,
	 * and the number of processed buffers can be detected using an {@link #alSourcei(int, int, int)}
	 * call to retrieve AL_BUFFERS_PROCESSED.<br>
	 * <br>
	 * When first created, a source will be of type AL_UNDETERMINED. A successful
	 * alSourceQueueBuffers call will change the source type to AL_STREAMING.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_NAME - At least one specified buffer name is not valid, or the specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context, an attempt was made to add a new buffer which is not the same format as the buffers already in the queue, or the source already has a static buffer attached.
	 * @param source the name of the source to queue buffers onto
	 * @param n (ALsizei) the number of buffers to be queued
	 * @param buffers a pointer to an array of buffer names to be queued
	 * @see #alSourceUnqueueBuffers(int, int, int[])    
	 */
	void alSourceQueueBuffers(int source, /* ALsizei */int n, int[] buffers);

	/**
	 * This function unqueues a set of buffers attached to a source. The number of processed
	 * buffers can be detected using an {@link #alSourcei(int, int, int)} call to retrieve AL_BUFFERS_PROCESSED,
	 * which is the maximum number of buffers that can be unqueued using this call.<br>
	 * <br>
	 * The unqueue operation will only take place if all n buffers can be removed from the queue.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - At least one buffer can not be unqueued because it has not been processed yet.<br>
	 * AL_INVALID_NAME - The specified source name is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param source the name of the source to unqueue buffers from
	 * @param n (ALsizei) the number of buffers to be unqueued
	 * @param buffers a pointer to an array of buffer names that were removed
	 * @see #alSourceQueueBuffers(int, int, int[])    
	 */
	void alSourceUnqueueBuffers(int source, /* ALsizei */int n, int[] buffers);

	/*
	 * BUFFER objects are storage space for sample data. Buffers are
	 * referred to by Sources. One Buffer can be used by multiple Sources.
	 * 
	 * Properties include: -
	 * 
	 * Frequency (Query only) AL_FREQUENCY int Size (Query only) AL_SIZE int
	 * Bits (Query only) AL_BITS int Channels (Query only) AL_CHANNELS int
	 */

	/* Create Buffer objects */

	/**
	 * This function generates one or more buffers, which contain audio data
	 * (see {@link #alBufferData(int, int, byte[], int, int)}).
	 * References to buffers are ALuint values,
	 * which are used wherever a buffer reference is needed
	 * (in calls such as {@link #alDeleteBuffers(int, int[])}, {@link #alSourcei(int, int, int)},
	 * {@link #alSourceQueueBuffers(int, int, int[])}, and {@link #alSourceUnqueueBuffers(int, int, int[])})<br>
	 * <br>
	 * If the requested number of buffers cannot be created,
	 * an error will be generated which can be detected with alGetError.
	 * If an error occurs, no buffers will be generated.
	 * If n equals zero, alGenBuffers does nothing and does not return an error.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The buffer array isn't large enough to hold the number of buffers requested.<br>
	 * AL_OUT_OF_MEMORY - There is not enough memory available to generate all the buffers requested.<br>
	 * @param n (ALsizei) the number of buffers to be generated
	 * @param buffers pointer to an array of ALuint values which will store the names of the new buffers
	 * @see #alDeleteBuffers(int, int[])    
	 * @see #alIsBuffer(int) 
	 */
	void alGenBuffers( /* ALsizei */int n, int[] buffers);

	/**
	 * This function deletes one or more buffers, freeing the resources used by the buffer.
	 * Buffers which are attached to a source can not be deleted.
	 * See {@link #alSourcei(int, int, int)} and {@link #alSourceUnqueueBuffers(int, int, int[])}
	 * for information on how to detach a buffer from a source.<br>
	 * <br>
	 * If the requested number of buffers cannot be deleted,
	 * an error will be generated which can be detected with {@link #alGetError()}.
	 * If an error occurs, no buffers will be deleted.
	 * If n equals zero, alDeleteBuffers does nothing and will not return an error.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_OPERATION - The buffer is still in use and can not be deleted.<br>
	 * AL_INVALID_NAME - A buffer name is invalid.<br>
	 * AL_INVALID_VALUE - The requested number of buffers can not be deleted.
	 * @param n (ALsizei) the number of buffers to be deleted
	 * @param buffers pointer to an array of buffer names identifying the buffers to be deleted
	 * @see #alGenBuffers(int, int[])    
	 * @see #alIsBuffer(int) 
	 */
	void alDeleteBuffers( /* ALsizei */int n, int[] buffers);

	/**
	 * This function tests if a buffer name is valid, returning AL_TRUE if valid, AL_FALSE if not.<br>
	 * <br>
	 * The NULL buffer is always valid (see {@link #alSourcei(int, int, int)} for information on how the NULL buffer is used).
	 * @param buffer a buffer name to be tested for validity
	 * @return if the buffer name is valid.
	 * @see #alGenBuffers(int, int[]) 
	 * @see #alDeleteBuffers(int, int[]) 
	 */
	boolean alIsBuffer(int buffer);

	/**
	 * This function fills a buffer with audio data.
	 * All the pre-defined formats are PCM data,
	 * but this function may be used by extensions to load other data types as well.<br>
	 * <br>
	 * 8-bit PCM data is expressed as an unsigned value over the range 0 to 255, 128 being an audio output level of zero.
	 * 16-bit PCM data is expressed as a signed value over the range -32768 to 32767, 0 being an audio output level of zero.
	 * Stereo data is expressed in interleaved format, left channel first.
	 * Buffers containing more than one channel of data will be played without 3D spatialization.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_OUT_OF_MEMORY - There is not enough memory available to create this buffer.<br>
	 * AL_INVALID_VALUE - The size parameter is not valid for the format specified, the buffer is in use, or the data is a NULL pointer.<br>
	 * AL_INVALID_ENUM - The specified format does not exist.
	 * @param buffer buffer name to be filled with data
	 * @param format (ALenum) format type from among the following<br>
	 *               [AL_FORMAT_MONO8, AL_FORMAT_MONO16, AL_FORMAT_STEREO8, AL_FORMAT_STEREO16]
	 * @param data pointer to the audio data
	 * @param size (ALsizei) the size of the audio data in bytes
	 * @param freq (ALsizei) the frequency of the audio data
	 */
	void alBufferData(int buffer, /* ALenum */int format, byte[] data, /* ALsizei */int size, /* ALsizei */int freq);

	/*
	 * Set Buffer parameters
	 */

	/**
	 * This function sets a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be set
	 * @param value the ALfloat value to be set
	 * @see #alBuffer3f(int, int, float, float, float)
	 * @see #alBufferfv(int, int, FloatByReference) 
	 * @see #alGetBufferf(int, int, FloatByReference) 
	 * @see #alGetBuffer3f(int, int, FloatByReference, FloatByReference, FloatByReference) 
	 * @see #alGetBufferfv(int, int, FloatByReference) 
	 */
	void alBufferf(int buffer, /* ALenum */int param, float value);

	/**
	 * This function sets a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be set
	 * @param value1 the ALfloat value to be set
	 * @param value2 the ALfloat value to be set
	 * @param value3 the ALfloat value to be set
	 * @see #alBufferf(int, int, float)
	 * @see #alBufferfv(int, int, FloatByReference)
	 * @see #alGetBufferf(int, int, FloatByReference)
	 * @see #alGetBuffer3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetBufferfv(int, int, FloatByReference)
	 */
	void alBuffer3f(int buffer, /* ALenum */int param, float value1, float value2, float value3);

	/**
	 * This function sets a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be set
	 * @param values a pointer to the ALfloat values to be set
	 * @see #alBufferf(int, int, float)
	 * @see #alBuffer3f(int, int, float, float, float)
	 * @see #alGetBufferf(int, int, FloatByReference)
	 * @see #alGetBuffer3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetBufferfv(int, int, FloatByReference)
	 */
	void alBufferfv(int buffer, /* ALenum */int param, FloatByReference values);

	/**
	 * This function retrieves an integer property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be set
	 * @param value a pointer to an ALint to hold the retrieved data
	 * @see #alBuffer3i(int, int, int, int, int)
	 * @see #alBufferiv(int, int, IntByReference) 
	 * @see #alGetBufferi(int, int, IntByReference) 
	 * @see #alGetBuffer3i(int, int, IntByReference, IntByReference, IntByReference) 
	 * @see #alGetBufferiv(int, int, IntByReference) 
	 */
	void alBufferi(int buffer, /* ALenum */int param, int value);

	/**
	 * This function sets a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be set
	 * @param value1 the ALint value to be set
	 * @param value2 the ALint value to be set
	 * @param value3 the ALint value to be set
	 * @see #alBufferi(int, int, int)
	 * @see #alBufferiv(int, int, IntByReference)
	 * @see #alGetBufferi(int, int, IntByReference)
	 * @see #alGetBuffer3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetBufferiv(int, int, IntByReference)
	 */
	void alBuffer3i(int buffer, /* ALenum */int param, int value1, int value2, int value3);

	/**
	 * This function sets a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be set
	 * @param values a pointer to the ALint values to be set
	 * @see #alBufferi(int, int, int)
	 * @see #alBuffer3i(int, int, int, int, int)
	 * @see #alGetBufferi(int, int, IntByReference)
	 * @see #alGetBuffer3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetBufferiv(int, int, IntByReference)
	 */
	void alBufferiv(int buffer, /* ALenum */int param, IntByReference values);

	/*
	 * Get Buffer parameters
	 */

	/**
	 * This function retrieves a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.<br>
	 * AL_INVALID_VALUE - The specified value pointer is not valid.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be retrieved
	 * @param value a pointer to an ALfloat to hold the retrieved data
	 * @see #alBufferf(int, int, float)
	 * @see #alBuffer3f(int, int, float, float, float)
	 * @see #alBufferfv(int, int, FloatByReference)
	 * @see #alGetBuffer3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 * @see #alGetBufferfv(int, int, FloatByReference)
	 */
	void alGetBufferf(int buffer, /* ALenum */int param, FloatByReference value);

	/**
	 * This function retrieves a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.<br>
	 * AL_INVALID_VALUE - The specified value pointer is not valid.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be retrieved
	 * @param value1 pointer to a ALfloat values to hold the retrieved data
	 * @param value2 pointer to a ALfloat values to hold the retrieved data
	 * @param value3 pointer to a ALfloat values to hold the retrieved data
	 * @see #alBufferf(int, int, float)
	 * @see #alBuffer3f(int, int, float, float, float)
	 * @see #alBufferfv(int, int, FloatByReference)
	 * @see #alGetBufferf(int, int, FloatByReference)
	 * @see #alGetBufferfv(int, int, FloatByReference)
	 */
	void alGetBuffer3f(int buffer, /* ALenum */int param, FloatByReference value1, FloatByReference value2, FloatByReference value3);

	/**
	 * This function retrieves a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.<br>
	 * AL_INVALID_VALUE - The specified value pointer is not valid.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be retrieved
	 * @param values pointer to an ALfloat vector to hold the retrieved data
	 * @see #alBufferf(int, int, float)
	 * @see #alBuffer3f(int, int, float, float, float)
	 * @see #alBufferfv(int, int, FloatByReference)
	 * @see #alGetBufferf(int, int, FloatByReference)
	 * @see #alGetBuffer3f(int, int, FloatByReference, FloatByReference, FloatByReference)
	 */
	void alGetBufferfv(int buffer, /* ALenum */int param, FloatByReference values);

	/**
	 * This function retrieves an integer property of a buffer.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.<br>
	 * AL_INVALID_VALUE - The specified value pointer is not valid.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_FREQUENCY, AL_BITS, AL_CHANNELS, AL_SIZE, AL_DATA]
	 * @param value a pointer to an ALint to hold the retrieved data
	 * @see #alBufferi(int, int, int)
	 * @see #alBuffer3i(int, int, int, int, int)
	 * @see #alBufferiv(int, int, IntByReference)
	 * @see #alGetBuffer3i(int, int, IntByReference, IntByReference, IntByReference)
	 * @see #alGetBufferiv(int, int, IntByReference)
	 */
	void alGetBufferi(int buffer, /* ALenum */int param, IntByReference value);

	/**
	 * This function retrieves a floating point property of a buffer.<br>
	 * <br>
	 * There are no relevant buffer properties defined in OpenAL 1.1 which can be affected by this call,
	 * but this function may be used by OpenAL extensions.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.<br>
	 * AL_INVALID_VALUE - The specified value pointer is not valid.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be retrieved
	 * @param value1 pointer to ALint values to hold the retrieved data
	 * @param value2 pointer to ALint values to hold the retrieved data
	 * @param value3 pointer to ALint values to hold the retrieved data
	 * @see #alBufferi(int, int, int)
	 * @see #alBuffer3i(int, int, int, int, int)
	 * @see #alBufferiv(int, int, IntByReference)
	 * @see #alGetBufferi(int, int, IntByReference)
	 * @see #alGetBufferiv(int, int, IntByReference)
	 */
	void alGetBuffer3i(int buffer, /* ALenum */int param, IntByReference value1, IntByReference value2, IntByReference value3);

	/**
	 * This function retrieves a floating point property of a buffer.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_ENUM - The specified parameter is not valid.<br>
	 * AL_INVALID_NAME - The specified buffer doesn't have parameters (the NULL buffer), or doesn't exist.<br>
	 * AL_INVALID_VALUE - The specified value pointer is not valid.
	 * @param buffer buffer name whose attribute is being retrieved
	 * @param param (ALenum) the name of the attribute to be retrieved<br>
	 *              [AL_FREQUENCY, AL_BITS, AL_CHANNELS, AL_SIZE, AL_DATA]
	 * @param values pointer to an ALint vector to hold the retrieved data
	 * @see #alBufferi(int, int, int)
	 * @see #alBuffer3i(int, int, int, int, int)
	 * @see #alBufferiv(int, int, IntByReference)
	 * @see #alGetBufferi(int, int, IntByReference)
	 * @see #alGetBuffer3i(int, int, IntByReference, IntByReference, IntByReference)
	 */
	void alGetBufferiv(int buffer, /* ALenum */int param, IntByReference values);

	/*
	 * Global Parameters
	 */

	/**
	 * This function selects the OpenAL Doppler factor value.<br>
	 * <br>
	 * The default Doppler factor value is 1.0.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The specified value is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param value the Doppler scale value to set
	 */
	void alDopplerFactor(float value);

	/**
	 * This function selects the OpenAL Doppler factor velocity.<br>
	 * <br>
	 * The default Doppler velocity value is 1.0.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The specified value is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param value the Doppler velocity to set
	 */
	@Deprecated
	void alDopplerVelocity(float value);

	/**
	 * This function selects the speed of sound for use in Doppler calculations.<br>
	 * <br>
	 * The default speed of sound value is 343.3.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The specified value is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param value the speed of sound value to set
	 */
	void alSpeedOfSound(float value);

	/**
	 * This function selects the OpenAL distance model.<br>
	 * <br>
	 * The default distance model in OpenAL is AL_INVERSE_DISTANCE_CLAMPED.<br>
	 * <br>
	 * <b>Possible Error States</b><br>
	 * AL_INVALID_VALUE - The specified value is not valid.<br>
	 * AL_INVALID_OPERATION - There is no current context.
	 * @param distanceModel (ALenum) the distance model to be set<br>
	 *                      [AL_INVERSE_DISTANCE, AL_INVERSE_DISTANCE_CLAMPED, AL_LINEAR_DISTANCE
	 * 						AL_LINEAR_DISTANCE_CLAMPED, AL_EXPONENT_DISTANCE, AL_EXPONENT_DISTANCE_CLAMPED
	 * 						AL_NONE]
	 */
	void alDistanceModel( /* ALenum */int distanceModel);

}
