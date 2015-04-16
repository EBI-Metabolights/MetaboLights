/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/9/13 4:37 PM
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

package uk.ac.ebi.metabolights.repository.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for generic string operations
 * @author conesa
 *
 */
public final class StringUtils {
	/**
	 * Truncates a string from left or right any numbers of characters
	 * @param text Text to truncate
	 * @param numCharacters number of characters to truncate
	 * @param fromTheLeft
	 * @return
	 */
	public static String truncate(String text, int numCharacters, boolean fromTheLeft){

		//If the text is NOT long enough...
		if (text.length()<numCharacters){
			return "";
		}

		//if we have to
		if (fromTheLeft){
			return text.substring(numCharacters);
		}else{
			return text.substring(0, text.length() -numCharacters);
		}
	}
	/**
	 * Truncates a string from the end (right) in one character
	 * @param text Text to truncate
	 * @return
	 */
	public static String truncate (String text){
		return truncate (text,1,false);
	}
	/**
	 * Truncate a string from the end (right)
	 * @param text Text to truncate
	 * @param numCharacters number of characters to truncate
	 * @return
	 */
	public static String truncate (String text, int numCharacters){
		return truncate (text,numCharacters, false);
	}
	/**
	 * Truncates a string from the left or the right based on a char
	 * @param text text to truncate
	 * @param find character to find
	 * //@param SearchFromTheLeft start the search from the left if true, otherwise from the right.
	 * @param fromTheLeft if true , truncate from the left.
	 * @return
	 */
	public static String truncate (String text, String find, boolean searchFromTheLeft, boolean fromTheLeft){

		int pos;

		//If we need the left part...
		if (searchFromTheLeft){

			//Get the position of the char to find
			pos = text.indexOf(find);

		} else {

			//Get the position from the end plus the length of the string we are finding
			pos = text.lastIndexOf(find);

		}

		//If not found
		if (pos==-1) return "";

		//If we want to remove the right part...
		if (!fromTheLeft) {
			//...truncate "pos" has to be from the end
			pos = text.length() - pos;
		} else {
			//We want to remove the left part, included the "find" string. So we need to increase the position
			pos = pos + find.length();
		}

		//Call truncate by position
		return truncate (text,pos,fromTheLeft);

	}
	/**
	 * Replace any text within a text using regular expressions
	 * @param text: Text to search
	 * @param findPattern: Text to find (regular expression)
	 * @param replacePattern: Text to replace (regular expression)
	 * @return
	 */
	public static String replace (String text, String findPattern, String replacePattern){

		//Create the pattern for the search
		Pattern pattern = Pattern.compile(findPattern);

		// Replace all
		Matcher matcher = pattern.matcher(text);

		//Return
		return matcher.replaceAll(replacePattern);

	}

    public static String join(String firstItem, String secondItem, String operator){
        return join(firstItem, secondItem, operator, "", "");
    }

    public static String join(String firstItem, String secondItem, String operator, String delimiter){
        return join(firstItem, secondItem, operator, delimiter, delimiter);
    }

    public static String join(String firstItem, String secondItem, String operator, String firstDelimiter, String secondDelimiter ){

        firstItem = delimit(firstItem, firstDelimiter, secondDelimiter);

        secondItem = delimit(secondItem, firstDelimiter, secondDelimiter);

        if((firstItem != "") && (secondItem != "")){
            return firstItem + operator + secondItem;
        } else {
            return firstItem + secondItem;
        }


    }

    public static String delimit(String text, String firstDelimiter, String secondDelimiter){
        if((text == null) || (text.isEmpty())){ // replaced '(text == null)' to '(text.equals(""))'
            return "";
        } else {
            return firstDelimiter + text + secondDelimiter;
        }
    }
}
