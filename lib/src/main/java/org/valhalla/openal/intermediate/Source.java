package org.valhalla.openal.intermediate;

import javax.sound.sampled.AudioFormat;

import org.valhalla.openal.stream.SourceBufferedOutputStream;
import org.valhalla.openal.stream.SourceOutputStream;
import org.valhalla.openal.jna.AL;
import org.valhalla.openal.factory.ALFactory;
import org.valhalla.openal.util.Util;

import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import org.valhalla.openal.util.ALException;
import org.valhalla.openal.util.SourceState;
import org.valhalla.openal.util.SourceType;
import org.valhalla.openal.util.Tuple3F;

/**
 * A class representing an OpenAL source.<br>
 * A source is a collection of OpenAL buffers that will be played one after another until there are none left.
 * @author Romain PETIT <u>tokazio@esyo.net</u>
 */
public class Source {

    /**
     * The size of each buffer that will be appended to a source
     */
    private int STREAMING_BUFFER_SIZE = 1024;

    /**
     * Link to the AL interface.<br>
     * The AL interface should be the interface on which the Buffer exists.
     */
    private final AL al;

    /**
     * A unique number given by OpenAL to each source.<br>
     * In OpenAL sources are addressed by id.
     */
    private final int sourceId;

    /**
     * Has the source been closed?
     * A closed source can no longer be played, and will result in a {@link ALException}.
     */
    private boolean closed = false;

    /**
     * Creates a new empty source.<br>
     * This source does not have any buffers assigned, so it cannot yet be played.
     * @param factory the factory that provides an OpenAL context
     * @throws ALException when there is an error creating a new OpenAL source
     * @see AL#alGenSources(int, IntByReference) 
     */
    public Source(ALFactory factory) throws ALException {
		al = factory.al;
		IntByReference sourceIdHolder = new IntByReference(0);
		
		clearErrors();
		al.alGenSources(1, sourceIdHolder);
		checkForError();
		
		sourceId = sourceIdHolder.getValue();

        System.out.println("source " + sourceId + " init");
    }

    /**
     * Set streaming buffer size in bytes (1024 by default).<br>
     * Used in createOutputStream.
     * @param aBufferSize buffer size in bytes.
     */
    private void setStreamingBufferSize(int aBufferSize){
    	STREAMING_BUFFER_SIZE = aBufferSize;
    }
    
    /**
     * Gets the size for steaming buffers.
     * @return Buffer size in bytes (1024 by default).
     */
    public int getStreamingBufferSize(){
    	return STREAMING_BUFFER_SIZE;
    }

    /**
     * Gets the unique source id of the source
     * @return the unique source id
     */
    public int getSourceId() {
    	return sourceId;
    }

    /**
     * Starts playing a source.<br>
     * A source needs to have at least 1 buffer attached to it, otherwise the source will not have anything to play.
     * @throws ALException when there is an error playing a source
     * @see AL#alSourcePlay(int) 
     */
    public void play() throws ALException {
    	clearErrors();
    	al.alSourcePlay(sourceId);
    	checkForError();
    }

    /**
     * Pauses the playback of a source.
     * @throws ALException when there is an error pausing the source
     * @see AL#alSourcePause(int) 
     */
    public void pause() throws ALException {
    	clearErrors();
    	al.alSourcePause(sourceId);
    	checkForError();
    }

    /**
     * Stops the playback of a source
     * @throws ALException when there is an error stopping the source
     * @see AL#alSourceStop(int) 
     */
    public void stop() throws ALException {
    	clearErrors();
    	al.alSourceStop(sourceId);
    	checkForError();
    }

    /**
     * Stops a source and rewinds it to the start.
     * @throws ALException when there is an error rewinding the source
     * @see AL#alSourceRewind(int)
     */
    public void rewind() throws ALException {
    	clearErrors();
    	al.alSourceRewind(sourceId);
    	checkForError();
    }

    /**
     * Closes a source and deletes the sources assigned to the object.
     * After closing a source, it can no longer be played.
     */
    public void close() {
	    if (!closed) {
	        IntByReference sourceIdHolder = new IntByReference(sourceId);
            IntByReference bufferId = new IntByReference();
            al.alGetSourcei(sourceId, AL.AL_BUFFER, bufferId);

            al.alSourcei(sourceId, AL.AL_BUFFER, 0);
	        al.alDeleteSources(1, sourceIdHolder);
            al.alDeleteBuffers(1, new int[] {bufferId.getValue()});


			System.out.println("buffer " + bufferId.getValue() + " closed");
            System.out.println("source " + sourceId + " closed");
        }
    }

