/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 06/09/13 13:57
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.bioinvindex.model.AssayGroup;
import uk.ac.ebi.bioinvindex.model.AssayResult;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.bioinvindex.model.processing.Assay;
import uk.ac.ebi.bioinvindex.model.term.FactorValue;
import uk.ac.ebi.bioinvindex.model.term.PropertyValue;
import uk.ac.ebi.metabolights.model.MLAssay;
import uk.ac.ebi.metabolights.properties.PropertyLookup;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Sample;
import uk.ac.ebi.metabolights.service.AccessionService;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.StudyService;
import uk.ac.ebi.metabolights.service.TextTaggerService;
import uk.ac.ebi.metabolights.webservice.client.MetabolightsWsClient;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;

//import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;

/**
 * Controller for entry (=study) details.
 *
 */
@Controller
public class EntryController extends AbstractController {

    private static Logger logger = Logger.getLogger(EntryController.class);
	private final String DESCRIPTION="descr";

	@Autowired
	private StudyService studyService;

    @Autowired
    private AccessionService accessionService;

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";

	//(value = "/entry/{metabolightsId}")
	@RequestMapping(value = { "/{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}", "/entry/{metabolightsId}" })

	public ModelAndView showEntry(@PathVariable("metabolightsId") String mtblId, HttpServletRequest request) {
		logger.info("requested entry " + mtblId);

		Study study = null;

        mtblId = mtblId.toUpperCase(); //This method maps to both MTBLS and mtbls, so make sure all further references are to MTBLS

		try {
			request.setCharacterEncoding("UTF-8");
			study = studyService.getBiiStudy(mtblId,true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();  //TODO, change
        } catch (IllegalAccessException e){

            /// The current user is not allowed to access the study...
            // If there isn't a logged in user...
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getPrincipal().equals("anonymousUser")){
                // redirect force login...
                return new ModelAndView("redirect:securedredirect?url=" + mtblId);

            // The user is logged in but it's not authorised.
            } else {
                return new ModelAndView ("redirect:/index?message="+ PropertyLookup.getMessage("msg.studyAccessRestricted") + " (" +mtblId + ")");
            }

		}

		// Is there a study with this name?  Was there an error?  Have you tried to access a PRIVATE study?
		if (study ==null || study.getAcc() == null || study.getAcc().equals("Error"))
 			return new ModelAndView ("redirect:index?message="+ PropertyLookup.getMessage("msg.noStudyFound") + " (" +mtblId + ")");

		Collection<String> organismNames = getOrganisms(study);

		// Get the DownloadLink
		//String fileLocation = FileDispatcherController.getDownloadLink(study.getAcc(), study.getStatus());

		ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("entry");
		mav.addObject("study", study);
		mav.addObject("organismNames", organismNames);
		mav.addObject("factors", getFactorsSummary(study));
		mav.addObject("assays", getMLAssays(study));

        mav.addObject("submittedID", accessionService.getSubmittedId(mtblId));

		//Have to give the user the download stream as the study is not on the public ftp
		//if (!study.getAcc().equals(VisibilityStatus.PRIVATE.toString()))
		//mav.addObject("fileLocation",fileLocation); //All zip files are now generated on the fly, so ftpLocation is really fileLocation

		//Stick text for tagging (Whatizit) in the session..                       //TODO, Whatizit is not responding
		//if (study.getDescription()!=null) {
		//	logger.debug("placing study description in session for Ajax highlighting");
		//	request.getSession().setAttribute(DESCRIPTION,study.getDescription());
		//}

		return mav;
	}

    @RequestMapping(value = "/alt/metabolitesIdentified")
    public ModelAndView getMetabolitesIdentified(
            @RequestParam(required = false, value = "maf") String mafPath, HttpServletRequest request){

        //compose the ws url..
        String wsUrl = request.getRequestURL().toString();

        //Replace the servlet path (alt/MTBLS1")
        wsUrl = wsUrl.replace(request.getServletPath(), "");

        // Add the webservice part...
        wsUrl = wsUrl + "/webservice/";

        MetabolightsWsClient wsClient = new MetabolightsWsClient(wsUrl);

        MetaboliteAssignment metaboliteAssignment = wsClient.getMetabolites(mafPath);

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("metabolitesIdentified");

        mav.addObject("metabolitesIdentified", metaboliteAssignment);

        return mav;
    }


    @RequestMapping(value = { "/alt/{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}"})

