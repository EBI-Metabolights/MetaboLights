/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/8/14 2:14 PM
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
import org.junit.Assert;
import org.junit.Test;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: conesa
 * Date: 08/09/2014
 * Time: 14:14
 */
public class ChebiMetaboliteScannerTest {

	private String[] bileAcidMetabolitesExpected = {
			"CHEBI:48887"
			,"CHEBI:48698", "CHEBI:63829"
			, "CHEBI:48700", "CHEBI:63831"
			, "CHEBI:48701", "CHEBI:63838"
			, "CHEBI:48714", "CHEBI:63839"
			, "CHEBI:48715", "CHEBI:63830"
			, "CHEBI:48728", "CHEBI:63834"
			, "CHEBI:48731", "CHEBI:63833"
			, "CHEBI:48732", "CHEBI:63835"
			, "CHEBI:48734", "CHEBI:63848"
			, "CHEBI:48735", "CHEBI:63849"
			, "CHEBI:48736"
			, "CHEBI:48742", "CHEBI:63820", "CHEBI:63824", "CHEBI:63823"
			, "CHEBI:48778"
			, "CHEBI:48825"
			, "CHEBI:48833"
			, "CHEBI:48834"
			, "CHEBI:52432"
			, "CHEBI:15494", "CHEBI:48474", "CHEBI:58752"
			, "CHEBI:27458", "CHEBI:52050", "CHEBI:59807"
			, "CHEBI:27505", "CHEBI:59879"
			, "CHEBI:28540"
	};

	private String[] alanineMetabolitesExpected = {
			"CHEBI:16449",
			"CHEBI:15570",
			"CHEBI:16977",
			"CHEBI:76050",
			"CHEBI:32440",
			"CHEBI:32439",
			"CHEBI:66916",
			"CHEBI:32436",
			"CHEBI:32435",
			"CHEBI:57416",
			"CHEBI:32432",
			"CHEBI:32431",
			"CHEBI:57972"
	};

	protected static final Logger LOGGER = Logger.getLogger(ChebiMetaboliteScannerTest.class);

	@Test
	public void testScanAlanineBranch() throws Exception {

		testChebiBranch(alanineMetabolitesExpected, "CHEBI:16449", "Alanine branch tests");
	}

	@Test
	public void testBileAcidMetabolites() throws Exception {

		testChebiBranch(bileAcidMetabolitesExpected,"CHEBI:48887", "bile acid metabolites branch tests");
	}


	@Test
	public void testOutputAllMetabolites() throws Exception {

		ChebiMetaboliteScanner scanner = new ChebiMetaboliteScanner();

		Map<String,Entity> metabolites = scanner.scan();

		printEntities(metabolites.keySet().toArray(new String[metabolites.size()]), "All metabolites");
	}

	private void testChebiBranch(String[] expected, String chebiId, String message) throws ChebiWebServiceFault_Exception, MalformedURLException {

		ChebiMetaboliteScanner chebiScanner = new ChebiMetaboliteScanner();


		Map<String, Entity> results = chebiScanner.scan(chebiId);

		checkResults(results, expected);

		// Check results size with expected size
		Assert.assertEquals(message, expected.length, results.size());

	}

	@Test
	public void printExpected(){
		printEntities(bileAcidMetabolitesExpected, "bile acid expected");
	}

	private void checkResults(Map<String, Entity> result, String[] expected) {


		Set<String> keysSet = result.keySet();
		String[] keys = keysSet.toArray(new String[keysSet.size()]);

		// Substract expected from results
		substractArray(keys,expected, "Results have some items not found in expected");

		// Substract results from expected
		substractArray(expected,keys, "Expected have some items not found in Results");

	}

	private void substractArray (String[] mainData, String[] elmentsToSubstract, String header){

		Set<String> substraction;
		substraction = new HashSet<String>(Arrays.asList(mainData));

		substraction.removeAll(Arrays.asList(elmentsToSubstract));

		if (substraction.size()!= 0 ){

			printEntities(substraction.toArray(new String[substraction.size()]), header);

			Assert.fail(header);
		}

	}

	private void printEntities(String[] entities, String header) {

		System.out.println(header);

		for (int index = 0; index < entities.length; index++) {

			System.out.println(entities[index]);
		}

	}
}
