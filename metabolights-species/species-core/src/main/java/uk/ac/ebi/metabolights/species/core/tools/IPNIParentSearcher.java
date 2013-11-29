package uk.ac.ebi.metabolights.species.core.tools;

import uk.ac.ebi.metabolights.species.model.Taxon;

/**
 * User: conesa
 * Date: 27/11/2013
 * Time: 15:10
 */
public class IPNIParentSearcher implements IParentSearcher {
	private static final String IPNI_PREFIX = "IPNI";

	@Override
	public Taxon getParentFromTaxon(Taxon child) {

		// Since IPNI deals with plants, and doesn't have a webservice...we can return always plant taxon (Viridiplantae)
		// GReat, IPNI hasn't got parent information, therefore it doesn't have such a taxon for viridiplantae.
		// SO,...I'll made one up: IPNI:1
		Taxon madeUpPlants = new Taxon(IPNI_PREFIX + ":1", "Viridiplantae","Green plants","");

		if (child.equals(madeUpPlants)){
			return null;
		} else {
			return madeUpPlants;
		}

	}

	@Override
	public boolean isThisTaxonYours(Taxon orphan) {
		return IPNI_PREFIX.equals(orphan.getPrefix());
	}
}
