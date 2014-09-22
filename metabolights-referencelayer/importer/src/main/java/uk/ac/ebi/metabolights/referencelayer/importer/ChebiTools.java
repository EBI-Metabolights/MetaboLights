/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/22/14 10:35 AM
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

package uk.ac.ebi.metabolights.referencelayer.importer;

import org.apache.log4j.Logger;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity;
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntityList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * User: conesa
 * Date: 22/09/2014
 * Time: 10:35
 */
public class ChebiTools {

	private static Logger LOGGER = Logger.getLogger(ChebiTools.class);
	public static ArrayList<String> chebiTsvToArrayList(File chebiTsvExport, int startFrom) {

		// Try to get the list of metabolites from chebi TSV...
		// SAMPLE:
		// ChEBI ID	ChEBI ASCII Name	Text Score	Tanimoto Similarity Score	Entry Status
		// CHEBI:36062	3,4-dihydroxybenzoic acid	7.22
		// CHEBI:1189	2-methoxyestrone	7.11
		// CHEBI:1387	3,4-dihydroxyphenylethyleneglycol	7.11
		// CHEBI:3648	chlorpromazine <i>N</i>-oxide	7.11

		Long linesRead = new Long(0);

		ArrayList<String> chebiIds = new ArrayList<String>();

		// Open and go through the file
		try {
			//Use a buffered reader
			BufferedReader reader = new BufferedReader(new FileReader(chebiTsvExport));
			String line;

			//Go through the file
			while ((line = reader.readLine()) != null) {

				// If its not the first line...skip it as it is the header definition line....
				if (linesRead >= startFrom) {
					// Get the Chebi id ==> First element in a line separated by Tabulators
					String[] values = line.split("\t");

					chebiIds.add(values[0]);


				}

				linesRead++;

			}

			//Close the reader
			reader.close();

			return chebiIds;

		} catch (Exception e) {
			LOGGER.error("Can't import Metabolites from chebi TSV.", e);
			return chebiIds;
		}
	}
	public static ArrayList<String> chebiTsvToArrayList(File chebiTsvExport){

		return chebiTsvToArrayList(chebiTsvExport,1);
	}

	public static LiteEntityList chebiArrayListToLiteEntityList(ArrayList<String> chebiArray){

		LiteEntityList chebiLiteEntityList = new LiteEntityList();

		for (String chebiId:chebiArray){
			LiteEntity liteEntity = new LiteEntity();
			liteEntity.setChebiId(chebiId);
			chebiLiteEntityList.getListElement().add(liteEntity);
		}

		return chebiLiteEntityList;
	}

	public static LiteEntityList chebiTsvToLiteEntityList(File tsv){
		ArrayList<String> chebiArray = chebiTsvToArrayList(tsv);

		return chebiArrayListToLiteEntityList(chebiArray);
	}
}
