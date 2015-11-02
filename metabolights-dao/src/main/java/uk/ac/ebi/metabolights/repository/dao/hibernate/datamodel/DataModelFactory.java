/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Jan-21
 * Modified by:   conesa
 *
 *
 * Copyright 2015 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.hibernate.datamodel;

import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.studyvalidator.Validation;

/**
 * User: conesa
 * Date: 21/01/15
 * Time: 10:14
 */
public class DataModelFactory {

	protected static StudyData getStudyDataInstance(Study study){

		StudyData studyData = new StudyData();

		initializeDataModel(studyData, study);

		return studyData;
	}

	private static void initializeDataModel(DataModel dataModel, Object bussinessModelEntity){

		dataModel.setBussinesModelEntity(bussinessModelEntity);

	}

	public static UserData getUserDataInstance(User user) {

		UserData userData = new UserData();

		initializeDataModel(userData, user);

		return userData;

	}

	public static ValidationData getValidationDataInstance(Validation validation) {

		ValidationData validationData = new ValidationData();

		initializeDataModel(validationData, validation);

		return validationData;

	}
}
