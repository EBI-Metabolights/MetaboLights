package uk.ac.metabolights.species.readers;

import uk.ac.ebi.metabolights.species.model.Taxon;
import uk.ac.ebi.metabolights.species.model.Taxonomy;

import javax.naming.ConfigurationException;
import java.util.Observable;

/**
 * User: conesa
 * Date: 29/10/2013
 * Time: 15:37
 */
public abstract class TaxonomyReader extends Observable {

	private Taxon currentTaxon;
	protected Taxonomy taxonomy;

	public Taxonomy getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}

	public Taxon getCurrentTaxon() {
		return currentTaxon;
	}

	protected abstract Taxonomy instantiateTaxonomy();

	public abstract void loadTaxonomy() throws ConfigurationException;

	public void taxonRead(Taxon taxon) throws ConfigurationException {

		if (taxonomy == null) taxonomy = instantiateTaxonomy();

		// If still remains null
		if (taxonomy == null){
			throw new ConfigurationException("taxonomy variable is not instantiated. Wrong development configuration. Please implement \"instantiateTaxonomy\" method");
		}
		currentTaxon = taxon;
		this.notifyObservers(this);

	}
}
