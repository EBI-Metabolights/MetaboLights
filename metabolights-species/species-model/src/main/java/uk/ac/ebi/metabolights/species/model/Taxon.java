/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/1/14 4:24 PM
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
 * Time: 12:49
 */
public class Taxon {

	public static final String DEFAULT_SEPATATOR = ":";

	String name;
	String commonName;
	String parentId;
	String prefix;
	String recordIdentifier;
	String idSeparator = DEFAULT_SEPATATOR;

	public Taxon(String id, String name, String commonName, String parentId) {
		splitId(id);
		this.name = name;
		this.commonName = commonName;
		this.parentId = parentId;
	}

	public String getId() {

		return composeId();
	}

	public void setId(String id) {
		splitId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	private void splitId(String id) {

		String[] fragments = id.split(idSeparator);

		if (fragments.length>1){
			prefix = fragments[0];
			recordIdentifier = fragments[1];
		} else {
			prefix = "";
			recordIdentifier = fragments[0];
		}

	}
	private String composeId(){

		if (prefix.equals("")){
			return recordIdentifier;
		} else {
			return prefix + idSeparator + recordIdentifier;
		}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getRecordIdentifier() {
		return recordIdentifier;
	}

	public void setRecordIdentifier(String recordIdentifier) {
		this.recordIdentifier = recordIdentifier;
	}

	public String getIdSeparator() {
		return idSeparator;
	}

	public void setIdSeparator(String idSeparator) {
		this.idSeparator = idSeparator;
	}

	@Override
	public boolean equals(Object obj){

		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Taxon))
			return false;

		Taxon attribute = (Taxon)obj;

		return attribute.getId().equals(this.getId());

	}

	@Override
	public String toString (){
		return name + "' " + composeId();
	}
}
