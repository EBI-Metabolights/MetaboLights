/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/14/13 11:41 AM
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

package uk.ac.ebi.metabolights.species.model;

/**
 * User: conesa
 * Date: 29/10/2013
 * Time: 12:45
 */
public class Taxonomy {
	String description;
	String id;
	String version;
	String matchingPattern;

	public Taxonomy(String description, String id, String version) {
		this.description = description;
		this.id = id;
		this.version = version;
		/* By default pattern will be id:\d+ (Id + colon + 1 or more digit) */
		this.matchingPattern = id + ":\\d+";
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMatchingPattern() {
		return matchingPattern;
	}

	public void setMatchingPattern(String matchingPattern) {
		this.matchingPattern = matchingPattern;
	}

	public boolean isThisYourTaxon(Taxon taxon){

		return taxon.getId().matches(matchingPattern);

	}
}
