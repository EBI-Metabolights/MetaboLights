package uk.ac.ebi.metabolights.controller;


import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;
import uk.ac.ebi.ebisearchservice.EBISearchService;
import uk.ac.ebi.ebisearchservice.EBISearchService_Service;
import uk.ac.ebi.metabolights.service.AppContext;


/**
 * Controller for login and related actions.  
 * @author Tejasvi Mahendraker
 *
 */
@Controller
public class ebEyeHelpController extends AbstractController{
	
	 private EBISearchService ebiSearchService; 
	           
	
	@RequestMapping({"/ebeyehelp"})
	public ModelAndView ebeyeHelp(@RequestParam(required=false,value="domain") String domain,
								  @RequestParam(required=false,value="query") String query,
								  @RequestParam(required=false,value="url") String url) {
	    
		
		if (url == null){

//			url = "http://ash-10.ebi.ac.uk:9190/ebisearch/service.ebi?wsdl";
			ebiSearchService=  new EBISearchService_Service().getEBISearchServiceHttpPort();
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