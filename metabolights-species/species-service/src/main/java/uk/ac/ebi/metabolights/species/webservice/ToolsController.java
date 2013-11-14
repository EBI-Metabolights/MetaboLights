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