    /**
     * Adds a buffer to the end of a source.
     * After a buffer has stopped playing, it will begin playing the next buffer.
     * @param buffer the buffer to be appended
     * @throws ALException when the buffer could not be appended
     */
    public void queueBuffer(Buffer buffer) throws ALException {
    	queueBuffers(new Buffer[]{buffer});
    }

    /**
     * Adds multiple buffers to a source.
     * After a buffer has stopped playing, it will begin playing the next buffer.
     * @param buffers the buffers to be appended
     * @throws ALException when the buffers could not be appended
     */
    public void queueBuffers(Buffer[] buffers) throws ALException {
		int[] bufferIds = new int[buffers.length];
		for (int i = 0; i < buffers.length; i++) {
		    bufferIds[i] = buffers[i].getBufferId();
		}
		
		clearErrors();
		al.alSourceQueueBuffers(sourceId, buffers.length, bufferIds);
		checkForError();
    }

    /**
     * Removes a buffer from a source.
     * @param buffer the buffer to be removed
     * @throws ALException when the buffer could not be removed
     */
    public void unqueueBuffer(Buffer buffer) throws ALException {
    	unqueueBuffers(new Buffer[]{buffer});
    }

    /**
     * Removed multiple buffers from a source.
     * @param buffers the buffers to be removed
     * @throws ALException when the buffers could not be removed
     */
    public void unqueueBuffers(Buffer[] buffers) throws ALException {
		int[] bufferIds = new int[buffers.length];
		for (int i = 0; i < buffers.length; i++) {
		    bufferIds[i] = buffers[i].getBufferId();
		}
		
		clearErrors();
		al.alSourceUnqueueBuffers(sourceId, bufferIds.length, bufferIds);
		checkForError();
    }

    /**
     * Gets the amount of buffers that are queued to a source.
     * @return the amount of queued buffers
     * @throws ALException when there is an error retrieving the amount of buffers
     */
    public int getQueuedBufferCount() throws ALException {
    	return getIntParam(AL.AL_BUFFERS_QUEUED);
    }

    /**
     * Gets the amount of buffers that have already been played.
     * @return the amount of buffers played
     * @throws ALException when there is an error retrieving the amount of buffers
     */
    public int getProcessedBufferCount() throws ALException {
    	return getIntParam(AL.AL_BUFFERS_PROCESSED);
    }

    /**
     * Gets the current state of a source.<br>
     * Source states can be INITIAL, PLAYING, STOPPED or PAUSED.
     * @return the source's state
     * @throws ALException when there is an error retrieving the state of the source
     * @see SourceState
     */
    public SourceState getSourceState() throws ALException {
		int sourceState = getIntParam(AL.AL_SOURCE_STATE);
        return switch (sourceState) {
            case AL.AL_INITIAL -> SourceState.INITIAL;
            case AL.AL_PLAYING -> SourceState.PLAYING;
            case AL.AL_STOPPED -> SourceState.STOPPED;
            case AL.AL_PAUSED -> SourceState.PAUSED;
            default -> throw new ALException("Unknown source state value encountered: " + sourceState);
        };
    }

    /**
     * Source type (Static, Streaming or undetermined).
     * Source is STATIC if a Buffer has been attached using AL_BUFFER.
     * Source is STREAMING if one or more Buffers have been attached using alSourceQueueBuffers.
     * Source is UNDETERMINED when it has the NULL buffer attached.
     * @return the type of the source
     * @throws ALException when there is an error retrieving the type of the source
     */
    public SourceType getSourceType() throws ALException {
		int sourceType = getIntParam(AL.AL_SOURCE_TYPE);
        return switch (sourceType) {
            case AL.AL_STATIC -> SourceType.STATIC;
            case AL.AL_STREAMING -> SourceType.STREAMING;
            case AL.AL_UNDETERMINED -> SourceType.UNDETERMINED;
            default -> throw new ALException("Unknown source type value encountered: " + sourceType);
        };
    }

    /**
     * Gets a floating point parameter of a source.
     * @param param the param to be queried
     * @return the value of the parameter
     * @throws ALException when there is an error fetching the value.
     * @see AL#alGetSourcef(int, int, FloatByReference) 
     */
    public float getFloatParam(int param) throws ALException {
		FloatByReference result = new FloatByReference(0f);
		
		clearErrors();
		al.alGetSourcef(sourceId, param, result);
		checkForError();
		
		return result.getValue();
    }

