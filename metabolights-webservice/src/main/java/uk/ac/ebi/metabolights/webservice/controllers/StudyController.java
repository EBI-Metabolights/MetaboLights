/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 6/6/14 2:20 PM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.webservice.model.StudyLite;
import uk.ac.ebi.metabolights.webservice.model.User;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;

import java.io.File;

@Controller
@RequestMapping("study")
public class StudyController {

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
	private final static Logger logger = LogManager.getLogger(StudyController.class.getName());
	private StudyDAO studyDAO;


    // Properties from context
    private @Value("#{publicStudiesLocation}") String publicStudiesLocationProp;
    private @Value("#{privateStudiesLocation}") String privateStudiesLocationProp;
    private @Value("#{isatabConfigurationLocation}") String isatabRootConfigurationLocation;

    @RequestMapping("{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}")
    @ResponseBody
    public Study getStudyById(@PathVariable("metabolightsId") String metabolightsId) {

		logger.info("Requesting " + metabolightsId + " to the webservice");

		return getStudy(metabolightsId, false);
	}

	@RequestMapping("{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}/full")
	@ResponseBody
	public Study getFullStudyById(@PathVariable("metabolightsId") String metabolightsId) {

		logger.info("Requesting full study " + metabolightsId + " to the webservice");

		return getStudy(metabolightsId, true);

	}

	private Study getStudy (String metabolightsId, boolean includeMAFFiles){

		Study study;


		StudyDAO invDAO = getStudyDAO();

		// Get the study status
		boolean isStudyPublic = invDAO.isStudyPublic(metabolightsId);


		// If the study is private.
		if (isStudyPublic || canUserAccessStudy(metabolightsId)){

			// Get the study
			study = invDAO.getStudy(metabolightsId.toUpperCase(), includeMAFFiles);           //Do not include metabolites (MAF) when loading this from the webapp.  This is added on later as an AJAX call

		} else {

			logger.warn("Study " +  metabolightsId + " is private. User can't access the study");
			// Let's return an empty one, until we implement security..
			study = new Study();
			study.setStudyIdentifier(metabolightsId);
			study.setPublicStudy(false);
			study.setTitle("PRIVATE STUDY");
			study.setDescription("This study is private and you haven't got access to it.");
		}

		return  study;

	}

	private boolean canUserAccessStudy(String metaboLightsId){

		// Get the user
		User user = getUser();

		if (user.isCurator()) return true;

		// If the user has the study in it's list of granted studies...
		for (StudyLite study: user.getStudies()){

			if (study.getAccesion().equals(metaboLightsId)) return true;

		}

		// If code has reached this point, user cant acces the data.
		return false;

	}
	private User getUser(){

		User user ;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getPrincipal().equals(new String("anonymousUser"))){

			 user = ((SpringUser) auth.getPrincipal()).getMetaboLightsUser();

		} else {

			user = new User();
			user.setUserName(auth.getPrincipal().toString());
		}

		return user;
	}

	private StudyDAO getStudyDAO() {

		if (studyDAO == null){

			studyDAO = new StudyDAO(isatabRootConfigurationLocation, publicStudiesLocationProp,privateStudiesLocationProp);
		}
		return studyDAO;
	}

	@RequestMapping("{metabolightsId:" + METABOLIGHTS_ID_REG_EXP +"}/assay/{assayIndex}/maf")
	@ResponseBody
	public MetaboliteAssignment getMetabolites(@PathVariable("metabolightsId") String metabolightsId, @PathVariable("assayIndex") String assayIndex){


		logger.info("Requesting maf file of the assay " + assayIndex + " of the study " + metabolightsId + " to the webservice");

		// Get the study....
		// TODO: optimize this, since we are loading the whole study to get the MAF file name of one of the assay, and maf file can be loaded having only the maf
		Study study = getStudy(metabolightsId, false);

		// Get the assay based on the index
		Assay assay = study.getAssays().get(Integer.parseInt(assayIndex)-1);

		MzTabDAO mzTabDAO = new MzTabDAO();
		MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();


		String filePath = assay.getMetaboliteAssignment().getMetaboliteAssignmentFileName();
		if (checkFileExists(filePath)){
			logger.info("MAF file found, starting to read data from "+filePath);
			metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(filePath);
		}  else {
			logger.error("MAF file " + filePath + " does not exist!");
			metaboliteAssignment.setMetaboliteAssignmentFileName("ERROR: " + filePath + " does not exist!");
		}

		return metaboliteAssignment;
	}

	private boolean checkFileExists(String filePath){
		File mafFile = new File(filePath);

		if (mafFile.exists())
			return true;

		return false;

	}
}
