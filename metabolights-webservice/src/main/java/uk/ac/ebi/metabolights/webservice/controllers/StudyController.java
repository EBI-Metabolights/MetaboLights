/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2015-Apr-22
 * Modified by:   kenneth
 *
 * Copyright 2015 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/11/14 3:39 PM
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

package uk.ac.ebi.metabolights.webservice.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.repository.dao.DAOFactory;
import uk.ac.ebi.metabolights.repository.dao.StudyDAO;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MzTabDAO;
import uk.ac.ebi.metabolights.repository.dao.hibernate.DAOException;
import uk.ac.ebi.metabolights.repository.model.Assay;
import uk.ac.ebi.metabolights.repository.model.MetaboliteAssignment;
import uk.ac.ebi.metabolights.repository.model.Study;
import uk.ac.ebi.metabolights.repository.model.User;
import uk.ac.ebi.metabolights.repository.model.webservice.RestResponse;
import uk.ac.ebi.metabolights.webservice.security.SpringUser;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("study")
public class StudyController {

    public static final String METABOLIGHTS_ID_REG_EXP = "(?:MTBLS|mtbls).+";
	private final static Logger logger = LoggerFactory.getLogger(StudyController.class.getName());
	private StudyDAO studyDAO;

    @RequestMapping("{accession:" + METABOLIGHTS_ID_REG_EXP +"}")
	@ResponseBody
	public RestResponse<Study> getStudyById(@PathVariable("accession") String accession) {

		logger.info("Requesting " + accession + " to the webservice");

		return getStudy(accession, false);
	}

	@RequestMapping("{accession:" + METABOLIGHTS_ID_REG_EXP +"}/full")
	@ResponseBody
	public RestResponse<Study> getFullStudyById(@PathVariable("accession") String accession) {

		logger.info("Requesting full study " + accession + " to the webservice");

		return getStudy(accession, true);

	}

	@RequestMapping("list")
	@ResponseBody
	public RestResponse<String[]> getAllStudyAccessions() {

		logger.info("Requesting a list of all public studies from the webservice");

		RestResponse<String[]> response = new RestResponse<>();

		try {
			studyDAO = getStudyDAO();

		} catch (DAOException e) {
			logger.error("Can't instantiate StudyDAO", e);
			return null;
		}

		try {
			List<String> studyList = studyDAO.getList(getUser().getApiToken());

			String[] strarray = studyList.toArray(new String[0]);
			response.setContent(strarray);
		} catch (DAOException e) {
			logger.error("Can't get the list of studies", e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return response;

	}

	private RestResponse<Study> getStudy (String accession, boolean includeMAFFiles)  {

		RestResponse<Study> response = new RestResponse<Study>();

		try {
			studyDAO= getStudyDAO();

		} catch (DAOException e) {
			logger.error("Can't instantiate StudyDAO", e);
			return null;
		}


		// Get the study
		try {
			response.setContent(studyDAO.getStudy(accession.toUpperCase(), getUser().getApiToken(), includeMAFFiles));           //Do not include metabolites (MAF) when loading this from the webapp.  This is added on later as an AJAX call
		} catch (DAOException e) {
			logger.error("Can't get the study requested " + accession, e);
			response.setMessage("Can't get the study requested.");
			response.setErr(e);
		}

		return  response;

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

	private uk.ac.ebi.metabolights.repository.dao.StudyDAO getStudyDAO() throws DAOException {

		if (studyDAO == null){

			studyDAO = DAOFactory.getInstance().getStudyDAO();
		}
		return studyDAO;
	}

	@RequestMapping("{accession:" + METABOLIGHTS_ID_REG_EXP +"}/assay/{assayIndex}/maf")
	@ResponseBody
	public RestResponse<MetaboliteAssignment> getMetabolites(@PathVariable("accession") String accession, @PathVariable("assayIndex") String assayIndex){


		logger.info("Requesting maf file of the assay " + assayIndex + " of the study " + accession + " to the webservice");

		// Get the study....
		// TODO: optimize this, since we are loading the whole study to get the MAF file name of one of the assay, and maf file can be loaded having only the maf
		RestResponse<Study> response = getStudy(accession, false);

		// Get the assay based on the index
		Assay assay = response.getContent().getAssays().get(Integer.parseInt(assayIndex)-1);

		MzTabDAO mzTabDAO = new MzTabDAO();
		MetaboliteAssignment metaboliteAssignment = new MetaboliteAssignment();


		String filePath = assay.getMetaboliteAssignment().getMetaboliteAssignmentFileName();

		if (filePath != null || !filePath.isEmpty()) {
			if (checkFileExists(filePath)) {
				logger.info("MAF file found, starting to read data from " + filePath);
				metaboliteAssignment = mzTabDAO.mapMetaboliteAssignmentFile(filePath);
			} else {
				logger.error("MAF file " + filePath + " does not exist!");
				metaboliteAssignment.setMetaboliteAssignmentFileName("ERROR: " + filePath + " does not exist!");
			}
		}

		return new RestResponse<MetaboliteAssignment>(metaboliteAssignment);
	}

	private boolean checkFileExists(String filePath){

		if (filePath == null || filePath.isEmpty())
			return false;        // No filename given

		File mafFile = new File(filePath);

		if (mafFile.exists())
			return true;

		return false;

	}
}
