/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Aug-21
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

package uk.ac.ebi.metabolights.repository.model;

/**
 * User: conesa
 * Date: 09/06/2014
 * Time: 10:03
 */
/**
 * Represents a role
 */
public enum AppRole {

	ROLE_SUBMITTER (0),
	ROLE_SUPER_USER (1),
	ANONYMOUS(2);

	private final int bit;

	/**
	 * Creates an authority with a specific bit representation. It's important that this doesn't
	 * change as it will be used in the database. The enum ordinal is less reliable as the enum may be
	 * reordered or have new roles inserted which would change the ordinal values.
	 *
	 * @param bit the permission bit which will represent this authority in the datastore.
	 */
	AppRole(int bit) {
		this.bit = bit;
	}

	public int getBit() {
		return bit;
	}

	public String getName() {
		return toString();
	}
}
