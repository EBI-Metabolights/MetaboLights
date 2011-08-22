package uk.ac.ebi.metabolights.service;

public class TextUtils {
	/**
	 * Returns true if aText is non-null and has visible content.
	 * 
	 * This is a test which is often performed, and should probably be placed in
	 * a general utility class.
	 */
	public static boolean textHasContent(String aText) {
		String EMPTY_STRING = "";
		return (aText != null) && (!aText.trim().equals(EMPTY_STRING));
	}

	/**
	 * Test null or empty, ignoring line breaks etc.
	 * @param str
	 */
	public static boolean isNullOrEmpty(String str) {
		if (str == null) {
			return true;
		}
		str = str.replace('\n', ' ');
		str = str.replace('\r', ' ');
		if (str.trim().equalsIgnoreCase("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Wraps an error stack as a String.
	 * 
	 * @param throwable
	 * @return
	 */
	public static String getErrorStackAsHTML(Throwable throwable) {
		StringBuffer sb = new StringBuffer(throwable.getMessage() + "<br>");
		StackTraceElement[] st = throwable.getStackTrace();
		for (int i = 0; i < st.length; i++) {
			StackTraceElement stackTraceElement = st[i];
			sb.append("\tat " + stackTraceElement.getClassName() + "."
					+ stackTraceElement.getMethodName() + " ( "
					+ stackTraceElement.getFileName() + ":"
					+ stackTraceElement.getLineNumber() + " )" + "<br>");
		}
		if (throwable.getCause() != null) {
			sb.append("<br>" + getErrorStackAsHTML(throwable.getCause()));
		}
		return sb.toString();
	}

}
