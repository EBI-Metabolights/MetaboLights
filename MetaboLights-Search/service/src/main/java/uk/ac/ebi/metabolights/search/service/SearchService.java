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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: conesa
 * Date: 02/12/14
 * Time: 09:00
 */
public abstract class SearchService<I> {

	protected final static Logger logger = LoggerFactory.getLogger(SearchService.class.getName());

	private Collection<EntityConverter<I>> entityConverters = new ArrayList<EntityConverter<I>>();

	// Abstract methods
	public abstract boolean getStatus();
	public abstract void delete(String id) throws IndexingFailureException;
	public abstract SearchResult<I> search(SearchQuery query);
	protected abstract void indexThis(I indexEntity) throws IndexingFailureException;
	protected abstract void addEntityConverters();
	public SearchService(){
		addEntityConverters();
	}
	public void index(Object entity) throws IndexingFailureException {

		EntityConverter<I> chosenConverter= null;

		// Loop through the EntityConverters collection
		for (EntityConverter<I> converter: entityConverters){
			if (converter.canYouHandleThis(entity)){
				chosenConverter = converter;
				break;
			}
		}

		if (chosenConverter != null) {

			I indexEnity = null;
			try {
				indexEnity = chosenConverter.convert(entity);
			} catch (EntityConversionException e) {
				e.printStackTrace();
			}
			indexThis(indexEnity);

		} else {

			throw new IndexingFailureException("Couldn't find any EntityConverter for entity passed.");
		}

	}
}
