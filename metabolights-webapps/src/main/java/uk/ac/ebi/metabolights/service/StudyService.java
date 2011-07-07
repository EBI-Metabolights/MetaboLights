package uk.ac.ebi.metabolights.service;


import java.util.List;

import uk.ac.ebi.bioinvindex.model.Study;


/**
 * Services retrieval of study details
 *
 */

public interface StudyService {

	public List<Study> findStudiesForUser(String userName);
	public Study getBiiStudy(String studyAcc);
}


