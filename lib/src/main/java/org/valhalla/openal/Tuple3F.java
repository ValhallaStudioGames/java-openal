package org.valhalla.openal;

/**
 * A tuple of 3 floats, used for specifying coordinates in the OpenAL System
 * @author Uri Shaked
 */
public class Tuple3F {

	/**
	 * The first value of the tuple.
	 * (Usually representing the x-coordinate)
	 */
	public final float v1;

	/**
	 * The second value of the tuple.
	 * (Usually representing the y-coordinate)
	 */
	public final float v2;

	/**
	 * The third value of the tuple.
	 * (Usually representing the z-coordinate)
	 */
	public final float v3;

	/**
	 * Creates a new tuple object.
	 * @param v1 the first float value
	 * @param v2 the second float value
	 * @param v3 the third float value
	 */
	public Tuple3F(float v1, float v2, float v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	/**
	 * Checks if 2 tuple objects point to the same coordinates.
	 * @param other the other tuple object
	 * @return if the 2 tuple objects are similar
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Tuple3F)) {
			return false;
		}
		return (((Tuple3F) other).v1 == v1) && (((Tuple3F) other).v2 == v2) && (((Tuple3F) other).v3 == v3);
	}

	/**
	 * Generates a unique hashcode for a tuple object
	 * @return a unique hashcode for the tuple object
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + Float.floatToIntBits(v1);
		hash = hash * 31 + Float.floatToIntBits(v2);
		hash = hash * 13 + Float.floatToIntBits(v3);
		return hash;
	}

	/**
	 * Returns a String representation of the tuple
	 * @return a String with details about the tuple
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "Tuple3F[" + v1 + "," + v2 + "," + v3 + "]";
	}
}
