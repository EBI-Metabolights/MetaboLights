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

import static org.junit.Assert.*;
import org.junit.Test;
import uk.ac.ebi.metabolights.species.model.Taxon;

import javax.naming.ConfigurationException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * User: conesa
 * Date: 30/10/2013
 * Time: 10:36
 */
public class GenericTaxonomyReaderTest {

	private static final String TAXONOMY_TEST_FILE = "test_taxonomy.tsv";
	private static final String NEWT_TAXONOMY_TEST_FILE = "NEWT-taxonomy-test.tab";


	private GenericTaxonomyReader getGenericTaxonomyReader() throws ConfigurationException {

		return getGenericTaxonomyReader( new GenericTaxonomyReaderConfig(getTestFileFullPath(TAXONOMY_TEST_FILE),new GenericTaxonomyReaderConfigDataStructure(new String[]{"id", "version" , "description"})));
	}


	private GenericTaxonomyReader getGenericTaxonomyReader(GenericTaxonomyReaderConfig config) throws ConfigurationException {


		return  new GenericTaxonomyReader( config);
	}

	private String getTestFileFullPath(String fileName) {
		URL taxonomyTestUrl = GenericTaxonomyReaderTest.class.getClassLoader().getResource(fileName);

		return taxonomyTestUrl.getFile();
	}

	private GenericTaxonomyReader getGenericTaxonomyReaderForNEWT() throws ConfigurationException {

		return getGenericTaxonomyReader( new GenericTaxonomyReaderConfig(getTestFileFullPath(NEWT_TAXONOMY_TEST_FILE), GenericTaxonomyReaderConfig.KnownConfigs.NEWT));
	}

	@Test (expected = ConfigurationException.class)
	public void testGenericTaxonomyReader() throws ConfigurationException {


		GenericTaxonomyReader reader = new GenericTaxonomyReader(new GenericTaxonomyReaderConfig("path", new GenericTaxonomyReaderConfigDataStructure(new String[]{"id", "version" , "description"})));
	}

	@Test
	public void testInstantiateTaxonomy() throws Exception {

		GenericTaxonomyReader reader = getGenericTaxonomyReader();

		assertNotNull("Is taxonomy instantiated?", reader.instantiateTaxonomy());

	}

	@Test
	public void testDefaultsGetterAndSetters() throws Exception {

		GenericTaxonomyReader reader = getGenericTaxonomyReader();

		// Test default values....
		assertEquals("Default field separator must be tab", "\t", reader.configData.getFieldSeparator());

		assertArrayEquals("Default column positions must be 0,1,2,3", new int[]{0, 1, 2, 3}, reader.configData.getColumnPositions());


		// Test fieldSeparator getter setter
		reader.configData.setFieldSeparator(",");
		assertEquals("Field separator must be ," , ",", reader.configData.getFieldSeparator());

		// Test columnPositions
		int[] newPositions = new int[]{3,2,1,0};
		reader.configData.setColumnPositions(newPositions);
		assertArrayEquals("Test set columnPositions (int array version)", newPositions,reader.configData.getColumnPositions());

		newPositions = new int[]{4,5,6,7};
		String newPositionsS = "4,5,6,7";
		reader.configData.setColumnPositions(newPositionsS);
		assertArrayEquals("Test set columnPositions (single String parameter)", newPositions,reader.configData.getColumnPositions());


		newPositions = new int[]{8,9,10,11};
		newPositionsS = "8;9;10;11";
		reader.configData.setColumnPositions(newPositionsS, ";");
		assertArrayEquals("Test set columnPositions (specifying the sepatartor)", newPositions,reader.configData.getColumnPositions());

	}

	@Test
	public void testLoadTaxonomy() throws Exception {

		GenericTaxonomyReader reader = getGenericTaxonomyReader();

		Observer testObserver = new Observer() {
			@Override
			public void update(Observable observable, Object o) {

				TaxonomyReader taxonomyReader = (TaxonomyReader) observable;

				Taxon taxon = taxonomyReader.getCurrentTaxon();

				assertEquals("Taxon commonname read properly", "commonname" +taxon.getId(), taxon.getCommonName());
				assertEquals("Taxon name read properly", "name" +taxon.getId(), taxon.getName());
				assertEquals("Taxon parent read properly", taxon.getId(), taxon.getParentId());

			}
		};

		// Set the observer
		reader.addObserver(testObserver);

		reader.loadTaxonomy();



	}

	@Test
	public void testLoadNEWTTestTaxonomy() throws Exception {

		GenericTaxonomyReader reader = getGenericTaxonomyReaderForNEWT();

		Observer testObserver = new Observer() {
			int counter = 0;

			String[][] expectedData = new String[][]{
				new String[]{"85621", "16SrII (Peanut WB group)", "", "33926"},
				new String[]{"85623", "16SrIII (X-disease group)", "", "33926"},
				new String[]{"85624", "16SrIV (Coconut lethal yellows group)", "", "33926"},
				new String[]{"85629", "16SrIX (Pigeon pea witches'-broom group)", "", "33926"},
			};

			@Override
			public void update(Observable observable, Object o) {

				TaxonomyReader taxonomyReader = (TaxonomyReader) observable;

				Taxon taxon = taxonomyReader.getCurrentTaxon();

				if (counter < expectedData.length){

					assertEquals("NEWT Taxon Id read properly", expectedData[counter][0], taxon.getId());
					assertEquals("NEWT Taxon name read properly", expectedData[counter][1], taxon.getName());
					assertEquals("NEWT Taxon commonname read properly", expectedData[counter][2], taxon.getCommonName());
					assertEquals("NEWT Taxon parent read properly", expectedData[counter][3], taxon.getParentId());

				}
				counter++;
			}
		};

		// Set the observer
		reader.addObserver(testObserver);

		reader.loadTaxonomy();



	}

}