    public ModelAndView showAltEntry(@PathVariable("metabolightsId") String mtblId, HttpServletRequest request) {

        logger.info("requested entry " + mtblId);


        //compose the ws url..
        String wsUrl = request.getRequestURL().toString();

        //Replace the servlet path (alt/MTBLS1")
        wsUrl = wsUrl.replace(request.getServletPath(), "");

        // Add the webservice part...
        wsUrl = wsUrl + "/webservice/";

        MetabolightsWsClient wsClient = new MetabolightsWsClient(wsUrl);

        uk.ac.ebi.metabolights.repository.model.Study study = wsClient.getStudy(mtblId);

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("entry2");

        mav.addObject("accession", mtblId);
        mav.addObject("study", study);
        for (Sample sample : study.getSamples()){
            mav.addObject("factors", sample.getFactors()); //just to get the correct order of the column headers
            break;
        }
//        mav.addObject("samples", study.getSamples());

        return  mav;
    }

	/**
	 * @param study
	 * @return
	 */
	private Collection<String> getOrganisms(Study study) {

		Collection<String> organismNames = new TreeSet<String>();

		for (AssayResult assRes : study.getAssayResults()) {
			for (PropertyValue<?> pv : assRes.getCascadedPropertyValues()) {
				if (pv.getType().getValue().equalsIgnoreCase("organism")) {
					organismNames.add(pv.getValue());
				}
			}
		}
		return organismNames;
	}

	private Collection<MLAssay> getMLAssays(Study study){

		HashMap<String,MLAssay> mlAssays = new HashMap<String, MLAssay>();

		for (AssayResult ar : study.getAssayResults()){

			MLAssay mlAssay= null;

			for (Assay assay: ar.getAssays()){

				String assayName = MLAssay.getAssayNameFromAssay(assay);

				// If we don't have the MLAssay
				if (!mlAssays.containsKey(assayName)){
					mlAssay = new MLAssay(assay);
					mlAssay.setStudy(study);
					mlAssays.put(assayName, mlAssay);
				} else {
					mlAssay = mlAssays.get(assayName);
				}

				mlAssay.addAssayLines(assay);
                //files = MLProcessingUtils.findAllDataInAssay(assay);
			}

			// Add the assay line to the assay
			mlAssay.addAssayResult(ar);


			if (ar.getAssays().size()!=1) logger.info("*****WARNING:Assays collection's size is different from 1, we expect to have a one-to-one relationship!!!: " + ar.getAssays().size());

		}

		// Assign AssayGroup and metabolites
		for (AssayGroup ag: study.getAssayGroups()){
			MLAssay mlAssay = mlAssays.get(ag.getFileName());

			if (mlAssay == null){
				logger.info("Oh! Not MLAssay found for AssayGroup: " + ag.getFileName());
			}else{
				// First set the study
				mlAssay.setAssayGroup(ag);
			}
		}

		return mlAssays.values();
	}



	@Autowired
	private TextTaggerService textTagger;

	/**
	 * Intended for an asynchronous call, to tag the description in a study.
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping(value="/tagText")
	public ModelAndView whatIzItStuff (HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("taggedText");
		String description = (String) request.getSession().getAttribute(DESCRIPTION);
		if(description!=null) {
			logger.debug("Calling WhatWhatIzIt");
			String taggedContent = textTagger.tagText(description);
			mav.addObject("taggedContent", taggedContent);
			logger.debug(taggedContent);
		}
		else
			mav.addObject("taggedContent", null);
		return mav;
	}

	private HashMap<String,ArrayList<String>> getFactorsSummary(Study study){

		HashMap<String, ArrayList<String>> fvSummary = new HashMap<String, ArrayList<String>>();

		// Loop through the assay results
		for (AssayResult ar:study.getAssayResults()){

			// For each factor value in Data item
			for (FactorValue fv:ar.getData().getFactorValues()){

				// ... if we don't have the factor already in the collection
				if (!fvSummary.containsKey(fv.getType().getValue())){
					fvSummary.put(fv.getType().getValue(),new ArrayList<String>());
				}

				// Get the values for that factor...
				ArrayList<String> values = fvSummary.get(fv.getType().getValue());


				String value;

				// Add the new value
				if (!(fv.getUnit() == null)){
					value = fv.getValue() + " " + fv.getUnit().getValue();
				} else {
					value = fv.getValue();
				}

				if (!values.contains(value)) values.add(value);
			}
		}
		return fvSummary;
	}
}