    /**
     * Sets a floating point parameter of a source.
     * @param param the parameter to be set
     * @param value the value to be assigned
     * @throws ALException when there is an error updating the value.
     * @see AL#alSourcef(int, int, float) 
     */
    public void setFloatParam(int param, float value) throws ALException {
    	clearErrors();
		al.alSourcef(sourceId, param, value);
		checkForError();
    }

    /**
     * Gets a tuple of floating point parameters of a source.
     * @param param the parameter to be queried
     * @return the 3 values associated with the parameter
     * @throws ALException when there is an error fetching the values.
     * @see AL#alGetSource3f(int, int, FloatByReference, FloatByReference, FloatByReference) 
     */
    public Tuple3F getFloat3Param(int param) throws ALException {
		FloatByReference v1 = new FloatByReference(0f);
		FloatByReference v2 = new FloatByReference(0f);
		FloatByReference v3 = new FloatByReference(0f);
		
		clearErrors();
		al.alGetSource3f(sourceId, param, v1, v2, v3);
		checkForError();
		
		return new Tuple3F(v1.getValue(), v2.getValue(), v3.getValue());
    }

    /**
     * Sets a tuple of floating point parameters of a source.
     * @param param the parameter to be set
     * @param value the values to be set
     * @throws ALException when there is an error updating the values.
     * @see AL#alSource3f(int, int, float, float, float) 
     */
    public void setFloat3Param(int param, Tuple3F value) throws ALException {
    	clearErrors();
    	al.alSource3f(sourceId, param, value.v1, value.v2, value.v3);
    	checkForError();
    }

    /**
     * Gets an integer value of a source of a source.
     * @param param the parameter to be queried
     * @return the value of the parameter
     * @throws ALException when there is an error fetching the value.
     */
    public int getIntParam(int param) throws ALException {
		IntByReference result = new IntByReference(0);
		
		clearErrors();
		al.alGetSourcei(sourceId, param, result);
		checkForError();
		
		return result.getValue();
    }

    /**
     * Sets an integer property of a source.
     * @param param the parameter to be set
     * @param value the value to bet set
     * @throws ALException when there is an error updating the value.
     */
    public void setIntParam(int param, int value) throws ALException {
    	clearErrors();
		al.alSourcei(sourceId, param, value);
		checkForError();
    }

    /**
     * Gets the assigned buffer of the source.
     * @return the assigned buffer
     * @throws ALException when there is an error fetching the buffer
     */
    public Buffer getBuffer() throws ALException {
		int bufferId = getIntParam(AL.AL_BUFFER);
		return new Buffer(al, bufferId);
    }

    /**
     * Sets the buffer assigned to the source.
     * @param buffer the buffer to be assigned
     * @throws ALException when there is an error assigning the buffer
     */
    public void setBuffer(Buffer buffer) throws ALException {
    	setIntParam(AL.AL_BUFFER, buffer.getBufferId());
    }

    /**
     * Returns the current gain value of the source.
     * @return the gain of the source
     * @throws ALException when there is an error fetching the gain
     */
    public float getGain() throws ALException {
    	return getFloatParam(AL.AL_GAIN);
    }

    /**
     * Indicate the gain (volume amplification) applied. Range: [0.0-1.0]
     * A value of 1.0 means un-attenuated/unchanged.
     * Each division by 2 equals an attenuation of -6dB.
     * Each multiplication with 2 equals an amplification of +6dB.
     * A value of 0.0 is meaningless with respect to a logarithmic scale;
     * it is interpreted as zero volume - the channel is effectively disabled.
     * @param gain the gain to be set
     * @throws ALException when there is an error updating the gain
     */
    public void setGain(float gain) throws ALException {
    	setFloatParam(AL.AL_GAIN, gain);
    }

    /**
     * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
     * @return the pitch of the source
     * @throws ALException when there is an error fetching the pitch
     */
    public float getPitch() throws ALException {
    	return getFloatParam(AL.AL_PITCH);
    }

    /**
     * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
     * @param pitch the pitch to be set
     * @throws ALException when there is an error updating the pitch
     */
    public void setPitch(float pitch) throws ALException {
    	setFloatParam(AL.AL_PITCH, pitch);
    }

