/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/14/13 11:39 AM
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

package uk.ac.metabolights.species.readers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * User: conesa
 * Date: 31/10/2013
 * Time: 10:04
 */
public class GenericTaxonomyReaderConfig {

	static Logger logger = LogManager.getLogger(GenericTaxonomyReaderConfig.class);

	public enum KnownConfigs{
		NEWT(new GenericTaxonomyReaderConfigDataStructure(new String[]{"NEWT","2013-10", "Uniprot taxonomy:Organisms are classified in a hierarchical tree structure. Our taxonomy database contains every node (taxon) of the tree. UniProtKB taxonomy data is manually curated.", "0,2,3,9" }));

		private GenericTaxonomyReaderConfigDataStructure dataStructure;

		KnownConfigs(GenericTaxonomyReaderConfigDataStructure dataStructure){
			this.dataStructure = dataStructure;
		}

		public GenericTaxonomyReaderConfigDataStructure getDataStructure() {
			return dataStructure;
		}
	}

	private GenericTaxonomyReaderConfigDataStructure configDataStructure;
	private String taxonomyPath;

	public GenericTaxonomyReaderConfig(String taxonomyPath, GenericTaxonomyReaderConfigDataStructure configDataStructure){

		this.configDataStructure = configDataStructure;
		this.taxonomyPath = taxonomyPath;

	}

	public GenericTaxonomyReaderConfig(String taxonomyPath, KnownConfigs knownConfigs){

		this.taxonomyPath = taxonomyPath;
		this.configDataStructure = knownConfigs.dataStructure;

	}
	public GenericTaxonomyReaderConfig(String taxonomyPath){
		this.taxonomyPath = taxonomyPath;
	}

	public GenericTaxonomyReaderConfigDataStructure getConfigDataStructure() {
		return configDataStructure;
	}

	public void setConfigDataStructure(GenericTaxonomyReaderConfigDataStructure configDataStructure) {
		this.configDataStructure = configDataStructure;
	}

	public String getTaxonomyPath() {
		return taxonomyPath;
	}

	public void setTaxonomyPath(String taxonomyPath) {
		this.taxonomyPath = taxonomyPath;
	}
}
