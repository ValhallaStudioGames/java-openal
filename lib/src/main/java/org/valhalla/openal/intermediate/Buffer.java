package org.valhalla.openal.intermediate;

import com.sun.jna.ptr.IntByReference;
import javax.sound.sampled.AudioFormat;

import org.valhalla.openal.jna.AL;
import org.valhalla.openal.factory.ALFactory;
import org.valhalla.openal.util.Util;
import org.valhalla.openal.util.ALException;

/**
 * A class representing an OpenAL buffer.<br>
 * A Buffer is a piece of memory in which the next part(s) of an audio track are stored.
 * An audio file will not be read entirely when creating a source, but will rather create buffers when needed.
 * @author Romain PETIT <u>tokazio@esyo.net</u>
 */
public class Buffer {

    /**
     * Link to the AL interface.<br>
	 * The AL interface should be the interface on which the Buffer exists.
     */
    private final AL al;

    /**
     * A unique number given by OpenAL to each buffer.<br>
	 * In OpenAL buffers are addressed by id.
     */
    private final int bufferId;

    /**
     * Has the buffer been closed?
	 * A closed buffer can no longer be read from or written to, and will result in a {@link ALException}.
     */
    private boolean closed = false;

    /**
     * Creates a new default buffer.<br>
	 * <br>
	 * The buffer will automatically get a buffer id assigned.
     * @param factory the factory that provides an OpenAL context
     * @throws ALException when there has been a problem creating a new buffer
     */
    public Buffer(ALFactory factory) throws ALException {
    	this(factory.al);
    }

    /**
     * Creates a new buffer.<br>
	 * <br>
	 * The buffer will automatically get a buffer id assigned.
	 * @param al the OpenAL interface on which the buffer will be created
	 * @throws ALException when there has been a problem creating a new buffer
     */
    public Buffer(AL al) throws ALException {
		this.al = al;
		int[] bufferIds = {0};
		Util.clearErrors(al);
		al.alGenBuffers(1, bufferIds);
		Util.checkForALError(al);
		bufferId = bufferIds[0];
		
		System.out.println("buffer " + bufferId + " init");
    }

    /**
     * Creates a new link to a buffer.<br>
	 * <br>
	 * This function can be used to construct a replica of a buffer object.
	 * @param al the OpenAL interface on which the buffer will be created
     * @param bufferId the buffer that will be replicated
     */
    Buffer(AL al, int bufferId) {
    	this.al = al;
    	this.bufferId = bufferId;
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    /**
     * Closes and destroys the buffer.
	 * Closing a buffer will result in the buffer getting deleted.
	 * This will happen automatically when the class is no longer needed.
	 * After a buffer has been deleted, using the buffer will throw an {@link ALException}.
     */
    public void close() {
		if (!closed) {
		    int[] bufferIds = {bufferId};
		    al.alDeleteBuffers(1, bufferIds);
		    closed = true;
		}
    }

    /**
     * Appends sound data to a buffer.<br>
	 * <br>
	 * The audio format is rather important.
	 * Inputting the wrong format might not throw an error, but it will be audibly wrong.
     * @param format format of the data
     * @param data the sound data
     * @throws ALException if there was an error adding data
     */
    public void addBufferData(AudioFormat format, byte[] data) throws ALException {
    	addBufferData(format, data, data.length);
    }

    /**
	 * Appends sound data to a buffer.<br>
	 * <br>
	 * The audio format is rather important.
	 * Inputting the wrong format might not throw an error, but it will be audibly wrong.
	 * @param format format of the data
	 * @param data the sound data
     * @param size the size of the data buffer
	 * @throws ALException if there was an error adding data
	 * @see #addBufferData(AudioFormat, byte[])
     */
    private void addBufferData(AudioFormat format, byte[] data, int size) throws ALException {
		int audioFormat = AL.AL_FALSE;
		if (format.getSampleSizeInBits() == 8) {
		    if (format.getChannels() == 1) {
			audioFormat = AL.AL_FORMAT_MONO8;
		    } else if (format.getChannels() == 2) {
			audioFormat = AL.AL_FORMAT_STEREO8;
		    }
		} else if (format.getSampleSizeInBits() == 16) {
		    if (format.getChannels() == 1) {
			audioFormat = AL.AL_FORMAT_MONO16;
		    } else if (format.getChannels() == 2) {
			audioFormat = AL.AL_FORMAT_STEREO16;
		    }
		}
		if (audioFormat == AL.AL_FALSE) {
		    throw new ALException("Unsuppported audio format: " + format);
		}

		addBufferData(audioFormat, data, size, (int) format.getSampleRate());
    }

	/**
	 * Appends sound data to a buffer.<br>
	 * <br>
	 * The audio format is rather important.
	 * Inputting the wrong format might not throw an error, but it will be audibly wrong.
	 * @param format format of the data
	 * @param data the sound data
	 * @param size the size of the data buffer
     * @param sampleRate the sample rate of the buffer data
	 * @throws ALException if there was an error adding data
	 * @see #addBufferData(AudioFormat, byte[])
     */
    private void addBufferData(int format, byte[] data, int size, int sampleRate) throws ALException {
    	Util.clearErrors(al);
    	al.alBufferData(bufferId, format, data, size, sampleRate);
    	Util.checkForALError(al);
    }

    /**
     * Gets the unique buffer id of the buffer
     * @return the unique buffer id
     */
    public int getBufferId() {
    	return bufferId;
    }

    /**
     * Check if 2 buffers point to the same OpenAL buffer
     * @param other the other buffer
     * @return if the 2 buffers contain the same OpenAL buffer
     */
    @Override
    public boolean equals(Object other) {
		if (other == null) {
		    return false;
		}
		if (!(other instanceof Buffer)) {
		    return false;
		}
		return ((Buffer) other).getBufferId() == getBufferId();
    }

	/**
	 * Gets the hashcode for a buffer object
	 * @return the hashcode for a buffer
	 * @see Object#hashCode()
	 */
	@Override
    public int hashCode() {
    	return getBufferId() * 11;
    }

    /**
     * Returns a String representation of the buffer
     * @return a String with details about the buffer
	 * @see Object#toString()
     */
    @Override
    public String toString() {
    	return "ALBuffer[" + getBufferId() + "]";
    }

    /**
     * Gets an internal integer property for a buffer
     * @param param the requested integer parameter
     * @return the value of the requested integer parameter
     * @throws ALException if the parameter could not be found
	 * @see AL#alGetBufferi(int, int, IntByReference) 
     */
    public int getIntParam(int param) throws ALException {
    	IntByReference result = new IntByReference(0);
    	Util.clearErrors(al);
    	al.alGetBufferi(getBufferId(), param, result);
    	Util.checkForALError(al);
    	return result.getValue();
    }
}