    /**
     * Gets if the source is being looped. A looped source will restart after ending.
     * @return if the source is looped.
     * @throws ALException when there is an error fetching the looping parameter
     */
    public boolean isLooping() throws ALException {
    	return getIntParam(AL.AL_LOOPING) == AL.AL_TRUE;
    }

    /**
     * Sets the looping parameter of a source.
     * @param looping the looping value to be set
     * @throws ALException when there is an error updating the looping parameter
     */
    public void setLooping(boolean looping) throws ALException {
    	setIntParam(AL.AL_LOOPING, looping ? AL.AL_TRUE : AL.AL_FALSE);
    }

    /**
     * Returns the current location in three-dimensional space.
     * For more information, see {@link #setPosition(Tuple3F)}.
     * @return the current position of the source
     * @throws ALException when there is an error fetching the position
     */
    public Tuple3F getPosition() throws ALException {
    	return getFloat3Param(AL.AL_POSITION);
    }

    /**
     * Specify the current location in three-dimensional space.
     * OpenAL, like OpenGL, uses a right-handed coordinate system,
     * where in a frontal default view X (thumb) points right, Y points up (index finger),
     * and Z points towards the viewer/camera (middle finger).
     * To switch from a left-handed coordinate system, flip the sign on the Z coordinate.
     * Listener position is always in the world coordinate system.
     * @param position the new position of the source
     * @throws ALException when there is an error fetching the position
     */
    public void setPosition(Tuple3F position) throws ALException {
    	setFloat3Param(AL.AL_POSITION, position);
    }

    /**
     * Specify the current location in three-dimensional space.
     * OpenAL, like OpenGL, uses a right-handed coordinate system,
     * where in a frontal default view X (thumb) points right, Y points up (index finger),
     * and Z points towards the viewer/camera (middle finger).
     * To switch from a left-handed coordinate system, flip the sign on the Z coordinate.
     * Listener position is always in the world coordinate system.
     * @param x the x coordinate for the source
     * @param y the y coordinate for the source
     * @param z the z coordinate for the source
     * @throws ALException when there is an error updating the position
     */
    public void setPosition(float x, float y, float z) throws ALException {
    	setFloat3Param(AL.AL_POSITION, new Tuple3F(x, y, z));
    }

    /**
     * Returns the current velocity in three-dimensional space.
     * @return the current velocity of the source
     * @throws ALException when there is an error fetching the velocity
     */
    public Tuple3F getVelocity() throws ALException {
    	return getFloat3Param(AL.AL_VELOCITY);
    }

    /**
     * Specify the current velocity in three-dimensional space.
     * @param velocity the new velocity of the source
     * @throws ALException when there is an error updating the velocity
     */
    public void setVelocity(Tuple3F velocity) throws ALException {
    	setFloat3Param(AL.AL_VELOCITY, velocity);
    }
    
    /**
     * Creates a buffered output stream that is associated with the source.
     * @param format the format of the audio input
     * @param numberOfBuffer the amount of buffers associated with the input
     * @param aBufferSize the size of the buffers
     * @return a new buffered output source stream
     * @throws ALException when there was an error creating a new output stream
     */
    public SourceBufferedOutputStream createOutputStream(AudioFormat format, int numberOfBuffer, int aBufferSize) throws ALException {
		setStreamingBufferSize(aBufferSize);
		return new SourceBufferedOutputStream(new SourceOutputStream(al, this, format, numberOfBuffer), STREAMING_BUFFER_SIZE);
    }

    /**
     * Check if an OpenAL error has occurred.
     * @throws ALException when an OpenAL error has occurred an according Exception will be thrown
     */
    private void checkForError() throws ALException {
    	Util.checkForALError(al);
    }

    /**
     * Clears any past errors.
     * If not used, some functions might throw strange errors.
     */
    private void clearErrors() {
    	Util.clearErrors(al);
    }

    /**
     * Check if 2 sources are linked to the same OpenAL source.
     * @param other the other source to be checked
     * @return if the 2 sources are equal
     */
    @Override
    public boolean equals(Object other) {
		if (other == null) {
		    return false;
		}
		if (!(other instanceof Source)) {
		    return false;
		}
		return ((Source) other).getSourceId() == getSourceId();
    }

    /**
     * Gets the unique hashcode representing the source object.
     * @return a unique hashcode
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
    	return getSourceId() * 11;
    }

    /**
     * Returns a String representation of the source
     * @return a String with details about the source
     * @see Object#toString()
     */
    @Override
    public String toString() {
    	return "ALSource[" + getSourceId() + "]";
    }
}
