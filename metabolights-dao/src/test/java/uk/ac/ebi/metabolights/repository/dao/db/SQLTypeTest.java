/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-23
 * Modified by:   conesa
 *
 *
 * Copyright 2014 EMBL-European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.repository.dao.db;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class SQLTypeTest {

	@Test
	public void testToString() throws Exception {


		SQLType type = SQLType.VARCHAR;

		String valueS = "value";
		Assert.assertEquals("SQLType varchar to String", "'" + valueS + "'" , type.toString(valueS));

		Integer valueI = 10;
		type = SQLType.NUMBER;
		Assert.assertEquals("SQLType number to String", valueI.toString()  , type.toString(valueI));

		Date valueD =  new Date();
		type = SQLType.DATE;
		Assert.assertEquals("SQLType date to String", "'" + valueD + "'" , type.toString(valueD));


	}
}