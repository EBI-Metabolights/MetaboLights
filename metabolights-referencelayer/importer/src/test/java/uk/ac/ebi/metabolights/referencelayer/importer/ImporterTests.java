/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-19
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/23/14 5:31 PM
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

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

public class ImporterTests extends TestCase{

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImporterTests.class);

	private ConnectionProvider connectionProvider;


	@Override
	@BeforeClass
	protected void setUp() throws Exception {

		connectionProvider = new ConnectionProvider() {
			@Override
			public Connection getConnection() {
				// Set up a simple configuration that logs on the console.
				BasicConfigurator.configure();

				DatabaseInstance dbi = null;
				try {
//					dbi = DatabaseInstance.getInstance("metabolightsDEV");
					dbi = DatabaseInstance.getInstance("metabolightsPROD");
				} catch (IOException e) {
					e.printStackTrace();
				}

				return dbi.getConnection();

			}
		};


	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (connectionProvider != null) connectionProvider.getConnection().close();

	}


	/*
	 */
	public void testImport() throws Exception {

        ReferenceLayerImporter rli = new ReferenceLayerImporter(connectionProvider);

        //rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL);
        //rli.setImportOptions(ReferenceLayerImporter.ImportOptions.REFRESH_MET_SPECIES);

		// By deafeult use "metabolite"
		String chebiId= rli.getChebiIDRoot();

		// Bile Acid metabolites
		//String chebiId = "CHEBI:48887";

		// Plant metabolite: CHEBI:76924
		//String chebiId = "CHEBI:76924";

		rli.setChebiIDRoot(chebiId);

//		rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL-ReferenceLayerImporter.ImportOptions.UPDATE_EXISTING_MET);
        rli.importMetabolitesFromChebi();


	}
    public void testImportFromTSV() throws Exception {

        ReferenceLayerImporter rli = new ReferenceLayerImporter(connectionProvider);

		// List of compounds that will need species to be refreshed
		//rli.setDeleteExistingCHEBISpecies(true);
		URL url = ImporterTests.class.getClassLoader().getResource("refresh_species_chebi_ids.tsv");

		// Duplicated human (NCBI + NEWT)
		//URL url = ImporterTests.class.getClassLoader().getResource("more_compounds_chebi_ids.tsv");




		// List from Ken's SQL query in CHEBI to get D- and L alanine....zwiterions not included:
		rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL-  ReferenceLayerImporter.ImportOptions.REFRESH_MET_SPECIES);
//		URL url = ImporterTests.class.getClassLoader().getResource("chebi_metabolites.tsv");
        //URL url = ImporterTests.class.getClassLoader().getResource("ChEBI_Results_Metabolites.tsv");
        //URL url = ImporterTests.class.getClassLoader().getResource("ChEBI_Results_Metabolites_20130607.tsv");
        //URL url = ImporterTests.class.getClassLoader().getResource("BMR-NMR-not_in_chebi.tsv");

        if (url == null) {
            // error - missing folder
        } else {
            File chebiTSV = new File(url.getFile());

            rli.importMetabolitesFromChebiTSV(chebiTSV, 10890);

        }

    }

	public void testImportSingleMolecule() throws Exception {

		ReferenceLayerImporter rli = new ReferenceLayerImporter(connectionProvider);

		rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL);

		//rli.importMetaboliteFromChebiID("CHEBI:65640");
		//rli.importMetaboliteFromChebiID("CHEBI:16449");
		// To test import of "human metabolite"
		rli.chebiID2MetaboLights("CHEBI:4167");


	}

	public void testRefresh() throws Exception {

		ReferenceLayerImporter rli = new ReferenceLayerImporter(connectionProvider);


		//rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL);
		rli.refreshMTBLC();

	}



}
