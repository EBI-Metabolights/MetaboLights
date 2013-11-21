package uk.ac.ebi.metabolights.species.core.tools;

import uk.ac.ebi.metabolights.species.model.Taxon;

/**
 * User: conesa
 * Date: 21/11/2013
 * Time: 15:47
 */
public interface IParentSearcher {
	public Taxon getParentFromTaxon(Taxon child);
	public boolean isThisTaxonYours(Taxon orphan);
}
