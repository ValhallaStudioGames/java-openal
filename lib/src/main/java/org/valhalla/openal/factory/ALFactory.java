package org.valhalla.openal.factory;

import java.io.File;
import java.io.FileNotFoundException;

import com.sun.jna.Native;
import org.valhalla.openal.jna.AL;
import org.valhalla.openal.jna.ALC;
import org.valhalla.openal.jna.ALExt;

public class ALFactory {
	private static final String DEFAULT_DLL_NAME = "openal";

	public final AL al;
	public final ALC alc;
	public final ALExt alext;

	public ALFactory() {
		al = Native.load(DEFAULT_DLL_NAME, AL.class);
		alc = Native.load(DEFAULT_DLL_NAME, ALC.class);
		alext = Native.load(DEFAULT_DLL_NAME, ALExt.class);
	}

	public ALFactory(File dllPath) throws FileNotFoundException {
		String dllName = DEFAULT_DLL_NAME;
		if (dllPath != null) {
			if (!dllPath.exists()) {
				throw new FileNotFoundException(dllPath.getAbsolutePath());
			}
			System.setProperty("jna.library.path", dllPath.getParent());
			dllName = dllPath.getName();
		}

		al = Native.load(dllName, AL.class);
		alc = Native.load(dllName, ALC.class);
		alext = Native.load(dllName, ALExt.class);
	}
}
