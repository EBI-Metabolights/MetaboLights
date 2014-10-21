/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 4/2/14 4:07 PM
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
/**
 * User: conesa
 * Date: 12/11/2013
 * Time: 15:41
 */


/*
 EXAMPLE OF A GROUP FOR NEWT:

 http://ols.wordvis.com
 Root --> http://ols.wordvis.com/q=NEWT:1   ("Not Viruses nor cellular organisms")
 Viruses --> http://ols.wordvis.com/q=NEWT:10239
 Bacteria --> http://ols.wordvis.com/q=NEWT:2
 Archaea --> http://ols.wordvis.com/q=NEWT:2157
 Viridiplantae -> http://ols.wordvis.com/q=NEWT:33090
 Fungi --> http://ols.wordvis.com/q=NEWT:4751
 Animals --> http://ols.wordvis.com/q=NEWT:33208
 Other Eukariota --> http://ols.wordvis.com/q=NEWT:2759 (Not Plants, Fungae nor Animals)


  										root (Others)

  						Viruses    Cellular organisms   Others	(resolves to root)
  								   /       |	 	\
  							Eukaryota	Bacteria	Archaea
 				 		  /  /   |  \
  			Viridiplantae	/	 |	Others (resolves to Eukaryota)
  						   /	 |
  					   Fungi   Metazoa


*/

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import uk.ac.ebi.metabolights.species.globalnames.client.GlobalNamesWSClient;
import uk.ac.ebi.metabolights.species.globalnames.model.Data;
import uk.ac.ebi.metabolights.species.globalnames.model.GlobalNamesResponse;
import uk.ac.ebi.metabolights.species.globalnames.model.Result;
import uk.ac.ebi.metabolights.species.model.Taxon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*
Having a taxonomy identifier, it will lookup in the proper taxonomy until it find the first taxon specified in the taxon groups.
 */
public class Grouper {

	static Logger logger = LoggerFactory.getLogger(Grouper.class);
	private final uk.ac.ebi.metabolights.species.core.tools.OLSParentSearcher OLSParentSearcher = new uk.ac.ebi.metabolights.species.core.tools.OLSParentSearcher();
	private Collection<IParentSearcher> parentSearchers = new ArrayList<IParentSearcher>();

	// Global names web service alternative.
	private boolean isGlobalNamesEnabled = false;
	GlobalNamesWSClient globalNamesWSClient = new GlobalNamesWSClient();

	List<Taxon> taxonGroups;

	public Grouper(){
		// Special not very useful case...no groups specified therefore it will always return the root element.
		this.taxonGroups = new LinkedList<Taxon>();

		// Add the OLS searcher by default.
		parentSearchers.add(new OLSParentSearcher());
	}
	/* Use TaxonConverter to generate taxons based on strings...*/
	public Grouper(List<Taxon>taxonGroups){
		this.taxonGroups = taxonGroups;

	}

	public List<Taxon> getTaxonGroups() {
		return taxonGroups;
	}

	public void setTaxonGroups(List<Taxon> taxonGroups) {
		this.taxonGroups = taxonGroups;
	}

	public boolean isGlobalNamesEnabled() {	return isGlobalNamesEnabled;}

	public void setGlobalNamesEnabled(boolean isGlobalNamesEnabled) {this.isGlobalNamesEnabled = isGlobalNamesEnabled;}

	public Collection<IParentSearcher> getParentSearchers() {
		return parentSearchers;
	}

	public void setParentSearchers(Collection<IParentSearcher> parentSearchers) {
		this.parentSearchers = parentSearchers;
	}

	public Taxon getGroupFromTaxon(Taxon taxon){


		IParentSearcher searcher = getParentSearcher(taxon);

		Taxon group = null;

		if (searcher != null) {

			group = scanTaxonomyForGroup(taxon, searcher);

		} else if(!isGlobalNamesEnabled){
			logger.warn("Can't look up a group for " + taxon.getId() + ". None of the ParentSearchers can take care of this kind of taxon and GlobalNames search is disabled");
			return null;
		}


		// If group is null and can use GlobalNames webservice
		if ((group == null || group.equals(taxon)) && isGlobalNamesEnabled){

			group = scanGlobalNamesForGroup(taxon);

		}

		return group;
	}


	// Scan global names webservice.
	private Taxon scanGlobalNamesForGroup(Taxon organism)
	{

		try {

			GlobalNamesResponse response = globalNamesWSClient.resolveName(organism.getName());

			// There should be only one
			Data data = response.getData().iterator().next();

			Taxon group;

			for (Result result : data.getResults()) {

				// Get the clasiffication path (String)
				String classificationS = result.getClassification_path();

				// If there's is NOT a classification...
				if (classificationS == null) continue;

				logger.info("Classification found for " + organism + ": " + classificationS);
				// Split it by pipe (|)
				String[] classification = classificationS.split("\\|");

				// Loop through the classification array from last one to first one (bottom -up)
				for (int pos = classification.length-1; pos >= 0; pos--){
					String groupCandidate = classification[pos];

					group = getGroupByTaxonName(groupCandidate);

					if (group != null) return group;

				}
			}

		} catch (Exception e) {
			logger.warn("Can't scan globalnames for " + organism);
		}


		return null;
	}

	private IParentSearcher getParentSearcher(Taxon orphan){

		for (IParentSearcher ps: parentSearchers){
			if (ps.isThisTaxonYours(orphan)){
				return ps;
			}
		}

		return null;
	}

	private Taxon scanTaxonomyForGroup(Taxon taxon, IParentSearcher searcher){


		// if the taxon is one of the group taxons
		if (isTaxonInGroupList(taxon)) {

			int index =  taxonGroups.indexOf(taxon);

			return taxonGroups.get(index);

		} else {

			// Search for the parent....
			Taxon parent = searcher.getParentFromTaxon(taxon);

			// If the parent is null ...we are at the root or there's no parent.
			if (parent == null){
				return taxon;
			} else {
				// Continue going up the tree
				parent = scanTaxonomyForGroup(parent, searcher);
				return parent;
			}

		}


	}

	public boolean isTaxonInGroupList(Taxon taxon){

		return taxonGroups.contains(taxon);

	}

	public Taxon getGroupByTaxonName(String taxonName){

		// Loop through the taxon group collection...
		for (Taxon group: taxonGroups)
		{
			if (group.getName().equals(taxonName)){
				return group;
			}
		}

		return null;

	}


}
