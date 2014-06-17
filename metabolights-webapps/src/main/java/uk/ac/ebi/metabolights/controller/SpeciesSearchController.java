/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 6/17/14 4:17 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.metabolights.referencelayer.domain.Species;
import uk.ac.ebi.metabolights.referencelayer.domain.SpeciesGroup;
import uk.ac.ebi.metabolights.referencelayer.model.ModelObjectFactory;
import uk.ac.ebi.metabolights.service.AppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static ch.lambdaj.Lambda.*;

/**
 * Controller for reference layer compounds (=MTBLC*) details.
 *
 */
@Controller
public class SpeciesSearchController extends AbstractController {

    private static Logger logger = Logger.getLogger(SpeciesSearchController.class);
    List<String> speciesList;


    /**
     * Forwards to the jsp based on the last part of the requested URL.
     *
     * @param request
     * @return String indicating JSP target
     */
    public static ModelAndView searchForLastPartOfUrl (HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String target=requestUrl.replaceFirst("^(.)*/", "");
        logger.debug("target is "+target);

        target = target!=null&&!target.equals("")?target:"index";

        return new ModelAndView ("redirect:download")
        ;

    }
    /**
     * Shortcuts for reference species
     * @param request
     * @return search page with selected organism
     */
    @RequestMapping(value={ "/human","/mouse","/arabidopsis", "/ecoli", "/celegans", "/yeast"})
    public ModelAndView modelAndView (HttpServletRequest request) {

        String requestUrl = request.getRequestURL().toString();

        if (requestUrl.endsWith("/human"))
            requestUrl = "reference?organisms=Homo%20sapiens%20(Human)";
        else if (requestUrl.equals("/mouse"))
            requestUrl = "reference?organisms=Mus%20musculus%20(Mouse)";
        else if (requestUrl.equals("/arabidopsis"))
            requestUrl = "reference?organisms=Arabidopsis%20thaliana%20(thale%20cress)";
        else if (requestUrl.equals("/ecoli"))
            requestUrl = "eference?organisms=Escherichia%20coli";
        else if (requestUrl.equals("/celegans"))
            requestUrl = "reference?organisms=Caenorhabditis%20elegans";
        else if (requestUrl.equals("/yeast"))
            requestUrl = "reference?organisms=Saccharomyces%20cerevisiae%20(Baker's%20yeast)";

        return new ModelAndView ("redirect:"+requestUrl);
    }


    @RequestMapping(value = "/arabidopsis")
    public ModelAndView showSpecies() {

        //ModelAndView mav = new ModelAndView("compound");
        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("species");

        return mav;

    }

	@RequestMapping(value = "/species/json")
	@ResponseBody
	public String getJsonSpecies() {

		Collection<SpeciesGroup> groups = ModelObjectFactory.getAllSpeciesTree();

		// Need to build the json structure.
		String json = getSpeciesTreeToJson(groups);

		return json;

	}

	private String getSpeciesTreeToJson(Collection<SpeciesGroup> tree){

		try {
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

		} catch (Exception e){
			logger.error("Can't turn species tree into json", e);
			return "Can't load tree!";
		}
	}

	private void treeToJson(Collection<SpeciesGroup> tree,StringBuilder json)
	{

		List<SpeciesGroup> firstLevel =filter(having(on(SpeciesGroup.class).getParentId(), Matchers.equalTo(0L)), tree);

		for (SpeciesGroup sg: firstLevel)
		{


			if (sg.getChildren().size()==0 && sg.getSpecieses() == null)
			{
				logger.warn("Species group " + sg.getName() + " hasn't got any children not species. It will not be added to JSON data");
				continue;
			}

			speciesGroupToJson(sg, json, 1);


		}


	}

	private void speciesGroupToJson(SpeciesGroup sg, StringBuilder json, int level)
	{

		if (!isThereAnyLeafInTheBranch(sg)) return;

		// Check if we need a comma
		if(json.length()!= 0 && json.charAt(json.length()-1) == '}')
		{
			json.append(",");
		}

		json.append("{");
		json.append("\"name\":\"" + sg.getName() + "\"");
		json.append(", \"level\":" + level);
		json.append(",\"children\":[");

		// Increase the level for the children
		level++;

		// If it has any children (Other species groups below)
		if (sg.getChildren() != null) {

			for (SpeciesGroup child : sg.getChildren()) {

				speciesGroupToJson(child, json, level);

			}
		}


		if (sg.getSpecieses() != null) {
			for (Species sp : sg.getSpecieses()) {

				speciesToJson(sp, json, level);

			}

		}

		json.append("]");
		json.append("}");

		//json.append("{\"name\":\"Animuls\",\"size\":89,\"level\":1},{\"name\":\"Archaea\",\"size\":1,\"level\":1},{\"name\":\"Bacteria\",\"size\":132,\"level\":1},{\"name\":\"Fungi\",\"size\":209,\"level\":1},{\"name\":\"Others\",\"size\":1,\"level\":1},{\"name\":\"Others Eukaryota\",\"size\":25,\"level\":1},{\"name\":\"Viruses\",\"size\":4,\"level\":1},{\"name\":\"PLANTS\",\"level\":1,\"size\":20}");

	}

	private boolean isThereAnyLeafInTheBranch(SpeciesGroup sg){

		if (doesSpeciesGroupHasAnySpecies(sg))
		{
			return true;

		} else if (!doesSpeciesGroupHasAnyChildren(sg)){

			return false;

		}else{

			for (SpeciesGroup children: sg.getChildren())
			{

				if (isThereAnyLeafInTheBranch(children)){
					return true;
				}
			}

		}
		return false;
	}

	private boolean doesSpeciesGroupHasAnySpecies(SpeciesGroup sg){

		return doesCollectionHasAnyItem(sg.getSpecieses());

	}

	private boolean doesSpeciesGroupHasAnyChildren(SpeciesGroup sg){

		return doesCollectionHasAnyItem(sg.getChildren());
	}

	private boolean doesCollectionHasAnyItem(Collection collection){

		return (collection != null && collection.size() > 0);

	}


	private void speciesToJson(Species sp, StringBuilder json, int level )
	{

		// Check if we need a comma
		if(json.length()!= 0 && json.charAt(json.length()-1) == '}')
		{
			json.append(",");
		}
		json.append("{");
		json.append("\"name\":\"" + sp.getSpecies() + "\"");
		json.append(", \"level\":" + level);
		json.append(",\"size\":" + 1);
		json.append("}");
	}

    @RequestMapping(value ="/getSpeciesAutoComplete",method = RequestMethod.GET)
    @ResponseBody
    private void speciesAutoComplete(HttpServletResponse response){
        String autoCompleteList = "";

        if (speciesList == null) //Take some time, so only populate when empty
            speciesList =  ModelObjectFactory.getAutoCompleteSpecies();

        Iterator itr = speciesList.iterator();
        while(itr.hasNext()) {
            autoCompleteList += itr.next().toString();
        }

        response.setContentType("text/html");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.write(autoCompleteList);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value ="/getSpeciesAutoComplete2",method = RequestMethod.GET)
    @ResponseBody
    private String speciesAutoComplete(){
        String autoCompleteList = "";

        if (speciesList == null) //Take some time, so only populate when empty
            speciesList =  ModelObjectFactory.getAutoCompleteSpecies();

        Iterator itr = speciesList.iterator();
        while(itr.hasNext()) {
            autoCompleteList += itr.next().toString();
        }

        return autoCompleteList;

    }
}
