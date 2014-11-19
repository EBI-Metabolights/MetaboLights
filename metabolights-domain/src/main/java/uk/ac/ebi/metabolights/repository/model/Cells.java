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
 * User: conesa
 * Date: 04/11/14
 * Time: 16:43
 */
public class Cells implements Iterator<Cell> {

	private Row row;
	private Iterator<Field> fields;
	public Cells(Row row ){

		this.row = row;
		this.fields = row.getTable().getFields().values().iterator();

	}

	@Override
	public boolean hasNext() {
		return fields.hasNext();
	}

	@Override
	public Cell next() {

		if (! hasNext())   throw new NoSuchElementException();

		Cell cell = new Cell(row, fields.next());

		return cell;
	}

	@Override
	public void remove() {

	}
}
