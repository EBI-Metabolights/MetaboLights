package uk.ac.ebi.metabolights.species.core.tools;

import aphia.v1_0.Classification;
import org.apache.axis2.AxisFault;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uk.ac.ebi.metabolights.referencelayer.worms.client.WoRMSClient;
import uk.ac.ebi.metabolights.species.model.Taxon;

/**
 * User: conesa
 * Date: 21/11/2013
 * Time: 17:21
 */
public class WoRMSPArentSearcher implements IParentSearcher {

	static Logger logger = LogManager.getLogger(WoRMSPArentSearcher.class);
	public static final String WoRMS_PREFIX = "WORMS";

	private WoRMSClient woRMSClient;

	public WoRMSPArentSearcher() throws AxisFault {
		woRMSClient = new WoRMSClient();

	}
	@Override
	public Taxon getParentFromTaxon(Taxon child) {



		try {

			// Classification starts from the root, so, you only have, getChild methods to navigate.
			Classification record = null;
			record = woRMSClient.getAphiaClasificationByID(Integer.parseInt(child.getRecordIdentifier()));
			Taxon parent = getParentFromClassification(record);
			return parent;


		} catch (Exception e) {
			logger.error("Couldn't get classification from " + child.getId() + "(" + child.getName() + ")");
			return null;
		}

	}

	private Taxon getParentFromClassification(Classification classification){


		// Start with the root;
		Classification child = classification.getChild();

		Classification parent = classification;
		Classification granparent= parent;

		while (child.getRank() != null) {

			granparent = parent;
			parent = child;
			child = child.getChild();

		}

		// Create a taxon for the parent
		Taxon parentT = new Taxon(WoRMS_PREFIX + ":" + granparent.getAphiaID(), granparent.getScientificname(), "","");

		return parentT;

	}

	@Override
	public boolean isThisTaxonYours(Taxon orphan) {
		return orphan.getPrefix().equals(WoRMS_PREFIX);
	}
}
