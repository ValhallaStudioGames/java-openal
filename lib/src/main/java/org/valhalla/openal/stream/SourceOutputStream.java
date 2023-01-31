package org.valhalla.openal.stream;

import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;

import org.valhalla.openal.intermediate.Buffer;
import org.valhalla.openal.intermediate.Source;
import org.valhalla.openal.jna.AL;
import org.valhalla.openal.util.ALException;
import org.valhalla.openal.util.SourceState;

/**
 * An output stream for easily streaming audio data to OpenAL source.
 * You can create a SourceOutputStream object by calling the createOutputStream() method of the relevant Source object.
 * The underlying implementation uses a cyclic buffer array for streaming the incoming audio data into the given OpenAL source.
 * @author Uri Shaked
 */
public class SourceOutputStream extends OutputStream {

    /**
     * The amount of cyclic buffers that will be rotated.
     */
    private int CYCLIC_BUFFER_COUNT = 3;

    /**
     * The source associated with the source output stream.
     */
    private final Source source;
    
    /**
     * The audio format of the input data.
     */
    private final AudioFormat format;
    
    /**
     * The array of buffers that will be rotated in and out of use.
     */
    private Buffer[] buffers;

    /**
     * The current position/ array-index of the queue head
     */
    private int queueHead = 0;
    
    /**
     * The current position/ array-index of the queue tail
     */
    private int queueTail = 0;

    /**
     * The amount of samples that have been processed/ played
     */
    private long samplesProcessed = 0;

    /**
     * Link to the AL interface.<br>
     * The AL interface should be the interface on which the Buffer exists.
     */
    private final AL al;

    /**
     * Creates a new source output stream that will manage the creation and rotation of buffers
	 * @param al the OpenAL interface on which the output stream will be created
     * @param source the source for which the buffers will be managed
     * @param format the format of the buffer data
     * @param numberOfBuffer the amount of buffers
     * @throws ALException when there is an error setting up an output stream
     */
    public SourceOutputStream(AL al, Source source, AudioFormat format, int numberOfBuffer) throws ALException {	
	    super();
	    this.al = al;
	    this.source = source;
	    this.format = format;

	    setNumberOfBuffer(numberOfBuffer);
    }

    /**
     * Set the number of buffers and initialize them.
     * @param numberOfBuffer the new number of buffers
     */
    private void setNumberOfBuffer(int numberOfBuffer) throws ALException {
	    CYCLIC_BUFFER_COUNT = numberOfBuffer;
	    initBuffers();
    }
    
    /**
     * Gets the amount of buffers in the circular buffer
     * @return The number of buffers
     */
    public int getNumberOfBuffer() {
	    return CYCLIC_BUFFER_COUNT;
    }
    
    /**
     * Initialize buffers (empty buffers)
     */
    private void initBuffers() throws ALException {
	    buffers = new Buffer[CYCLIC_BUFFER_COUNT];
	    for (int i = 0; i < buffers.length; i++) {
	        buffers[i] = new Buffer(al);
	    }
    }
    
    /**
	 * Gets the amount of buffers that have been processed/ played.
     * @return the amount of processed buffers
     */
    public long getSamplesProcessed() {
	    return this.samplesProcessed;
    }

    @Override
    public void write(int b) throws IOException {
	    write(new byte[]{(byte) b}, 0, 1);
    }

    @Override
    public void write(byte[] b) throws IOException {
	    write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
	    try {
	        int queueSize = source.getQueuedBufferCount();
	        if (queueSize >= buffers.length) {
	    		while (source.getProcessedBufferCount() == 0) {
	    	    	try {
	    				Thread.sleep(20);
	    	    	} catch (InterruptedException e) {
	    				throw new IOException("IO Operation interrupted", e);
	    	    	}
	    		}
	    		source.unqueueBuffer(buffers[queueTail]);

	    		samplesProcessed += buffers[queueTail].getIntParam(AL.AL_SIZE) / (buffers[queueTail].getIntParam(AL.AL_BITS) / 8);

	    		queueTail = (queueTail + 1) % buffers.length;
	        }

	        if (off != 0) {
	    		throw new IOException("Offsets other than 0 are not currently supported");
	        }

	        buffers[queueHead].addBufferData(format, b);
	        source.queueBuffer(buffers[queueHead]);
	        queueHead = (queueHead + 1) % buffers.length;

	        if (source.getSourceState() == SourceState.INITIAL) {
	    		source.play();
	        }
	    }catch (ALException e) {
	        throw new IOException(e);
	    }
    }

    @Override
    public void close() {
	    for (Buffer buffer : buffers) {
	        buffer.close();
	    }
    }
}
