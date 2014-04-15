/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * Last modified: 4/15/14 10:06 AM
 * Modified by:   kenneth
 *
 * Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 */

/**
 *
 */
package uk.ac.ebi.metabolights.dao;


import uk.ac.ebi.bioinvindex.model.Study;

import java.util.List;

/**
 * @author kenneth
 *
 */
public interface StudyDAO {

	public Study getStudy(String studyAcc, boolean clearSession) throws IllegalAccessException;
    public Study getStudy(String studyAcc, boolean clearSession, boolean fromQueueOrReviwer) throws IllegalAccessException;
    public Study getBiiStudyOnObfuscation(String obfuscationCode, boolean clearSession, boolean fromQueueOrReviwer) throws IllegalAccessException;
    public List<String> findAllAcc();
    public List<String> findStudiesGoingLive();
	public void update(Study study);

}
