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