/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-12
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.search.service.imp.es.resultsmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import uk.ac.ebi.metabolights.search.service.imp.es.LiteEntity;

import java.util.Date;

/**
 * User: conesa
 * Date: 12/12/14
 * Time: 14:32
 */
public class LiteStudy extends LiteEntity {

	private String studyIdentifier;
	private String title;

	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z")
	private Date studyPublicReleaseDate;
	public String getStudyIdentifier() {
		return studyIdentifier;
	}

	public void setStudyIdentifier(String id) {
		this.studyIdentifier = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStudyPublicReleaseDate() {
		return studyPublicReleaseDate;
	}

	public void setStudyPublicReleaseDate(Date studyPublicReleaseDate) {
		this.studyPublicReleaseDate = studyPublicReleaseDate;
	}
}
