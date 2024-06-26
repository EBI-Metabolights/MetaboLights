/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Dec-02
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

package uk.ac.ebi.metabolights.search.service;

import org.junit.Assert;
import org.junit.Test;

public class PaginationTest {

	@Test
	public void testGetPageCount() throws Exception {

		Pagination pagination = new Pagination(100);

		Assert.assertEquals("Page count exact pages", 10, pagination.getPageCount());
		Assert.assertEquals("First page item", 1, pagination.getFirstPageItemNumber());
		Assert.assertEquals("Last page item", 10, pagination.getLastPageItemNumber());

		pagination.setPage(10);
		Assert.assertEquals("First page item", 91, pagination.getFirstPageItemNumber());
		Assert.assertEquals("Last page item", 100, pagination.getLastPageItemNumber());



		pagination.setItemsCount(101);
		Assert.assertEquals("Page count for 101 items", 11, pagination.getPageCount());

		pagination.setPage(11);
		Assert.assertEquals("First page item", 101, pagination.getFirstPageItemNumber());
		Assert.assertEquals("Last page item", 101, pagination.getLastPageItemNumber());


		pagination.setItemsCount(99);
		Assert.assertEquals("Page count for 101 items", 10, pagination.getPageCount());

		pagination.setPageSize(9);
		Assert.assertEquals("Page count for 99 items, page size 9", 11, pagination.getPageCount());

		Assert.assertEquals("First page item", 91, pagination.getFirstPageItemNumber());
		Assert.assertEquals("Last page item", 99, pagination.getLastPageItemNumber());






	}
}