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
import uk.ac.ebi.ook.web.services.*;

import java.util.*;

/*
Having a taxonomy identifier, it will lookup in the proper taxonomy until it find the first taxon specified in the taxon groups.
 */
public class Grouper {

	static Logger logger = LogManager.getLogger(Grouper.class);

	List<Taxon> taxonGroups;

	public Grouper(){
		// Special not very useful case...no groups specified therefore it will always return the root element.
		this.taxonGroups = new LinkedList<Taxon>();
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

	public Taxon getGroupFromTaxon(Taxon taxon){

		String id = taxon.getId();

		// Replace NCBI for NEWT...
		id = id.replace("NCBI","NEWT");

		// If it doesn't start with NEWT we can't do anything for now.
		if (!id.startsWith("NEWT:")){

			logger.warn("Can't look up a group for " + taxon.getId() + ". For now we only can accept NCBI and NEWT taxons.");

			return null;

		}


		Taxon group = scanTaxonomyForGroup(taxon);

		return group;
	}

	private Taxon scanTaxonomyForGroup(Taxon taxon){


		// if the taxon is one of the group taxons
		if (isTaxonInGroupList(taxon)) {

			int index =  taxonGroups.indexOf(taxon);

			return taxonGroups.get(index);

		} else {

			// Search for the parent....
			Taxon parent = getOLSParent(taxon);

			// If the parent is null ...we are at the root or there's no parent.
			if (parent == null){
				return taxon;
			} else {
				// Continue going up the tree
				parent = scanTaxonomyForGroup(parent);
				return parent;
			}

		}


	}

	public boolean isTaxonInGroupList(Taxon taxon){

		return taxonGroups.contains(taxon);

	}
	public Taxon getOLSParent(Taxon taxon){

	Taxon parent = null;

	try {
			QueryService locator = new QueryServiceLocator();
			Query qs = locator.getOntologyQuery();

			//Map map = qs.getTermsByName("Homo", "NEWT", false);
			Map map = qs.getTermParents(taxon.getRecordIdentifier(), taxon.getPrefix());

			// If there's no parent (root elements)
			if (map.size() ==0){
				return null;
			}

			// There should be only one parent: get the key
			String key = (String) map.keySet().iterator().next();

			String value = (String)map.get(key);

			// Create a taxon based on this data
			parent = new Taxon("",value,"","");

			parent.setPrefix(taxon.getPrefix());
			parent.setRecordIdentifier(key);
			return parent;


		} catch (Exception e) {
			e.printStackTrace();
		}

		return parent;
	}

}
