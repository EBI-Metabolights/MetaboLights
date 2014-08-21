/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/28/13 4:34 PM
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
import static org.junit.Assert.*;

/**
 * User: conesa
 * Date: 28/11/2013
 * Time: 15:24
 */
public class NameResolversParamsTest {

	private static final String NAME_VALUE = "name";

	@Test
	public void testNameResolversParams() throws Exception {

		NameResolversParams params = new NameResolversParams(NAME_VALUE);

		assertEquals("Name from constructor test" , NAME_VALUE, params.getName());

		assertNull("Datasource is empty by default",  params.getDataSource());


	}

	@Test
	public void testSetName() throws Exception {

		NameResolversParams params = new NameResolversParams(NAME_VALUE, GlobalNamesSources.ITIS);


		params.setName(NAME_VALUE + 2);
		assertEquals("Name from constructor test", NAME_VALUE + 2, params.getName());

		assertEquals("Datasource is ITIS",GlobalNamesSources.ITIS, params.getDataSource());


	}

	@Test
	public void testToString() throws Exception {

		NameResolversParams params = new NameResolversParams(NAME_VALUE);

		assertEquals("toString with only Names test", NameResolversParams.NAMES_PARAM_NAME + "=" + NAME_VALUE + "\t", params.toString());

		// Add a second value
		params.getNames().add(NAME_VALUE);
		assertEquals("toString with more than one name Name test", NameResolversParams.NAMES_PARAM_NAME + "=" + NAME_VALUE + "\t" + NAME_VALUE +"\t", params.toString());

		// Add a data source
		params.setDataSource(GlobalNamesSources.Index_Fungorum);
		assertEquals("toString with name and data_source test",
				NameResolversParams.NAMES_PARAM_NAME + "=" + NAME_VALUE + "\t" + NAME_VALUE +"\t" + "&" + NameResolversParams.DATA_SOURCE_PARAM_NAME + "=" + GlobalNamesSources.Index_Fungorum.getDataSourceId(), params.toString());


		// Remove names
		params.setName("");
		assertEquals("toString with only datasource", NameResolversParams.DATA_SOURCE_PARAM_NAME + "=" + GlobalNamesSources.Index_Fungorum.getDataSourceId(), params.toString());



	}

}
