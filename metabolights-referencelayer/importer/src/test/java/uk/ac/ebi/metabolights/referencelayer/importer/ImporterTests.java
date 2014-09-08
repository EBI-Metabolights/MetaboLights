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
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import uk.ac.ebi.biobabel.util.db.DatabaseInstance;

import java.io.File;
import java.net.URL;
import java.sql.Connection;

public class ImporterTests extends TestCase{

	protected static final Logger LOGGER = Logger.getLogger(ImporterTests.class);

	private Connection con;


	@Override
	@BeforeClass
	protected void setUp() throws Exception {

		// Set up a simple configuration that logs on the console.
	    BasicConfigurator.configure();

        DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsDEV");
//		DatabaseInstance dbi = DatabaseInstance.getInstance("metabolightsPROD");
		con = dbi.getConnection();


	}
	 @Override
	 @AfterClass
	protected void tearDown() throws Exception {
		if (con != null) con.close();

	}


	/*
	 */
	public void testImport() throws Exception {

        ReferenceLayerImporter rli = new ReferenceLayerImporter(con);

        // Set the root to anminoacid
        //rli.setChebiIDRoot("CHEBI:16449");
		//rli.setRelationshipType(RelationshipType.HAS_FUNCTIONAL_PARENT);

		rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL);
        rli.importMetabolitesFromChebi();

        /*
        Expected compounds are:
        CHEBI:177403-oxoalanine3CHEBI:48459N-(2,6-dichlorobenzoyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]alanine3CHEBI:48463N-(2,6-dichlorobenzoyl)-3-[6-(2,6-dimethoxyphenyl)-2-naphthyl]alanine3CHEBI:48473methyl 3-[2-(2,6-dichlorophenyl)quinolin-6-yl]alaninate3CHEBI:48475methyl N-(2,6-dichlorobenzyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]alaninate3CHEBI:48477methyl N-(2,6-dichlorobenzyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]-N-methylalaninate3CHEBI:48478N-(2,6-dichlorobenzyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]-N-methylalanine3CHEBI:48479N-(2,6-dichlorobenzoyl)-3-(2-phenoxy-6-quinolyl)alanine3CHEBI:48482methyl 3-(2-phenoxy-6-quinolyl)alaninate3CHEBI:48491methyl N-(tert-butoxycarbonyl)-3-[2-(2,6-dichlorophenyl)-6-quinolyl]alaninate3CHEBI:48492methyl N-(tert-butoxycarbonyl)-3-[2-(2,6-dichlorophenyl)-4-(phenylsulfanyl)-1,2,3,4,4a,8a-hexahydro-6-quinolyl]alaninate3
         */

	}
    public void testImportFromTSV() throws Exception {

        ReferenceLayerImporter rli = new ReferenceLayerImporter(con);

		// List of compounds that will need species to be refreshed
		//rli.setDeleteExistingCHEBISpecies(true);
		URL url = ImporterTests.class.getClassLoader().getResource("refresh_species_chebi_ids.tsv");

		// Duplicated human (NCBI + NEWT)
		//URL url = ImporterTests.class.getClassLoader().getResource("more_compounds_chebi_ids.tsv");




		// List from Ken's SQL query in CHEBI to get D- and L alanine....zwiterions not included:
		rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL- (ReferenceLayerImporter.ImportOptions.DO_FUZZY_SEARCH + ReferenceLayerImporter.ImportOptions.REFRESH_MET_SPECIES));
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

		ReferenceLayerImporter rli = new ReferenceLayerImporter(con);

		rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL);

		//rli.importMetaboliteFromChebiID("CHEBI:65640");
		//rli.importMetaboliteFromChebiID("CHEBI:16449");
		// To test import of "human metabolite"
		rli.importMetaboliteFromChebiID("CHEBI:4167");


	}

	public void testRefresh() throws Exception {

		ReferenceLayerImporter rli = new ReferenceLayerImporter(con);


		//rli.setImportOptions(ReferenceLayerImporter.ImportOptions.ALL);
		rli.refreshMTBLC();

	}



}
