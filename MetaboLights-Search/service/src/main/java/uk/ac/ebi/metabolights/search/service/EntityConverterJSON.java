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

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * User: conesa
 * Date: 02/12/14
 * Time: 13:38
 */
public class EntityConverterJSON implements EntityConverter<String> {
	@Override
	public boolean canYouHandleThis(Object entity) {
		// In theory should be able to handle everything
		return true;
	}

	@Override
	public String convert(Object entity) throws EntityConversionException {

		ObjectMapper mapper = new ObjectMapper();

		try {
			String output = mapper.writeValueAsString(entity);
			return output;

		} catch (com.fasterxml.jackson.core.JsonProcessingException e) {
			throw new EntityConversionException(e);
		}
	}
}
