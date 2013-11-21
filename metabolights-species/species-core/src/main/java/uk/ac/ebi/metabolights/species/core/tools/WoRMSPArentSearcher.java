package uk.ac.ebi.metabolights.species.core.tools;

import org.apache.axis2.AxisFault;
import uk.ac.ebi.metabolights.referencelayer.worms.client.WoRMSClient;
import uk.ac.ebi.metabolights.species.model.Taxon;

/**
 * User: conesa
 * Date: 21/11/2013
 * Time: 17:21
 */
public class WoRMSPArentSearcher implements IParentSearcher {

	private final String WoRMS_PREFIX = "WORMS";

	private WoRMSClient woRMClient;

	public WoRMSPArentSearcher() throws AxisFault {
		woRMClient = new WoRMSClient();

	}
	@Override
	public Taxon getParentFromTaxon(Taxon child) {

		// TODO: not implemented yet
		return null;

	}

	@Override
	public boolean isThisTaxonYours(Taxon orphan) {
		return orphan.getPrefix().equals(WoRMS_PREFIX);
	}
}
