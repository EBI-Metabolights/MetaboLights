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

package uk.ac.ebi.metabolights.species.search;

import java.io.File;

/**
 * User: conesa
 * Date: 04/11/2013
 * Time: 11:48
 */
public class Configuration {

	// Singleton instance
	private static Configuration instance;

	// Root folder where lucene index will reside...
	private File rootIndexFolder;

	public static void setInstance(Configuration instance) {
		Configuration.instance = instance;
	}

	public static Configuration getInstance(){
		if (instance == null){
			instance = new Configuration();
		}

		return instance;
	}

	private Configuration(){};

	public File getRootIndexFolder() {
		return rootIndexFolder;
	}

	public void setRootIndexFolder(File rootIndexFolder) {
		this.rootIndexFolder = rootIndexFolder;
	}
}
