/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/3/13 5:26 PM
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

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

/**
 * User: conesa
 * Date: 03/12/2013
 * Time: 16:48
 */
public class OntologyLookUpServiceTest {
	private static OntologyLookUpService lus;


	@BeforeClass
	public static void setUp(){
		lus = new OntologyLookUpService();
	}
	@Test
	public void testGetTermsByName() throws Exception {



		Map<String,String> res = lus.getTermsByName("brain", "EFO");
		for (String tissue : res.keySet()) {
			System.out.println(tissue+"\t"+res.get(tissue));
		}

	}

	@Test
	public void testGetTermsPrefixed() throws Exception {



		Map<String,String> res2 = lus.getTermsPrefixed("brain");
		for (String tissue : res2.keySet()) {
			System.out.println(tissue+"\t"+res2.get(tissue));
		}

	}

	@Test
	public void testGetTermName() throws Exception {


		System.out.println("BTO trial :"+lus.getTermName("BTO:0000309", "BTO"));


		// ATENTION: For NEWT you shouldn't send the prefix!!!
		System.out.println("NEWT human :"+lus.getTermName("NEWT:9606", "NEWT"));

	}
}
