/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/1/14 12:28 PM
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

package uk.ac.ebi.metabolights.species.globalnames.client;

public	enum GlobalNamesSources {
	ITIS(3, "ITIS"),
	NCBI(4, "NCBI"),
	Index_Fungorum(5, "IF"),
	WoRMS(9, "WORMS"),
	//ZooBank(132,),
	The_International_Plant_Names_Index(167, "IPNI");

	private final int dataSourceId;
	private final String prefix;

	GlobalNamesSources(int dataSourceId, String prefix) {
		this.dataSourceId = dataSourceId;
		this.prefix = prefix;
	}

	public int getDataSourceId() {
		return dataSourceId;
	}
}
