/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/29/14 5:24 PM
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
