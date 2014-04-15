/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/15/14 9:38 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

package uk.ac.ebi.metabolights.service;

import uk.ac.ebi.bioinvindex.model.Study;

import java.util.List;

/**
 * Services retrieval of study details
 *
 */
public interface StudyService {

	public Study getBiiStudy(String studyAcc, boolean clearSession) throws IllegalAccessException;
    public Study getBiiStudy(String studyAcc, boolean clearSession, boolean fromQueue) throws IllegalAccessException;
    public Study getBiiStudyOnObfuscation(String obfuscationCode, boolean clearSession) throws IllegalAccessException;
	public void update(Study study);
    public List<String> findAllStudies();
    public List<String> findStudiesGoingLive();

}


