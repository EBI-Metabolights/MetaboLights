/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2/4/13 6:06 PM
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

package uk.ac.ebi.metabolights.webapp;

import uk.ac.ebi.metabolights.controller.FileDispatcherController;
import uk.ac.ebi.metabolights.search.LuceneSearchResult;
import uk.ac.ebi.metabolights.utils.PropertiesUtil;

import java.io.File;

public class StudyHealth {

	String identifier;
	boolean isPublic;
	String studyPath;
	boolean isThere;
	
	public StudyHealth(LuceneSearchResult study){
		identifier = study.getAccStudy();
		isPublic = study.getIsPublic();

		// Calculate the path where the study files are meant to be.
		studyPath = PropertiesUtil.getProperty("studiesLocation") + "/" + study;

		// Check if it is there
		isThere = new File(studyPath).exists();
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public String getStudyPath() {
		return studyPath;
	}

	public boolean getIsThere() {
		return isThere;
	}
	
}
