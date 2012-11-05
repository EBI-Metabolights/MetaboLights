package uk.ac.ebi.metabolights.controller;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import uk.ac.ebi.ebisearchservice.ArrayOfArrayOfString;
import uk.ac.ebi.ebisearchservice.ArrayOfString;
import uk.ac.ebi.ebisearchservice.EBISearchService;
import uk.ac.ebi.ebisearchservice.EBISearchService_Service;
import uk.ac.ebi.metabolights.referencelayer.MetabolightsCompound;

/**
 * Controller for login and related actions.
 * @author The Metabolights Team
 */
@Controller
public class ReferenceLayerController extends AbstractController {

	String url = "http://ash-3:8080/ebisearch/service.ebi?wsdl";
	private EBISearchService ebiSearchService;

	String ChDomain = "chebi";
	String MTBLDomain = "metabolights";

	@RequestMapping({ "/RefLayerSearch" })
	public ModelAndView searchAndDisplay(
			@RequestParam(required = false, value = "query") String query,
			@RequestParam(required = false, value = "organisms") String[] organisms,
			@RequestParam(required = false, value = "technology") String[] technology) {
			


		try {
			ebiSearchService = new EBISearchService_Service(new URL(url)).getEBISearchServiceHttpPort();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("RefLayerSearch"); // must match the definition attribute in tiles.xml

		mav.addObject("query", query);
		mav.addObject("Chdomain", ChDomain);
		mav.addObject("MTBLDomain", MTBLDomain);
		getSearchResults(mav, ChDomain, MTBLDomain, query, organisms, technology);
		return mav;
	}

	public void getSearchResults(ModelAndView mav, String ChDomain, String MTBLDomain, String query, String[] organisms, String[] technology) {


		String ModQuery = null;
		
		if(query == null){
			query = "";
		}
		
		if(query.equals("")){
			ModQuery = "id:MTBLC*";
		} else if(organisms != null){
			//ModQuery = "("+query+") AND (technology_type:"+technology+")";
		}else {
			ModQuery = "("+query+") AND (id:MTBLC*)";
		}
		
		int MTBLnumOfResults = 0;		

		if ((query != null) && ((MTBLDomain != null) || ((ChDomain != null)))) {
			MTBLnumOfResults = ebiSearchService.getNumberOfResults(MTBLDomain, ModQuery);
//			ChDomainnumOfResults = ebiSearchService.getNumberOfResults(ChDomain, ModQuery);
		}
		
		ArrayOfString MTBLFields = ebiSearchService.listFields(MTBLDomain);
		MTBLFields.getString().add("CHEBI");
		MTBLFields.getString().add("METABOLIGHTS");


		if (MTBLnumOfResults != 0) {
			
			ArrayOfString MTBLResults = ebiSearchService.getAllResultsIds(MTBLDomain, ModQuery);
			ArrayOfArrayOfString MTBLArrayOfEntries = ebiSearchService.getEntries(MTBLDomain, MTBLResults, MTBLFields);
			
			int MTBLArraySize = MTBLArrayOfEntries.getArrayOfString().size(); //2
			
			ArrayOfString MTBLEntries = null;
			ArrayList<String> techTypes = new ArrayList<String>();
			ArrayList<String> orgTypes = new ArrayList<String>();
			
			// Declare a collection to store all the entries found
			Collection<MetabolightsCompound> mcs = new ArrayList <MetabolightsCompound>();
			
			for(int i=0; i<MTBLArraySize; i++){
				
				MTBLEntries = MTBLArrayOfEntries.getArrayOfString().get(i);
//				String queryID = MTBLEntries.getString().get(2); // Id of the compound in Metabolights
				String queryIUPACName = MTBLEntries.getString().get(3); // IUPAC name of the compound in MetaboLights
//				String queryName = MTBLEntries.getString().get(4); //Name of the compound
				String organismType = MTBLEntries.getString().get(5);
				String technologyType = MTBLEntries.getString().get(6); // gets technology_type, which can be NMR Sectroscopy or MS.
				String ChebiName = MTBLEntries.getString().get(7); // Chebi name for the compound in Metabolights
				String[] SplitChebiName = ChebiName.split(":");
				String MTBLStudies = MTBLEntries.getString().get(8); // Studies for that compound
				
				if(queryIUPACName == null){
					queryIUPACName = "null";
				}
				
				String[] iupacSplit = queryIUPACName.split("\\n");
//				int iupacSplitLen = iupacSplit.length;
				
				String[] MTBLSplit = MTBLStudies.split("\\s");
//				int MTBLSplitSize = MTBLSplit.length;
				
				if (!techTypes.contains(technologyType)){
					if(technologyType != null){
						//System.out.println(technologyType.toString());
						techTypes.add(technologyType);
					}
				}
				
				if(!orgTypes.contains(organismType)){
					if(organismType != null){
						orgTypes.add(organismType);
					}
				}
//				techType = technologyType.split("NMR");


				
				// Instantiate a new entry...
				MetabolightsCompound mc = new MetabolightsCompound();
				
				// Fill its properties...
				mc.setChebiURL(SplitChebiName[1]);
				mc.setAccession(MTBLEntries.getString().get(2)); // Id of the compound in Metabolights
				mc.setName(MTBLEntries.getString().get(4)); //Name of the compound
				mc.setIupac(iupacSplit);
//				mc.setTechnologyType(techType); // gets technology_type, which can be NMR Sectroscopy or MS.
				mc.setChebiId(MTBLEntries.getString().get(7)); // Chebi name for the compound in Metabolights
				mc.setMTBLStudies(MTBLSplit);
				
				// Store the metabolite entry in the collection
				mcs.add(mc);
			}
			//System.out.println(techTypes.size());
			mav.addObject("organismList", orgTypes);
			mav.addObject("technologyList", techTypes);
			mav.addObject("query", query);
			mav.addObject("entries", mcs);
			mav.addObject("MTBLResults", MTBLResults.getString());
		}
	}
}






//name:alanine technology_type:NMR
//if(query == null){
//query = "*";
//}
//mav.addObject("url", url);
//int ChDomainnumOfResults = 0;
//int a = 0;
//ArrayOfString ChebiFields = ebiSearchService.listFields(ChDomain); //lists the fields in the specified domain

//mav.addObject("ChebiFields", ChebiFields.getString());
//mav.addObject("MTBLFields", MTBLFields.getString());

//String ChebiNameImage = SplitChebiName[1];
//ArrayOfString queryIDfields = null;
//ArrayOfString IUPACNamefields = null;
//ArrayOfString Namefields = null;
//ArrayOfString ChebiNamefields = null;
//ArrayOfString MTBLStudiesfields = null;
//ArrayOfArrayOfString arrayOfFields = new ArrayOfArrayOfString();

//queryIDfields = new ArrayOfString();
//IUPACNamefields = new ArrayOfString();
//Namefields = new ArrayOfString();
//ChebiNamefields = new ArrayOfString();
//MTBLStudiesfields = new ArrayOfString();

//queryIDfields.getString().add(queryID);
//j++;
//for(int y=0; y<iupacSplitLen; y++){
//	IUPACNamefields.getString().add(iupacSplit[y]);
//}
//j++;
//Namefields.getString().add(queryName); // name of the compound
//j++;
//ChebiNamefields.getString().add(ChebiName);
//
//for(int x=0; x<MTBLSplitSize; x++){
//	MTBLStudiesfields.getString().add(MTBLSplit[x]);
//}

//ArrayOfString ListAddRefFields = ebiSearchService.listAdditionalReferenceFields(MTBLDomain);
//System.out.println("ListAddRefFields  -"+ListAddRefFields);
//ArrayOfString ChResults = ebiSearchService.getResultsIds(ChDomain, query, 1, 20);
//mav.addObject("ListAddRefFields", ListAddRefFields.getString());

//ArrayOfString fields = new ArrayOfString();
//fields.getString().add("description");
//fields.getString().add("name");
//fields.getString().add("id");