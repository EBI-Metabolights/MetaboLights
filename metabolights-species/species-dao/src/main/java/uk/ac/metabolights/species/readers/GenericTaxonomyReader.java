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

import uk.ac.ebi.metabolights.species.model.Taxon;
import uk.ac.ebi.metabolights.species.model.Taxonomy;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * User: conesa
 * Date: 29/10/2013
 * Time: 16:52
 */
public class GenericTaxonomyReader extends TaxonomyReader {

	// Have default config...
	GenericTaxonomyReaderConfigDataStructure configData;

	File taxonomyFile;

	public GenericTaxonomyReader(GenericTaxonomyReaderConfig config) throws ConfigurationException {

		this.configData = config.getConfigDataStructure();

		taxonomy = new Taxonomy(configData.getDescription(), configData.getTaxonomyId() ,configData.getVersion());

		taxonomyFile = new File(config.getTaxonomyPath());

		// Check file exist
		if (!taxonomyFile.exists()){
			throw new ConfigurationException("Taxonomy file does not exists. " + config.getTaxonomyPath());
		}

	}

	@Override
	protected Taxonomy instantiateTaxonomy() {

		// Should be instantiated in the constructor
		return taxonomy;

	}

	@Override
	public void loadTaxonomy() throws ConfigurationException {

		// Open the taxonomy file
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(taxonomyFile));


			int lineCount = 1;

			while ((sCurrentLine = br.readLine()) != null) {

				if (lineCount > configData.getNumberRowsToSkip()){
					Taxon currentTaxon = lineToTaxon(sCurrentLine);

					taxonRead(currentTaxon);
				}

				lineCount++;

			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {

			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}


	}

	private Taxon lineToTaxon (String line){

		String[] values = line.split(configData.getFieldSeparator());

		String id, name, commonName, parentId;

		id = values[getIndexByColumnName(GenericTaxonomyReaderConfigDataStructure.TaxonomyColumns.TAXON_ID)];
		name = values[getIndexByColumnName(GenericTaxonomyReaderConfigDataStructure.TaxonomyColumns.NAME)];
		commonName = values[getIndexByColumnName(GenericTaxonomyReaderConfigDataStructure.TaxonomyColumns.COMMON_NAME)];
		parentId = values[getIndexByColumnName(GenericTaxonomyReaderConfigDataStructure.TaxonomyColumns.PARENT_ID)];

		Taxon taxon = new Taxon(id,  name,  commonName,  parentId);

		return taxon;

	}

	private int getIndexByColumnName(GenericTaxonomyReaderConfigDataStructure.TaxonomyColumns column){

		return configData.getColumnPositions()[column.ordinal()];
	};

	public File getTaxonomyFile() {
		return taxonomyFile;
	}

	public void setTaxonomyFile(File taxonomyFile) {
		this.taxonomyFile = taxonomyFile;
	}

}
