package uk.ac.ebi.metabolights.metabolightsuploader;

import java.util.Arrays;

public class IsaTabIdReplacerException extends Exception{
	private String fileFormat;
	private String filePath;
	
	/**
	 * 
	 * @param msgHeader
	 * @param errors: First Item will be for the FileFormat, Second for the path.
	 */
	public IsaTabIdReplacerException(String msgHeader, String[] errors) {
		
		super(msgHeader + Arrays.toString(errors));
		
		//Populate the errors
		fileFormat = errors[0];
		filePath = errors[1];
		
		
	}
	public String getFileFormat(){
		return fileFormat;
	}

	public String getFilePath(){
		return filePath;
	}

}
