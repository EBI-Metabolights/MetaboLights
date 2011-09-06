package uk.ac.ebi.metabolights.service;


import uk.ac.ebi.bioinvindex.model.Study;


/**
 * Services retrieval of study details
 *
 */

public interface StudyService {

	public Study getBiiStudy(String studyAcc, boolean clearSession);
	public void update(Study study);
}


