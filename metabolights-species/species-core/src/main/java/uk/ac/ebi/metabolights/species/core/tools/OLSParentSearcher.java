package uk.ac.ebi.metabolights.species.core.tools;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.species.model.Taxon;
import uk.ac.ebi.ook.web.services.Query;
import uk.ac.ebi.ook.web.services.QueryService;
import uk.ac.ebi.ook.web.services.QueryServiceLocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class OLSParentSearcher implements IParentSearcher {

	static Logger logger = LogManager.getLogger(OLSParentSearcher.class);

	private QueryServiceLocator locator;

	private final String QUERY_PREFIX = "NEWT";
	private ArrayList<String> validPrefixes = new ArrayList<String>(Arrays.asList(new String []{QUERY_PREFIX, "NCBI"}));

	private QueryServiceLocator getLocator() {

		if (locator == null){
			locator = new QueryServiceLocator();
		}

		return locator;
	}

	@Override
	public Taxon getParentFromTaxon(Taxon child) {

		Taxon parent = null;

		try {

			QueryService locator = getLocator();
			Query qs = locator.getOntologyQuery();

			//Map map = qs.getTermsByName("Homo", "NEWT", false);
			// We will query always for NEWT...although if it's a NCBI taxon.
			Map map = qs.getTermParents(child.getRecordIdentifier(), QUERY_PREFIX);

			// If there's no parent (root elements)
			if (map.size() == 0) {
				logger.warn("Taxon " + child.getId() + " hasn't any NEWT parent.");
				return null;
			}

			// There should be only one parent: get the key
			String key = (String) map.keySet().iterator().next();

			String name = (String) map.get(key);

			// Create a taxon based on this data
			parent = new Taxon("", name, "", "");

			// Prefix will always be NEWT.
			parent.setPrefix(QUERY_PREFIX);
			parent.setRecordIdentifier(key);

			logger.debug("Taxon " + child.getId() + " has a NEWT parent:" + parent.getId() + " - " + parent.getName());
			return parent;


		} catch (Exception e) {
			e.printStackTrace();
		}

		return parent;
	}

	@Override
	public boolean isThisTaxonYours(Taxon orphan) {

		boolean valid = validPrefixes.contains(orphan.getPrefix());

		return valid;
	}
}