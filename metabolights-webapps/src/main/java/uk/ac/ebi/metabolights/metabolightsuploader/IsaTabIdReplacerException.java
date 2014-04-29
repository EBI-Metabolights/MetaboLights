/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/23/14 8:49 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.metabolightsuploader;

import org.apache.commons.lang.StringUtils;

public class IsaTabIdReplacerException extends Exception{
	private String fileFormat;
	private String filePath;

	/**
	 *
	 * @param msgHeader
	 * @param errors: First Item will be for the FileFormat, Second for the path.
	 */
	public IsaTabIdReplacerException(String msgHeader, String[] errors) {

		//super(msgHeader + Arrays.toString(errors));
		super(msgHeader + StringUtils.join(errors));

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
