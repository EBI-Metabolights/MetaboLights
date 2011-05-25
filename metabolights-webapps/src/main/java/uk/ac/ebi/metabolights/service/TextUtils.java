package uk.ac.ebi.metabolights.service;

public class TextUtils {
	/**
	 * Returns true if aText is non-null and has visible content.
	 *
	 * This is a test which is often performed, and should probably
	 * be placed in a general utility class.
	 */
	public static boolean textHasContent( String aText ){
		String EMPTY_STRING = "";
		return (aText != null) && (!aText.trim().equals(EMPTY_STRING));
	}

}
