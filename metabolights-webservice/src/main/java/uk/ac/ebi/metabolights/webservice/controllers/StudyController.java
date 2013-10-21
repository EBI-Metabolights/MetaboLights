/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 11/10/13 14:34
 * Modified by:   kenneth
 *
 * Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.webservice.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.StudyDAO;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;

import java.io.File;

@Controller
@RequestMapping("study")
public class StudyController {

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
	private final static Logger logger = Logger.getLogger(StudyController.class.getName());
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

		StudyDAO invDAO = getStudyDAO();

		// Get the study
		Study study = invDAO.getStudy(metabolightsId.toUpperCase(), includeMAFFiles);           //Do not include metabolites (MAF) when loading this from the webapp.  This is added on later as an AJAX call

		// If the study is private.
		if (!study.isPublic()){

			// Let's return an empty one, until we implement security..
			study = new Study();
			study.setStudyIdentifier(metabolightsId);
			study.setPublic(false);
			study.setTitle("PRIVATE STUDY");
			study.setDescription("This study is private and can not be accessed at the moment. Soon we will implement the required security layer in the webservice.");
		}

		return  study;

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
