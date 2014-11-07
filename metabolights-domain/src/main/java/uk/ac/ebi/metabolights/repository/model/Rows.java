/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2014-Nov-04
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

package uk.ac.ebi.metabolights.repository.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * Iterator for table rows
 * User: conesa
 * Date: 04/11/14
 * Time: 16:16
 */
public class Rows implements Iterator<Row>{

	private Table table;
	private int current;

	public Rows(Table table ){
		this.table = table;
	}

	@Override
	public boolean hasNext() {
		return current < table.getData().size();
	}

	@Override
	public Row next() {

		if (! hasNext())   throw new NoSuchElementException();


		return new Row(table, current++);
	}

	@Override
	public void remove() {

	}
}
