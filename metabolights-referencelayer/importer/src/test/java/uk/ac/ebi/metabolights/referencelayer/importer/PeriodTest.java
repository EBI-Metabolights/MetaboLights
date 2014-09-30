/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/25/14 4:49 PM
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

import java.util.Date;

/**
 * User: conesa
 * Date: 25/09/2014
 * Time: 16:49
 */
public class PeriodTest {
	@Test
	public void testDefaults() throws Exception {

		Period period = new Period();

		Assert.assertNull("Start is null by default", period.getStart());
		Assert.assertNull("End is null by default", period.getEnd());
		Assert.assertEquals("Duration is 0", 0, period.getDuration());


	}

	@Test
	public void testGetStart() throws Exception {

		Period period = new Period();

		Date tmpDate = new Date();
		period.setStart(tmpDate);

		Assert.assertEquals("Start set works", tmpDate ,period.getStart());

		Thread.sleep(1000);

		Assert.assertEquals("Duration must be 1", 1 ,period.getDuration());




	}

	@Test
	public void testGetEnd() throws Exception {

		Period period = new Period();

		period.setEnd(new Date(1));

		Assert.assertEquals("End set works", new Date(1) ,period.getEnd());

		Thread.sleep(1000);

		Assert.assertEquals("Duration must be 0", 0 ,period.getDuration());

	}


	@Test
	public void testGetDuration() throws Exception {

		Period period = new Period();

		period.setStart(new Date(0));

		Assert.assertTrue("Duration should be more than 0", period.getDuration()>0);

		period.setEnd(new Date());
		period.setStart(null);
		Assert.assertEquals("Duration must be 0", 0 ,period.getDuration());

		period.setStart(new Date(period.getEnd().getTime()-1000));
		Assert.assertEquals("Duration is 1 ", 1 ,period.getDuration());



	}
}
