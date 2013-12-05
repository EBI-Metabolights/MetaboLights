package uk.ac.ebi.metabolights.species.core.tools;

import uk.ac.ebi.metabolights.species.model.Taxon;

import java.util.Arrays;
import java.util.List;

/**
 * User: conesa
 * Date: 27/11/2013
 * Time: 15:10
 */
public class FungaeParentSearcher implements IParentSearcher {
	private static final List<String> PREFIX = Arrays.asList(new String[]{"MB", "IF"});
	public static final String FUNGAE_KINGDOM = "IF:90157";

	@Override
	public Taxon getParentFromTaxon(Taxon child) {

		// Since IPNI deals with plants, and doesn't have a webservice...we can return always plant taxon (Viridiplantae)
		// GReat, IPNI hasn't got parent information, therefore it doesn't have such a taxon for viridiplantae.
		// SO,...I'll made one up: IPNI:1
		Taxon madeUpPlants = new Taxon(FUNGAE_KINGDOM, "Fungi","IF & MB Fungi root","");

		if (child.equals(madeUpPlants)){
			return null;
		} else {
			return madeUpPlants;
		}

	}

	@Override
	public boolean isThisTaxonYours(Taxon orphan) {
		return PREFIX.contains(orphan.getPrefix());
	}
}
