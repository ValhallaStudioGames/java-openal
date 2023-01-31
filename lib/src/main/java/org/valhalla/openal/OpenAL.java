package org.valhalla.openal;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.jna.ptr.IntByReference;
import org.valhalla.openal.intermediate.Buffer;
import org.valhalla.openal.intermediate.Context;
import org.valhalla.openal.intermediate.Device;
import org.valhalla.openal.intermediate.Source;
import org.valhalla.openal.jna.AL;
import org.valhalla.openal.factory.ALFactory;
import org.valhalla.openal.util.Util;
import org.valhalla.openal.util.ALException;

/**
 * This class contains some boilerplate code for initializing/tearing down OpenAL,
 * as well as factory methods to obtaining OpenAL objects.
 */
public class OpenAL {

    /**
     * AL factory that is used for providing OpenAL access
     */
    private final ALFactory factory;

    /**
     * The device that is used for playing back audio
     */
    private Device device;

    /**
     * The context that will be used for playing back audio
     */
    private Context context;

    /**
     * Creates a new OpenAL instance and initializes an ALFactory.<br>
     * This constructor will open a channel on the default device and create a related context.
     * @throws ALException when there is an error initialising OpenAL
     */
    public OpenAL() throws ALException {
	    this(new ALFactory(), null);
    }

    /**
     * Creates a new OpenAL instance and will open up a channel on the given device.<br>
     * The constructor will also create context on said device.
     * @param factory the factory to be
     * @param deviceName the device to be used for audio playback
     * @throws ALException when there is an error initialising OpenAL
     */
    public OpenAL(ALFactory factory, String deviceName) throws ALException {
	    this.factory = factory;
	    init(deviceName);
    }

    /**
     * Initializes OpenAL. You usually don't need to call this method directly,
     * as it's called automatically by the constructor.
     * @param deviceName the device to be used for audio playback
     * @throws ALException when there is an error initialising OpenAL
     */
    private void init(String deviceName) throws ALException {
	    if (device == null) {
	        device = new Device(factory, deviceName);
	    }

	    if (context == null) {
	        context = new Context(device);
	    }
    }

    /**
     * Cleans up the Context and Device objects.
     */
    public void close() {
	    if (context != null) {
	        context.close();
	        context = null;
	    }

	    if (device != null) {
	        device.close();
	        device = null;
	    }
    }

    /**
     * Returns the OpenAL device object
     * @return the Device used be OpenAL
     */
    public Device getDevice() {
	    return device;
    }

    /**
     * Returns the OpenAL context object
     * @return the current OpenAL context
     */
    public Context getContext() {
	    return context;
    }

    /**
     * Creates a new OpenAL source and returns it.
     * @return the created OpenAL source
     * @throws ALException when there is an error creating an OpenAL source
     * @see AL#alGenSources(int, IntByReference)
     */
    private Source createSource() throws ALException {
	    return new Source(factory);
    }

    /**
     * Creates a new OpenAL buffer and returns it.
     * @return the created OpenAL buffer
     * @throws ALException when there is an error creating an OpenAL buffer
     * @see AL#alGenBuffers(int, int[])
     */
    private Buffer createBuffer() throws ALException {
	    return new Buffer(factory);
    }

    /**
     * Utility method to create a source with a given wave file attached as a buffer.
     * @param waveFile The file to load into the source's buffer
     * @return A new OpenAL source
     * @throws ALException when there is an error related to OpenAL
     * @throws IOException when there is an error reading the file
     * @throws UnsupportedAudioFileException when file type of the file is in an unsupported format
     */
    public Source createSource(File waveFile) throws ALException, IOException, UnsupportedAudioFileException {
	    return createSource(AudioSystem.getAudioInputStream(waveFile));
    }

    /**
     * Utility method to create a source and preload audio data into its buffer.
     * @param url a URL of a wave file containing the audio data to load
     * @return A new OpenAL source
     * @throws ALException when there is an error related to OpenAL
     * @throws IOException when there is an error reading the file
     * @throws UnsupportedAudioFileException when file type of the file is in an unsupported format
     */
    public Source createSource(URL url) throws ALException, IOException, UnsupportedAudioFileException {
	    return createSource(AudioSystem.getAudioInputStream(url));
    }

    /**
     * Utility method to create a source and preload audio data into its buffer
     * @param audioInputStream The audio input stream to load into the new source's buffer
     * @return A new OpenAL source
     * @throws ALException when there is an error related to OpenAL
     * @throws IOException when there is an error reading the file
     */
    public Source createSource(AudioInputStream audioInputStream) throws ALException, IOException {
	    Source source = createSource();
	    Buffer buffer = createBuffer(audioInputStream);
	    source.setBuffer(buffer);
	    return source;
    }

    /**
     * Creates a buffer and loads the given wave file input that buffer.
     * @param waveFile The file to load into the new buffer
     * @return A new buffer preloaded with the given audio file contents
     * @throws ALException when there is an error related to OpenAL
     * @throws IOException when there is an error reading the file
     * @throws UnsupportedAudioFileException when file type of the file is in an unsupported format
     */
    public Buffer createBuffer(File waveFile) throws ALException, IOException, UnsupportedAudioFileException {
	    return createBuffer(AudioSystem.getAudioInputStream(waveFile));
    }

    /**
     * Creates a buffer and fills it with data from the given audio input stream
     * @param audioStream The audio input stream to load into the new buffer
     * @return A new OpenAL buffer preloaded with the given audio file contents
     * @throws ALException when there is an error related to OpenAL
     * @throws IOException when there is an error reading the file
     */
    public Buffer createBuffer(AudioInputStream audioStream) throws ALException, IOException {
	    Buffer result = createBuffer();
	    result.addBufferData(audioStream.getFormat(), Util.readStreamContents(audioStream));
	    return result;
    }
}
