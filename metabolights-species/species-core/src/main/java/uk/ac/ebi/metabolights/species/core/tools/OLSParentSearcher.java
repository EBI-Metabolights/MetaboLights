/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 10/3/14 9:35 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * Y
 * ou may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/21/13 5:06 PM
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

package uk.ac.ebi.metabolights.species.core.tools;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import uk.ac.ebi.metabolights.species.model.Taxon;
import uk.ac.ebi.ols.soap.Query;
import uk.ac.ebi.ols.soap.QueryService;
import uk.ac.ebi.ols.soap.QueryServiceLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class OLSParentSearcher implements IParentSearcher {

	static Logger logger = LoggerFactory.getLogger(OLSParentSearcher.class);

	private QueryServiceLocator locator;

	private final String QUERY_PREFIX = "NEWT";
	private ArrayList<String> validPrefixes = new ArrayList<String>(Arrays.asList(new String []{QUERY_PREFIX, "NCBI"}));

	private QueryServiceLocator getLocator() {

		if (locator == null){
			locator = new QueryServiceLocator();
		}

		return locator;
	}

	@Override
	public Taxon getParentFromTaxon(Taxon child) {

		Taxon parent = null;

		try {

			QueryService locator = getLocator();
			Query qs = locator.getOntologyQuery();

			//Map map = qs.getTermsByName("Homo", "NEWT", false);
			// We will query always for NEWT...although if it's a NCBI taxon.
			Map map = qs.getTermParents(child.getRecordIdentifier(), QUERY_PREFIX);

			// If there's no parent (root elements)
			if (map.size() == 0) {
				logger.warn("Taxon " + child.getId() + " hasn't any NEWT parent.");
				return null;
			}

			// There should be only one parent: get the key
			String key = (String) map.keySet().iterator().next();

			String name = (String) map.get(key);

			// Create a taxon based on this data
			parent = new Taxon("", name, "", "");

			// Prefix will always be NEWT.
			parent.setPrefix(QUERY_PREFIX);
			parent.setRecordIdentifier(key);

			logger.debug("Taxon " + child.getId() + " has a NEWT parent:" + parent.getId() + " - " + parent.getName());
			return parent;


		} catch (Exception e) {
			e.printStackTrace();
		}

		return parent;
	}

	@Override
	public boolean isThisTaxonYours(Taxon orphan) {

		boolean valid = validPrefixes.contains(orphan.getPrefix());

		return valid;
	}
}
