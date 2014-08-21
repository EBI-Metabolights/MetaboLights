/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 11/14/13 11:44 AM
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

package uk.ac.ebi.metabolights.species.webservice;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.species.model.Taxon;

/**
 * User: conesa
 * Date: 12/11/2013
 * Time: 09:31
 */
@Controller
public class ToolsController {


	String GROUPS = "NEWT:10239~NEWT:2~NEWT:2157~NEWT:33090~NEWT:4751~NEWT:33208~NEWT:2759~NEWT:1";

	private final static Logger logger = LogManager.getLogger(ToolsController.class.getName());


	@RequestMapping("getgroup/{taxonId}")
	@ResponseBody
	public Taxon getStudyById(@PathVariable("taxonId") String taxonId) {

		logger.info("Requesting group for the taxon " + taxonId + " to the species webservice");


		return getTaxon(taxonId);

	}

	private Taxon getTaxon(String taxonId ) {
		return new Taxon(taxonId, "Name", "common name", "parent id");
	}


}
