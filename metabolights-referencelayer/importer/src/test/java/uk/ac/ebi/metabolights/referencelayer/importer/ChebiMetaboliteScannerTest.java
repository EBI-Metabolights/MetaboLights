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
import uk.ac.ebi.chebi.webapps.chebiWS.model.LiteEntity;

import java.util.List;

/**
 * User: conesa
 * Date: 08/09/2014
 * Time: 14:14
 */
public class ChebiMetaboliteScannerTest {

	private String[] bileAcidMetabolitesExpected = {
				"CHEBI:48698", "CHEBI:63829"
				,"CHEBI:48700", "CHEBI:63831"
				,"CHEBI:48701","CHEBI:63838"
				,"CHEBI:48714", "CHEBI:63839"
				,"CHEBI:48715", "CHEBI:63830"
				,"CHEBI:48728", "CHEBI:63834"
				,"CHEBI:48731", "CHEBI:63833"
				,"CHEBI:48732", "CHEBI:63835"
				,"CHEBI:48734", "CHEBI:63848"
				,"CHEBI:48735", "CHEBI:63849"
				,"CHEBI:48736"
				,"CHEBI:48742", "CHEBI:63820"
				,"CHEBI:48778"
				,"CHEBI:48825"
				,"CHEBI:48833"
				,"CHEBI:48834"
				,"CHEBI:52432"
				,"CHEBI:15494", "CHEBI:48474"
				,"CHEBI:27458", "CHEBI:52050"
				,"CHEBI:27505", "CHEBI:52432"
				,"CHEBI:28540"
	};

	//Note: CHEBI:48474 is a conjugate base of CHEBI:58752 but we are not following that path,...shall we?
	//Same: CHEBI:59879 is a conjugate base of CHEBI:52432
	//Same: CHEBI:59807 is a conjugate base of CHEBI:52050


	protected static final Logger LOGGER = Logger.getLogger(ChebiMetaboliteScannerTest.class);

	@Test
	public void testScanBileAcidMetabolites() throws Exception {

		ChebiMetaboliteScanner chebiScanner = new ChebiMetaboliteScanner();


		List<LiteEntity> bileAcidMetabolites = chebiScanner.scan("CHEBI:48887");

		// Check results size with expected size
		Assert.assertEquals("Check bile acid metabolites scan size", bileAcidMetabolitesExpected.length, bileAcidMetabolites.size());

	}

	public void exportScanMetabolite() throws Exception {

		ChebiMetaboliteScanner chebiScanner = new ChebiMetaboliteScanner();

		List<LiteEntity> allMetabolites = chebiScanner.scan();

		System.out.println("\n\n\nOutput");

		for (LiteEntity entity: allMetabolites){
			System.out.println(entity.getChebiId());
		}


	}

}
