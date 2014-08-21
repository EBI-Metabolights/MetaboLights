/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/14/13 11:39 AM
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
		this.setChanged();
		this.notifyObservers(this);

	}
}
