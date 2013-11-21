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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.species.model.Taxon;

import java.util.*;

/*
Having a taxonomy identifier, it will lookup in the proper taxonomy until it find the first taxon specified in the taxon groups.
 */
public class Grouper {

	static Logger logger = LogManager.getLogger(Grouper.class);
	private final uk.ac.ebi.metabolights.species.core.tools.OLSParentSearcher OLSParentSearcher = new uk.ac.ebi.metabolights.species.core.tools.OLSParentSearcher();
	private Collection<IParentSearcher> parentSearchers = new ArrayList<IParentSearcher>();

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

	public Collection<IParentSearcher> getParentSearchers() {
		return parentSearchers;
	}

	public void setParentSearchers(Collection<IParentSearcher> parentSearchers) {
		this.parentSearchers = parentSearchers;
	}

	public Taxon getGroupFromTaxon(Taxon taxon){


		IParentSearcher searcher = getParentSearcher(taxon);

		if (searcher == null) {
			logger.warn("Can't look up a group for " + taxon.getId() + ". None of the ParentSearchers can take care of this kind of taxon.");
			return null;
		}

		Taxon group = scanTaxonomyForGroup(taxon , searcher);

		return group;
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

}
