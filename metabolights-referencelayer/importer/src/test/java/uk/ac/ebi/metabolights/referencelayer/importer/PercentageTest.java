/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/25/14 4:33 PM
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

import org.junit.Assert;
import org.junit.Test;

/**
 * User: conesa
 * Date: 25/09/2014
 * Time: 16:33
 */
public class PercentageTest {
	@Test
	public void testGetTotal() throws Exception {

		Percentage perc = new Percentage();
		Assert.assertEquals("Total default value is 100", 100, perc.getTotal());

		perc.setTotal(1000);
		Assert.assertEquals("Total set is 1000", 1000, perc.getTotal());

		perc = new Percentage(50);
		Assert.assertEquals("Total is set at instantiation", 50, perc.getTotal());

	}

	@Test
	public void testGetCount() throws Exception {

		Percentage perc = new Percentage();
		Assert.assertEquals("Count default value is 0", 0, perc.getCount());

		perc.oneMore();
		Assert.assertEquals("Onemore method increases count by one", 1, perc.getCount());

		perc.setCount(5);
		Assert.assertEquals("set count works", 5, perc.getCount());


	}

	@Test
	public void testGetPercentage() throws Exception {

		Percentage perc = new Percentage(50);
		Assert.assertEquals("Percentage test", "0", perc.getPercentage());
		perc.oneMore();
		Assert.assertEquals("Percentage test", "2", perc.getPercentage());

	}
}
