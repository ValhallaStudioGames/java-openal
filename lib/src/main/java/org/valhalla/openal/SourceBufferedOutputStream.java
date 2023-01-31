package org.valhalla.openal;

import java.io.BufferedOutputStream;

/**
 * A wrapper class for {@link SourceOutputStream} that makes use of {@link BufferedOutputStream}.
 * @author Romain PETIT <u>tokazio@esyo.net</u>
 */
public class SourceBufferedOutputStream extends BufferedOutputStream {

    /**
     * The output stream used be the wrapper class
     */
    private final SourceOutputStream sourceOutputStream;
    
    /**
     * Creates a new buffered output stream.<br>
     * The buffered output stream will assign new buffers when needed.
     * The steam will only allocate n buffers at a time instead of making 1 buffer with the whole file to be played back.
     * @param sourceOutputStream the output stream that will be used
     * @param size the total size of the output buffer
     */
    public SourceBufferedOutputStream(SourceOutputStream sourceOutputStream, int size) {
	    super(sourceOutputStream, size);
	    this.sourceOutputStream = sourceOutputStream;
    }
    
    /**
     * Gets the amount of samples that have already been played by the associated source.
     * @return the amount of samples that have been processed
     */
    public long getSamplesProcessed() {
	    return sourceOutputStream.getSamplesProcessed();
    }
}
