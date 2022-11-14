package org.valhalla.openal;

import com.sun.jna.ptr.IntByReference;
import javax.sound.sampled.AudioFormat;

import org.valhalla.openal.jna.AL;
import org.valhalla.openal.jna.ALFactory;
import org.valhalla.openal.jna.Util;

/**
 *
 * @author Romain PETIT <u>tokazio@esyo.net</u>
 */
public class Buffer {

    /**
     *
     */
    private final AL al;

    /**
     *
     */
    private final int bufferId;

    /**
     *
     */
    private boolean closed = false;

    /**
     *
     * @param factory
     * @throws ALException
     */
    public Buffer(ALFactory factory) throws ALException {
    	this(factory.al);
    }

    /**
     *
     * @param al
     * @throws ALException
     */
    public Buffer(AL al) throws ALException {
		this.al = al;
		int[] bufferIds = {0};
		Util.clearErrors(al);
		al.alGenBuffers(1, bufferIds);
		Util.checkForALError(al);
		bufferId = bufferIds[0];
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	        this.close();
	    }));
    }

    /**
     *
     * @param al
     * @param bufferId
     */
    Buffer(AL al, int bufferId) {
    	this.al = al;
    	this.bufferId = bufferId;
    	
    	Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	        this.close();
	    }));
    }

    /**
     *
     */
    public void close() {
		if (!closed) {
		    int[] bufferIds = {bufferId};
		    al.alDeleteBuffers(1, bufferIds);
		    closed = true;
		}
    }

    /**
     *
     * @param format
     * @param data
     * @throws ALException
     */
    public void addBufferData(AudioFormat format, byte[] data) throws ALException {
    	addBufferData(format, data, data.length);
    }

    /**
     *
     * @param format
     * @param data
     * @param size
     * @throws ALException
     */
    public void addBufferData(AudioFormat format, byte[] data, int size) throws ALException {
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
     *
     * @param format
     * @param data
     * @param size
     * @param sampleRate
     * @throws ALException
     */
    public void addBufferData(int format, byte[] data, int size, int sampleRate) throws ALException {
    	Util.clearErrors(al);
    	al.alBufferData(bufferId, format, data, size, sampleRate);
    	Util.checkForALError(al);
    }

    /**
     *
     * @return
     */
    public int getBufferId() {
    	return bufferId;
    }

    /**
     *
     * @param other
     * @return
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
     *
     * @return
     */
    @Override
    public int hashCode() {
    	return getBufferId() * 11;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
    	return "ALBuffer[" + getBufferId() + "]";
    }

    /**
     *
     * @param param
     * @return
     * @throws ALException
     */
    public int getIntParam(int param) throws ALException {
    	IntByReference result = new IntByReference(0);
    	Util.clearErrors(al);
    	al.alGetBufferi(getBufferId(), param, result);
    	Util.checkForALError(al);
    	return result.getValue();
    }
}
