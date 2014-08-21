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

package uk.ac.ebi.metabolights.checklists;

public enum SubmissionProcessCheckListSeed implements ICheckListItemSeed{
	FILEUPLOAD ("1", "File upload"),
	FILEVALIDATION("2", "File basic validation"),
	FILEUNZIP("3","File unzip"),
	CONTENTVALIDATION("4","Experiment data validation"),
	IDREPLACEMENTS("5","Metabolights Id assignment"),
	FILEPERSISTENCE("6","File persistence"),
	SETPERMISSIONS ("7","Permission and visibility set up")	
	;
	
	private final String key;
	private final String title;
	
	private SubmissionProcessCheckListSeed(String key, String title){
		this.key = key;
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}
}
