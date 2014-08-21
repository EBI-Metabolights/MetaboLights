/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/2/14 10:56 AM
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

import uk.ac.ebi.metabolights.species.model.Taxon;

import java.util.ArrayList;
import java.util.List;

public class TaxonConverter {
	public static List<Taxon> stringToTaxonList(String taxonsIdString, String separator) {

		String[] taxonsId = taxonsIdString.split(separator);

		return stringArrayToTaxonList(taxonsId);

	}

	public static List<Taxon> stringToTaxonList(String taxonsIdString) {

		return stringToTaxonList(taxonsIdString, "~");
	}

	public static List<Taxon> stringArrayToTaxonList(String[] taxonsIdString) {

		ArrayList<Taxon> taxons = new ArrayList<Taxon>();

		for (String taxonId : taxonsIdString) {
			Taxon taxon = stringToTaxon(taxonId);

			taxons.add(taxon);
		}

		return taxons;
	}

	public static Taxon stringToTaxon(String taxonId) {
		return new Taxon(taxonId, "", "", "");
	}
	public static Taxon stringToTaxon(String taxonId, String name) {
		return new Taxon(taxonId, name, "", "");
	}

}