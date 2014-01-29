/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 29/01/14 12:14
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesGroup;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.service.AppContext;

import java.util.Collection;
import java.util.List;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
public class SpeciesSearchController extends AbstractController {

    private static Logger logger = Logger.getLogger(SpeciesSearchController.class);



    @RequestMapping(value = "/species")
    public ModelAndView showSpecies() {


        //ModelAndView mav = new ModelAndView("compound");
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("species");

        return mav;

    }

	@RequestMapping(value = "/species/json")
	@ResponseBody
	public String getJsonSpectra() {


		Collection<SpeciesGroup> groups = ModelObjectFactory.getAllSpeciesTree();

		// Need to build the json structure.
		String json = getSpeciesTreeToJson(groups);

		return json;



	}
	private String getSpeciesTreeToJson(Collection<SpeciesGroup> tree){

		StringBuilder json = new StringBuilder();

		// Get the root element...
		json.append("{");
		json.append("\"name\":\"Species\"");
		json.append(", \"level\":0");
		json.append(", \"children\":[");

		treeToJson(tree, json);

		// Close root
		json.append("]}");
		return json.toString();
	}

	private void treeToJson(Collection<SpeciesGroup> tree,StringBuilder json)
	{

		List<SpeciesGroup> firstLevel =filter(having(on(SpeciesGroup.class).getParentId(), Matchers.equalTo(0L)), tree);

		boolean isFirst = true;
		for (SpeciesGroup sg: firstLevel)
		{


			if (sg.getChildren().size()==0 && sg.getSpecieses() == null)
			{
				logger.warn("Species group " + sg.getName() + " hasn't got any children not species. It will not be added to JSON data");
				continue;
			}

			if (isFirst)
			{
				isFirst = false;
			}else{
				json.append(",");
			}

			speciesGroupToJson(sg, json, 1);
		}


	}
	private void speciesGroupToJson(SpeciesGroup sg, StringBuilder json, int level)
	{

		json.append("{");
		json.append("\"name\":\"" + sg.getName() + "\"");
		json.append(", \"level\":" + level);
		json.append(",\"children\":[");

		// Increase the level for the children
		level++;
		boolean isFirst = true;

		// If it has any children (Other species groups below)
		if (sg.getChildren().size() > 0)
		{

			for (SpeciesGroup child: sg.getChildren()){

				if (isFirst)
				{
					isFirst = false;
				}else{
					json.append(",");
				}
				speciesGroupToJson(child, json, level);
			}


		} else
		{


			for (Species sp : sg.getSpecieses())
			{

				if (isFirst)
				{
					isFirst = false;
				}else{
					json.append(",");
				}

				speciesToJson(sp,json,level);
			}
		}

		json.append("]");
		json.append("}");

		//json.append("{\"name\":\"Animuls\",\"size\":89,\"level\":1},{\"name\":\"Archaea\",\"size\":1,\"level\":1},{\"name\":\"Bacteria\",\"size\":132,\"level\":1},{\"name\":\"Fungi\",\"size\":209,\"level\":1},{\"name\":\"Others\",\"size\":1,\"level\":1},{\"name\":\"Others Eukaryota\",\"size\":25,\"level\":1},{\"name\":\"Viruses\",\"size\":4,\"level\":1},{\"name\":\"PLANTS\",\"level\":1,\"size\":20}");

	}

	private void speciesToJson(Species sp, StringBuilder json, int level)
	{
		json.append("{");
		json.append("\"name\":\"" + sp.getSpecies() + "\"");
		json.append(", \"level\":" + level);
		json.append(",\"size\":" + 1);
		json.append("}");
	}
}
