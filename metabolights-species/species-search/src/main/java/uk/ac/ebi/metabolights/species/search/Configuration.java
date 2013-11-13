/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 12/11/13 11:00
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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
