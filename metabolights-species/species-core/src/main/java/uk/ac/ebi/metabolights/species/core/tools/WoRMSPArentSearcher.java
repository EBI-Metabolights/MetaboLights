/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/26/13 4:33 PM
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

			logger.info("Requesting classification for " + child.getId() + " to WORMS webservice.");

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

		Classification parent = null;
		Classification granparent= null;

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
