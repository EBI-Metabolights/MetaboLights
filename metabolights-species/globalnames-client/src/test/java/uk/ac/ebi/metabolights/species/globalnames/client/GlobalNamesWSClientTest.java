/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/2/14 11:25 AM
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

import org.junit.Test;
import uk.ac.ebi.metabolights.species.globalnames.model.GlobalNamesResponse;
import uk.ac.ebi.metabolights.species.globalnames.model.Result;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * User: conesa
 * Date: 28/11/2013
 * Time: 17:17
 */
public class GlobalNamesWSClientTest {

	private static final String ISARIA_SINCLAIRII = "Isaria sinclairii";
	private static final String HYPERICUM_AUCHERI = "Hypericum aucheri";

	@Test
	public void testResolveName() throws Exception {

		GlobalNamesWSClient client = new GlobalNamesWSClient();

		// Expected result....
		// http://resolver.globalnames.org/name_resolvers.json?names=Isaria%20sinclairii&data_source_ids=5

		GlobalNamesResponse response =  client.resolveName(ISARIA_SINCLAIRII, GlobalNamesSources.Index_Fungorum);


		//assertEquals("Datasources must be 1" , 1, response.getData_sources().size());

		//assertEquals("Datasources must be Index Fungorium" , GlobalNamesSources.Index_Fungorum.getDataSourceId(), response.getData_sources().iterator().next().getId());

		// Test data
		assertEquals("Data must be 1" , 1, response.getData().size());


		Collection<Result> results = response.getData().iterator().next().getResults();

		// Test data
		assertEquals("Results must be 1" , 1, results.size());

		Result result = results.iterator().next();

		assertEquals("Test taxon id" , "254719", result.getTaxon_id());
		assertEquals("Test cannonical form" , ISARIA_SINCLAIRII, result.getCanonical_form());

		assertEquals("Test classification path" , "Fungi|Ascomycota|Sordariomycetes|Hypocreales|Cordycipitaceae|Isaria|Isaria sinclairii", result.getClassification_path());

	}

	@Test
	public void testResolveNameWhitoutSource() throws Exception {

		GlobalNamesWSClient client = new GlobalNamesWSClient();

		// Expected result....
		// http://resolver.globalnames.org/name_resolvers.json?names=Hypericum%20aucheri
		GlobalNamesResponse response =  client.resolveName (HYPERICUM_AUCHERI);

		// Test data
		assertEquals("Data must be 1" , 1, response.getData().size());


		Collection<Result> results = response.getData().iterator().next().getResults();

		// Test data: this might chenge with time
		assertEquals("Results must be 6" , 6, results.size());

	}
}
