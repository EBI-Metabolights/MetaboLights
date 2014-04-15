/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/15/14 10:18 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.bioinvindex.model.Study;
import uk.ac.ebi.metabolights.dao.StudyDAO;

import java.util.List;

@Service
public class StudyServiceImpl implements StudyService {

	private static Logger logger = Logger.getLogger(StudyServiceImpl.class);

	@Autowired
	private StudyDAO studyDAO;

    //Called from the study Queue system
    @Transactional
    public Study getBiiStudy(String studyAcc,boolean clearSession, boolean fromQueue) throws IllegalAccessException {
        Study study = studyDAO.getStudy(studyAcc,clearSession, fromQueue);

        if (studyAcc.isEmpty() || study.getAcc().isEmpty())
            return new Study();

        return study;
    }

    //Called from reviewer access code
    @Transactional
    public Study getBiiStudyOnObfuscation(String obfuscationCode, boolean clearSession) throws IllegalAccessException {
        Study study = studyDAO.getBiiStudyOnObfuscation(obfuscationCode, clearSession, true);

        if (obfuscationCode.isEmpty() || study.getAcc().isEmpty())
            return new Study();

        return study;
    }


    //Called from user interation in the webapp
	@Transactional
	public Study getBiiStudy(String studyAcc, boolean clearSession) throws IllegalAccessException {
		return getBiiStudy(studyAcc, clearSession, false);
	}

	@Transactional
	public void update(Study study){
		studyDAO.update(study);
	}

    @Transactional
    public List<String> findAllStudies() {
        return studyDAO.findAllAcc();
    }

    @Transactional
    public List<String> findStudiesGoingLive() {
        return studyDAO.findStudiesGoingLive();
    }


}
