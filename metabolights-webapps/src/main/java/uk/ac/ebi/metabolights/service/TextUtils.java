/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/24/12 12:40 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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

	public static boolean checkSpecialCharsAndScript(String text){
		if(text.contains("<") || text.contains(">") || text.contains("!") || text.contains("%") ||
				text.contains("*") || text.contains("?") || text.contains("script")) {
			return true;
		}else return false;
	}

}
