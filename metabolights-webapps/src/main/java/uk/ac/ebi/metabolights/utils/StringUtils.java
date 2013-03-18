package uk.ac.ebi.metabolights.utils;

import java.util.Collection;
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
			
		}else{
			
			//Get the position from the end plus the length of the string we are finding
			pos = text.lastIndexOf(find);
			
		}

		//If not found
		if (pos==-1){return "";}
		
		//If we want to remove the right part...
		if (!fromTheLeft) {
			//...truncate "pos" has to be from the end
			pos = text.length() - pos;
		}else{
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
//	/**
//	 * Joins all the Strings in a collection and returns it.
//	 * @param c
//	 * @return
//	 */
//	public String join (Collection<String> c) {
//	    StringBuilder sb=new StringBuilder();
//	    for(String s: c)
//	        sb.append(s);
//	    return sb.toString();
//	}

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
