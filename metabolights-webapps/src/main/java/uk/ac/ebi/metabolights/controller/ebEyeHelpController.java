/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 12/23/13 2:25 PM
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

package uk.ac.ebi.metabolights.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;
import uk.ac.ebi.ebisearchservice.EBISearchService;
import uk.ac.ebi.ebisearchservice.EBISearchService_Service;
import uk.ac.ebi.metabolights.service.AppContext;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Controller for login and related actions.
 * @author Tejasvi Mahendraker
 *
 */
@Controller
public class ebEyeHelpController extends AbstractController{

	 private EBISearchService ebiSearchService;

    private @Value("#{ebiServiceURL}") String jndiUrl;


	@RequestMapping({"/ebeyehelp"})
	public ModelAndView ebeyeHelp(@RequestParam(required=false,value="domain") String domain,
								  @RequestParam(required=false,value="query") String query,
								  @RequestParam(required=false,value="url") String url) {

		if (url == null)
            url = jndiUrl;

        if (url.isEmpty()){
//			url = "http://ash-10.ebi.ac.uk:9190/ebisearch/service.ebi?wsdl";

			ebiSearchService =  new EBISearchService_Service().getEBISearchServiceHttpPort();
		}else{

			//@RequestParam(required=false,value="wsdlurl") String url
			try {
				ebiSearchService=  new EBISearchService_Service(new URL(url)).getEBISearchServiceHttpPort();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav ("ebeyehelp"); //name of jsp page must be same as this
		mav.addObject("query", query);
		mav.addObject("domain", domain);
		mav.addObject("url", url);
		getSearchResults(mav, domain, query);
		return mav;
    }


	private void getSearchResults(ModelAndView mav, String domain, String query){


		  ArrayOfString listdomains1 = ebiSearchService.listDomains();
		  mav.addObject("listDomains", listdomains1.getString());

		  if (domain != null){

			  ArrayOfString listFields1 = ebiSearchService.listFields(domain); //lists the fields in the specified domain
			  //listFields1.getString().add("TAXONOMY");
			  //listFields1.getString().add("CHEBI");
			  mav.addObject("listFields", listFields1.getString());

			  int numOfResults = ebiSearchService.getNumberOfResults(domain, query);
			  mav.addObject("totalNumberCompounds", numOfResults);

			  if(numOfResults != 0){
				  ArrayOfString resultsIds = ebiSearchService.getResultsIds(domain, query, 0, 20);
				  mav.addObject("resultIds", resultsIds.getString());

				  ArrayOfArrayOfString arrayOfArrayOfEntries = ebiSearchService.getEntries(domain, resultsIds, listFields1);
				  mav.addObject("getEntries", arrayOfArrayOfEntries.getArrayOfString());

				  ArrayOfString ListAddRefFields = ebiSearchService.listAdditionalReferenceFields(domain);
				  //ArrayOfArrayOfString arrayOfarrayOfEntries = ebiSearchService.getEntries(domain, resultsIds, ListAddRefFields);

				  mav.addObject("ListAddRefFields", ListAddRefFields.getString());
				  //mav.addObject("arrayOfarrayOfEntries", arrayOfarrayOfEntries.getArrayOfString());
			  }
		  }
	}
}